package cz.kucharo2.service.impl;

import cz.kucharo2.common.model.AddOrderItemModel;
import cz.kucharo2.data.dao.OrderDao;
import cz.kucharo2.data.dao.OrderItemDao;
import cz.kucharo2.data.entity.*;
import cz.kucharo2.data.enums.AccountRole;
import cz.kucharo2.data.enums.CategoryType;
import cz.kucharo2.data.enums.OrderStatus;
import cz.kucharo2.event.ConfirmOrderEvent;
import cz.kucharo2.rest.model.RequestContext;
import cz.kucharo2.service.CashDeskService;
import cz.kucharo2.service.MenuService;
import cz.kucharo2.service.OrderingService;
import cz.kucharo2.service.TableService;
import cz.kucharo2.service.exception.ServiceException;
import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import javax.enterprise.event.Event;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.*;

/**
 * Tests for {@link OrderingService}
 *
 * @Author Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */
public class OrderingServiceImplTest {

    @Mock
    private OrderItemDao orderItemDao;

    @Mock
    private OrderDao orderDao;

    @Mock
    private RequestContext requestContext;

    @Mock
    private Event<ConfirmOrderEvent> confirmOrderEventEvent;

    @Mock
    private TableService tableService;

    @Mock
    private CashDeskService cashDeskService;

    @Mock
    private MenuService menuService;

    @Mock
    private Logger logger;

    @InjectMocks
    private OrderingService orderingService = new OrderingServiceImpl();

    private static String TABLE_NAME = "test";
    private static int ID = 1;
    private static int ID2 = 2;
    private static Long PRICE = 100L;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionLengthOfItemsInOrderItem() throws Exception {
        AddOrderItemModel addOrderItemModel = new AddOrderItemModel();
        addOrderItemModel.setItemsToAdd(new Integer[0]);
        orderingService.orderItem(addOrderItemModel);
    }

    @Test
    public void testGetOrderIdAndOrderAreNullInOrderItem() throws Exception {
        AddOrderItemModel addOrderItemModel = new AddOrderItemModel();
        Integer[] integers = new Integer[]{ID};
        addOrderItemModel.setItemsToAdd(integers);
        addOrderItemModel.setTableId(ID);

        RestaurantTable restaurantTable = createRestaurantTable();
        when(tableService.getTable(anyInt())).thenReturn(restaurantTable);

        doAnswer((Answer<Void>) invocation -> {
            invocation.getArguments();
            return null;
        }).when(logger).info(anyString());

        Account account = createAccount(ID);

        when(requestContext.getLoggedAccount()).thenReturn(account);
        when(orderDao.getCreatedOrderByTableAndUser(anyInt(), anyInt())).thenReturn(null);

        Order order = createOrder();
        order.setStatus(OrderStatus.CREATED);
        order.setTable(restaurantTable);

        when(cashDeskService.createOrderOnTable(any(RestaurantTable.class))).thenReturn(order);

        Item item = createItem(CategoryType.MASO, CategoryType.MAIN_FOOD);
        when(menuService.getItemById(anyInt())).thenReturn(item);

        Integer id = orderingService.orderItem(addOrderItemModel);

        verify(tableService).getTable(anyInt());
        verify(cashDeskService).createOrderOnTable(any(RestaurantTable.class));
        verify(requestContext).getLoggedAccount();
        verify(orderDao).getCreatedOrderByTableAndUser(anyInt(), anyInt());
        verify(menuService).getItemById(anyInt());
        verify(logger).info(anyString());

        Assert.assertNotNull(id);
        Assert.assertEquals(order.getId(), id);
    }

