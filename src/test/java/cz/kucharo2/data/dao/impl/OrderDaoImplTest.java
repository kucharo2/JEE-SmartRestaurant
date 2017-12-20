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
        Assert.assertEquals(0, orderDao.getUnpaidOrderOnTable(0).size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldExistUnpaidOrderOnTable() throws Exception {
        Order order = new Order();
        order.setStatus(OrderStatus.CREATED);
        order.setDate(new Date());
        order.setTable(restaurantTableDao.getById(1));

        OrderItem orderItem = new OrderItem();
        orderItem.setCreated(new Date());
        orderItem.setOrder(order);
        orderItem.setPaid(false);
        orderItem.setPrice(100);
        orderItem.setItem(itemDao.getById(1));

        orderItemDao.createOrUpdate(orderItem);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        order.setOrderItems(orderItems);

        orderDao.createOrUpdate(order);

        Assert.assertTrue(0 < orderDao.getUnpaidOrderOnTable(1).size());
    }

    @Test

    public void testShouldNotExistOrderByTableAndUser() throws Exception {
        Assert.assertNull(orderDao.getCreatedOrderByTableAndUser(0, 0));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldExistOrderByTableAndUser() throws Exception {
        Order order = new Order();
        order.setStatus(OrderStatus.CREATED);
        order.setDate(new Date());
        order.setAccount(accountDao.getById(1));
        order.setTable(restaurantTableDao.getById(2));

        orderDao.createOrUpdate(order);

        Order testOrder = orderDao.getCreatedOrderByTableAndUser(2, 1);

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
        Order order = new Order();
        order.setStatus(OrderStatus.CREATED);
        order.setDate(new Date());

        OrderItem orderItem = new OrderItem();
        orderItem.setCreated(new Date());
        orderItem.setOrder(order);
        orderItem.setPaid(false);
        orderItem.setPrice(100);

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
}
