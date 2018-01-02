package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.*;
import cz.kucharo2.data.entity.Order;
import cz.kucharo2.data.entity.OrderItem;
import cz.kucharo2.data.enums.CategoryType;
import cz.kucharo2.data.enums.OrderStatus;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Tests for {@link OrderDao}
 *
 * @Author Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */
@RunWith(Arquillian.class)
public class OrderDaoImplTest {

    @Inject
    private OrderDao orderDao;

    @Inject
    private AccountDao accountDao;

    @Inject
    private RestaurantTableDao restaurantTableDao;

    @Inject
    private OrderItemDao orderItemDao;

    @Inject
    private ItemDao itemDao;

    private static int ID = 1;
    private static int FAIL_ID = 1;
    private static Long PRICE = 100L;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, OrderDao.class.getPackage())
                .addPackages(true, Order.class.getPackage())
                .addPackages(true, CategoryType.class.getPackage())
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldNotExistUnpaidOrderOnTable() throws Exception {
        Assert.assertEquals(0, orderDao.getUnpaidOrderOnTable(FAIL_ID).size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldExistUnpaidOrderOnTable() throws Exception {
        Order order = createOrder();
        order.setStatus(OrderStatus.FINISHED);
        order.setTable(restaurantTableDao.getById(ID));

        OrderItem orderItem = createOrderItem(order);
        orderItem.setItem(itemDao.getById(ID));

        orderItemDao.createOrUpdate(orderItem);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        order.setOrderItems(orderItems);

        orderDao.createOrUpdate(order);

        List<Order> orderList = orderDao.getUnpaidOrderOnTable(ID);
        Assert.assertFalse(orderList.isEmpty());

        Order testOrder = orderList.stream().filter(x -> order.getId().equals(x.getId())).findAny().orElse(null);

        Assert.assertNotNull(testOrder);
        Assert.assertEquals(order.getId(), testOrder.getId());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldNotExistUnpaidFinishedOrdersOnTableByUser(){
        Assert.assertEquals(0, orderDao.getUnpaidFinishedOrdersOnTableByUser(FAIL_ID, FAIL_ID).size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldExistUnpaidFinishedOrdersOnTableByUser(){
        Order order = createOrder();
        order.setStatus(OrderStatus.FINISHED);
        order.setTable(restaurantTableDao.getById(ID));
        order.setAccount(accountDao.getById(ID));

        OrderItem orderItem = createOrderItem(order);
        orderItem.setItem(itemDao.getById(ID));

        orderItemDao.createOrUpdate(orderItem);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        order.setOrderItems(orderItems);

        orderDao.createOrUpdate(order);

        List<Order> orderList = orderDao.getUnpaidFinishedOrdersOnTableByUser(ID, ID);
        Assert.assertTrue(0 < orderList.size());

        Order testOrder = orderList.stream().filter(x -> order.getId().equals(x.getId())).findAny().orElse(null);

        Assert.assertNotNull(testOrder);
        Assert.assertEquals(order.getId(), testOrder.getId());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldNotExistOrderByTableAndUser() throws Exception {
        Assert.assertNull(orderDao.getCreatedOrderByTableAndUser(FAIL_ID, FAIL_ID));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldExistOrderByTableAndUser() throws Exception {
        Order order = createOrder();
        order.setStatus(OrderStatus.CREATED);
        order.setAccount(accountDao.getById(ID));
        order.setTable(restaurantTableDao.getById(ID));

        orderDao.createOrUpdate(order);

        Order testOrder = orderDao.getCreatedOrderByTableAndUser(ID, ID);

        Assert.assertNotNull(testOrder);
        Assert.assertEquals(order.getId(), testOrder.getId());
        Assert.assertEquals(order.getStatus(), testOrder.getStatus());
        Assert.assertEquals(order.getDate(), testOrder.getDate());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldNotExistOrderWithItems() throws Exception {
        Assert.assertNull(orderDao.getOrderWithItems(0));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldExistOrderWithItems() throws Exception {
        Order order = createOrder();
        order.setStatus(OrderStatus.CREATED);

        OrderItem orderItem = createOrderItem(order);

        orderItemDao.createOrUpdate(orderItem);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        order.setOrderItems(orderItems);

        orderDao.createOrUpdate(order);

        Order testOrder = orderDao.getOrderWithItems(order.getId());

        Assert.assertNotNull(testOrder);
        Assert.assertEquals(order.getId(), testOrder.getId());
        Assert.assertEquals(order.getStatus(), testOrder.getStatus());
        Assert.assertEquals(order.getDate(), testOrder.getDate());
    }

    private Order createOrder(){
        Order order = new Order();
        order.setDate(new Date());

        return order;
    }

    private OrderItem createOrderItem(Order order){
        OrderItem orderItem = new OrderItem();
        orderItem.setCreated(new Date());
        orderItem.setPaid(false);
        orderItem.setPrice(PRICE);
        orderItem.setOrder(order);

        return orderItem;
    }
}
