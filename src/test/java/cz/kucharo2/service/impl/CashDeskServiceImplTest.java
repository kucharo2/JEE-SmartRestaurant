package cz.kucharo2.service.impl;

import cz.kucharo2.data.dao.OrderDao;
import cz.kucharo2.data.dao.OrderItemDao;
import cz.kucharo2.data.entity.Account;
import cz.kucharo2.data.entity.Order;
import cz.kucharo2.data.entity.OrderItem;
import cz.kucharo2.data.entity.RestaurantTable;
import cz.kucharo2.data.enums.OrderStatus;
import cz.kucharo2.rest.model.RequestContext;
import cz.kucharo2.service.CashDeskService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Tests for {@link CashDeskService}
 *
 * @Author Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */
public class CashDeskServiceImplTest {
    @Mock
    private OrderDao orderDao;

    @Mock
    private OrderItemDao orderItemDao;

    @Mock
    private RequestContext requestContext;

    @InjectMocks
    private CashDeskService cashDeskService = new CashDeskServiceImpl();

    private static String TABLE_NAME = "test";
    private static String USERNAME = "test";
    private static Long PRICE = 255L;
    private static int ID = 1;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateOrderOnTable() throws Exception {
        Order order = new Order();
        order.setStatus(OrderStatus.CREATED);
        order.setDate(new Date());

        doAnswer((Answer<Void>) invocation -> {
            Object[] arguments = invocation.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                Order currentOrder = (Order) arguments[0];
                order.setTable(currentOrder.getTable());
            }
            return null;
        }).when(orderDao).createOrUpdate(any(Order.class));

        when(requestContext.getLoggedAccount()).thenReturn(new Account());

        RestaurantTable table = new RestaurantTable();
        table.setName(TABLE_NAME);

        cashDeskService.createOrderOnTable(table);

        verify(orderDao).createOrUpdate(any(Order.class));
        verify(requestContext).getLoggedAccount();

        Assert.assertEquals(table.getName(), order.getTable().getName());
    }

    @Test
    public void testCreateOrderItem() throws Exception {
        OrderItem orderItem = new OrderItem();
        orderItem.setPaid(false);

        doAnswer((Answer<Void>) invocation -> {
            Object[] arguments = invocation.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                OrderItem currentOrderItem = (OrderItem) arguments[0];
                orderItem.setPrice(currentOrderItem.getPrice());
            }
            return null;
        }).when(orderItemDao).createOrUpdate(any(OrderItem.class));

        OrderItem testOrderItem = new OrderItem();
        testOrderItem.setPrice(PRICE);
        cashDeskService.createOrderItem(testOrderItem);

        verify(orderItemDao).createOrUpdate(any(OrderItem.class));

        Assert.assertEquals(testOrderItem.getPrice(), orderItem.getPrice());
    }

    @Test
    public void testGetUnpaidOrderItemFoodByOrder() throws Exception {
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(PRICE);

        when(orderItemDao.getUnpaidOrderItemByOrder(anyInt())).thenReturn(Collections.singletonList(orderItem));

        List<OrderItem> testOrderItemList = cashDeskService.getUnpaidOrderItemFoodByOrder(ID);

        verify(orderItemDao).getUnpaidOrderItemByOrder(anyInt());

        Assert.assertFalse(testOrderItemList.isEmpty());
        Assert.assertEquals(orderItem, testOrderItemList.get(0));
    }

    @Test
    public void testGetUnpaidFinishedOrderItemsOnTable() throws Exception {
        OrderItem orderItem = new OrderItem();
        orderItem.setPaid(false);

        Order order = new Order();
        order.setStatus(OrderStatus.CREATED);
        order.setOrderItems(Collections.singletonList(orderItem));

        when(orderDao.getUnpaidFinishedOrdersOnTableByUser(anyInt(), anyInt())).thenReturn(Collections.singletonList(order));

        List<OrderItem> testOrderItemList = cashDeskService.getUnpaidFinishedOrderItemsOnTable(ID, ID);

        verify(orderDao).getUnpaidFinishedOrdersOnTableByUser(anyInt(), anyInt());

        Assert.assertFalse(testOrderItemList.isEmpty());
        Assert.assertEquals(orderItem, testOrderItemList.get(0));
    }

    @Test
    public void testGetUsersHavingUnpaidFinishedOrdersOnTable() throws Exception {
        OrderItem orderItem = new OrderItem();
        orderItem.setPaid(false);

        Account account = new Account();
        account.setUsername(USERNAME);

        Order order = new Order();
        order.setStatus(OrderStatus.CREATED);
        order.setOrderItems(Collections.singletonList(orderItem));
        order.setAccount(account);

        when(orderDao.getUnpaidOrderOnTable(anyInt())).thenReturn(Collections.singletonList(order));

        List<Account> testAccounts = cashDeskService.getUsersHavingUnpaidFinishedOrdersOnTable(ID);

        verify(orderDao).getUnpaidOrderOnTable(anyInt());

        Assert.assertFalse(testAccounts.isEmpty());
        Assert.assertEquals(account.getUsername(), testAccounts.get(0).getUsername());
    }

    @Test
    public void testPay() throws Exception {
        OrderItem orderItem = new OrderItem();
        orderItem.setPaid(false);

        doAnswer((Answer<Void>) invocation -> {
            Object[] arguments = invocation.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                OrderItem currentOrderItem = (OrderItem) arguments[0];
                orderItem.setPaid(currentOrderItem.isPaid());
            }
            return null;
        }).when(orderItemDao).createOrUpdate(any(OrderItem.class));

        when(orderItemDao.getById(anyInt())).thenReturn(orderItem);

        cashDeskService.pay(Collections.singletonList(ID));

        verify(orderItemDao).createOrUpdate(any(OrderItem.class));
        verify(orderItemDao).getById(anyInt());

        Assert.assertTrue(orderItem.isPaid());
    }

}