    @Test
    public void testGetOrderIdIsNullAndOrderIsNotNullInOrderItem() throws Exception {
        AddOrderItemModel addOrderItemModel = new AddOrderItemModel();
        Integer[] integers = new Integer[]{ID};
        addOrderItemModel.setItemsToAdd(integers);
        addOrderItemModel.setTableId(ID);

        RestaurantTable restaurantTable = createRestaurantTable();
        Account account = createAccount(ID);

        Order order = createOrder();
        order.setStatus(OrderStatus.CREATED);
        order.setTable(restaurantTable);

        when(requestContext.getLoggedAccount()).thenReturn(account);
        when(orderDao.getCreatedOrderByTableAndUser(anyInt(), anyInt())).thenReturn(order);

        Item item = createItem(CategoryType.MASO, CategoryType.MAIN_FOOD);
        when(menuService.getItemById(anyInt())).thenReturn(item);

        Integer id = orderingService.orderItem(addOrderItemModel);

        verify(requestContext).getLoggedAccount();
        verify(orderDao).getCreatedOrderByTableAndUser(anyInt(), anyInt());
        verify(menuService).getItemById(anyInt());

        Assert.assertNotNull(id);
        Assert.assertEquals(order.getId(), id);
    }

    @Test
    public void testGetOrderIdIsNotNullAndCorrectAccInOrderItem() throws Exception {
        AddOrderItemModel addOrderItemModel = new AddOrderItemModel();
        Integer[] integers = new Integer[]{ID};
        addOrderItemModel.setItemsToAdd(integers);
        addOrderItemModel.setTableId(ID);
        addOrderItemModel.setOrderId(ID);

        Account account = createAccount(ID);
        account.setAccountRole(AccountRole.REGISTERED_CUSTOMER);

        Account account2 = createAccount(ID2);
        account2.setAccountRole(AccountRole.ANONYMOUS_CUSTOMER);


        Order order = createOrder();
        order.setStatus(OrderStatus.CREATED);
        order.setAccount(account2);

        when(orderDao.getOrderWithItems(anyInt())).thenReturn(order);
        when(requestContext.getLoggedAccount()).thenReturn(account);

        doAnswer((Answer<Void>) invocation -> {
            Object[] arguments = invocation.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                Order currentOrder = (Order) arguments[0];
                order.setAccount(currentOrder.getAccount());
            }
            return null;
        }).when(orderDao).createOrUpdate(order);

        Item item = createItem(CategoryType.MASO, CategoryType.MAIN_FOOD);
        when(menuService.getItemById(anyInt())).thenReturn(item);

        Integer id = orderingService.orderItem(addOrderItemModel);

        verify(orderDao).getOrderWithItems(anyInt());
        verify(requestContext).getLoggedAccount();
        verify(orderDao).createOrUpdate(any(Order.class));
        verify(menuService).getItemById(anyInt());

