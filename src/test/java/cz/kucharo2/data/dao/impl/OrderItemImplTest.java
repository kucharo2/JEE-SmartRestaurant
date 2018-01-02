package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.ItemDao;
import cz.kucharo2.data.dao.OrderDao;
import cz.kucharo2.data.dao.OrderItemDao;
import cz.kucharo2.data.entity.Order;
import cz.kucharo2.data.entity.OrderItem;
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
 * Tests for {@link OrderItemDao}
 *
 * Copyright 2017 IEAP CTU
 * Author: Jakub Begera (jakub.begera@cvut.cz), Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */
@RunWith(Arquillian.class)
public class OrderItemImplTest {
    @Inject
    private OrderDao orderDao;

    @Inject
    private OrderItemDao orderItemDao;

    @Inject
    private ItemDao itemDao;

    private static int ID = 1;
    private static int FAIL_ID = 1;
    private static Long PRICE = 150L;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, OrderItemDao.class.getPackage())
                .addPackages(true, OrderItem.class.getPackage())
                .addPackages(true, OrderStatus.class.getPackage())
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldNotExistUnpaidOrderItemByOrder() throws Exception {
        Assert.assertEquals(0, orderItemDao.getUnpaidOrderItemByOrder(FAIL_ID).size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldExistUnpaidOrderItemByOrder() throws Exception {
        Order order = new Order();
        order.setStatus(OrderStatus.CREATED);
        order.setDate(new Date());

        OrderItem orderItem = new OrderItem();
        orderItem.setCreated(new Date());
        orderItem.setOrder(order);
        orderItem.setPaid(false);
        orderItem.setPrice(PRICE);
        orderItem.setItem(itemDao.getById(ID));

        orderItemDao.createOrUpdate(orderItem);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        order.setOrderItems(orderItems);

        orderDao.createOrUpdate(order);

        List<OrderItem> orderItemList = orderItemDao.getUnpaidOrderItemByOrder(order.getId());
        Assert.assertFalse(orderItemList.isEmpty());

        OrderItem testOrderItem = orderItemList.stream().filter(x -> orderItem.getId().equals(x.getId())).findAny().orElse(null);

        Assert.assertNotNull(testOrderItem);
        Assert.assertEquals(orderItem.getId(), testOrderItem.getId());
    }
}