        Assert.assertNotNull(id);
        Assert.assertEquals(order.getId(), id);
    }

    @Test(expected = ServiceException.class)
    public void testGetOrderIdIsNotNullAndIncorrectAccInOrderItem() throws Exception {
        AddOrderItemModel addOrderItemModel = new AddOrderItemModel();
        Integer[] integers = new Integer[]{ID};
        addOrderItemModel.setItemsToAdd(integers);
        addOrderItemModel.setTableId(ID);
        addOrderItemModel.setOrderId(ID);

        Account account = createAccount(ID);
        account.setAccountRole(AccountRole.ANONYMOUS_CUSTOMER);

        Account account2 = createAccount(ID2);
        account2.setAccountRole(AccountRole.ANONYMOUS_CUSTOMER);

        Order order = createOrder();
        order.setStatus(OrderStatus.CREATED);
        order.setAccount(account);

        when(orderDao.getOrderWithItems(anyInt())).thenReturn(order);
        when(requestContext.getLoggedAccount()).thenReturn(account2);

        Integer id = orderingService.orderItem(addOrderItemModel);
    }

    @Test(expected = ServiceException.class)
    public void testOrderStatusIsCreatedInOrderItem() throws Exception {
        AddOrderItemModel addOrderItemModel = new AddOrderItemModel();
        Integer[] integers = new Integer[]{ID};
        addOrderItemModel.setItemsToAdd(integers);
        addOrderItemModel.setTableId(ID);
        addOrderItemModel.setOrderId(ID);

        Account account = createAccount(ID);
        account.setAccountRole(AccountRole.REGISTERED_CUSTOMER);

        Order order = createOrder();
        order.setStatus(OrderStatus.FINISHED);
        order.setAccount(account);

        when(orderDao.getOrderWithItems(anyInt())).thenReturn(order);
        when(requestContext.getLoggedAccount()).thenReturn(account);

        Integer id = orderingService.orderItem(addOrderItemModel);
    }

    @Test
    public void testWholeMethodOrderItem() throws Exception {
        AddOrderItemModel addOrderItemModel = new AddOrderItemModel();
        Integer[] integers = new Integer[]{ID};
        addOrderItemModel.setItemsToAdd(integers);
        addOrderItemModel.setTableId(ID);
        addOrderItemModel.setOrderId(ID);

        Account account = createAccount(ID);
        account.setAccountRole(AccountRole.REGISTERED_CUSTOMER);

        Order order = createOrder();
        order.setStatus(OrderStatus.CREATED);
        order.setAccount(account);

        when(orderDao.getOrderWithItems(anyInt())).thenReturn(order);
        when(requestContext.getLoggedAccount()).thenReturn(account);

        Item item = createItem(CategoryType.PRILOHA, CategoryType.MAIN_FOOD);
        when(menuService.getItemById(anyInt())).thenReturn(item);

        Integer id = orderingService.orderItem(addOrderItemModel);

        verify(orderDao).getOrderWithItems(anyInt());
        verify(requestContext).getLoggedAccount();
        verify(menuService).getItemById(anyInt());

        Assert.assertNotNull(id);
        Assert.assertEquals(order.getId(), id);
    }

    @Test
    public void testRemoveItemFomOrder() throws Exception {
        Order order = createOrder();
        order.setStatus(OrderStatus.CREATED);

        OrderItem orderItem = createOrderItem(order);

        order.setOrderItems(Arrays.asList(orderItem));

        when(orderItemDao.getById(anyInt())).thenReturn(orderItem);
        when(orderItemDao.delete(any(OrderItem.class))).thenReturn(true);

        Integer id = orderingService.removeItemFomOrder(ID);

        verify(orderItemDao, times(2)).getById(anyInt());
        verify(orderItemDao).delete(any(OrderItem.class));

        Assert.assertNotNull(id);
        Assert.assertEquals(order.getId(), id);
    }

    @Test(expected = ServiceException.class)
    public void testExceptionRemoveInItemFomOrder() throws Exception {
        Order order = createOrder();
        order.setStatus(OrderStatus.FINISHED);

        OrderItem orderItem = createOrderItem(order);

        order.setOrderItems(Arrays.asList(orderItem));

        when(orderItemDao.getById(anyInt())).thenReturn(orderItem);

        Integer id = orderingService.removeItemFomOrder(ID);
    }

    @Test
    public void testConfirmOrder() throws Exception {
        Order order = createOrder();
        order.setStatus(OrderStatus.CREATED);

        when(orderDao.getById(anyInt())).thenReturn(order);

        doAnswer((Answer<Void>) invocation -> {
            Object[] arguments = invocation.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                order.setStatus(OrderStatus.CONFIRMED);
            }
            return null;
        }).when(orderDao).createOrUpdate(any(Order.class));

        doAnswer((Answer<Void>) invocation -> {
            Object[] arguments = invocation.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                ConfirmOrderEvent currentConfirmOrderEvent = (ConfirmOrderEvent) arguments[0];
            }
            return null;
        }).when(confirmOrderEventEvent).fire(any(ConfirmOrderEvent.class));

        Order testOrder = orderingService.confirmOrder(ID);

        verify(orderDao).getById(anyInt());
        verify(orderDao).createOrUpdate(any(Order.class));
        verify(confirmOrderEventEvent).fire(any(ConfirmOrderEvent.class));

        Assert.assertEquals(order, testOrder);
    }

    @Test(expected = ServiceException.class)
    public void testExceptionInConfirmOrder() throws Exception {
        Order order = createOrder();
        order.setStatus(OrderStatus.FINISHED);

        when(orderDao.getById(anyInt())).thenReturn(order);

        Order testOrder = orderingService.confirmOrder(ID);
    }

    @Test
    public void testSaveOrder() throws Exception {
        Order order = createOrder();
        order.setStatus(OrderStatus.CREATED);

        doAnswer((Answer<Void>) invocation -> {
            Object[] arguments = invocation.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                Order currentOrder = (Order) arguments[0];
                order.setDate(currentOrder.getDate());
                order.setStatus(currentOrder.getStatus());
            }
            return null;
        }).when(orderDao).createOrUpdate(any(Order.class));

        Order order2 = createOrder();
        order2.setStatus(OrderStatus.FINISHED);

        orderingService.saveOrder(order2);

        verify(orderDao).createOrUpdate(any(Order.class));

        Assert.assertEquals(order2, order);
    }

    @Test
    public void testCancelBIll() throws Exception {
        Order order = createOrder();
        order.setStatus(OrderStatus.CREATED);

        when(orderDao.getById(anyInt())).thenReturn(order);

        doAnswer((Answer<Void>) invocation -> {
            Object[] arguments = invocation.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                order.setStatus(OrderStatus.CANCELED);
            }
            return null;
        }).when(orderDao).createOrUpdate(any(Order.class));

        Order testOrder = orderingService.cancelBIll(ID);

        verify(orderDao).getById(anyInt());
        verify(orderDao).createOrUpdate(any(Order.class));

        Assert.assertEquals(order, testOrder);
    }

    @Test(expected = ServiceException.class)
    public void testExceptionInCancelBIll() throws Exception {
        Order order = createOrder();
        order.setStatus(OrderStatus.FINISHED);

        when(orderDao.getById(anyInt())).thenReturn(order);

        Order testOrder = orderingService.cancelBIll(ID);
    }

    @Test
    public void testGetOrderById() throws Exception {
        Order order = createOrder();
        order.setStatus(OrderStatus.CREATED);

        when(orderDao.getOrderWithItems(anyInt())).thenReturn(order);

        Order testOrder =  orderingService.getOrderById(ID);

        verify(orderDao).getOrderWithItems(anyInt());

        Assert.assertEquals(order, testOrder);
    }

    @Test
    public void testGetCreatedOrderOnTable() throws Exception {
        Account account = createAccount(ID);

        Order order = createOrder();
        order.setStatus(OrderStatus.CREATED);
        order.setAccount(account);

        when(requestContext.getLoggedAccount()).thenReturn(account);

        when(orderDao.getCreatedOrderByTableAndUser(anyInt(), anyInt())).thenReturn(order);

        Order testOrder = orderingService.getCreatedOrderOnTable(ID);

        verify(requestContext).getLoggedAccount();
        verify(orderDao).getCreatedOrderByTableAndUser(anyInt(), anyInt());

        Assert.assertEquals(order, testOrder);
    }

    private RestaurantTable createRestaurantTable(){
        RestaurantTable restaurantTable = new RestaurantTable();
        restaurantTable.setName(TABLE_NAME);

        return restaurantTable;
    }

    private Account createAccount(int id){
        Account account = new Account();
        account.setId(id);


        return account;
    }

    private Order createOrder(){
        Order order = new Order();
        order.setDate(new Date());
        order.setId(ID);

        return order;
    }

    private Item createItem(CategoryType code, CategoryType parentCode){
        Category category = new Category();
        category.setCode(code);

        Category parentCategory = new Category();
        parentCategory.setCode(parentCode);

        category.setParentCategory(parentCategory);
        parentCategory.setChildCategories(Arrays.asList(category));

        Item item = new Item();
        item.setId(ID);
        item.setCategory(category);

        return item;
    }


    private OrderItem createOrderItem(Order order){
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(PRICE);
        orderItem.setPaid(false);
        orderItem.setCreated(new Date());
        orderItem.setOrder(order);

        return orderItem;
    }
}