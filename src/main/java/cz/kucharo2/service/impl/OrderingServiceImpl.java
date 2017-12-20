package cz.kucharo2.service.impl;

import cz.kucharo2.common.model.AddOrderItemModel;
import cz.kucharo2.data.dao.OrderDao;
import cz.kucharo2.data.dao.OrderItemDao;
import cz.kucharo2.data.entity.*;
import cz.kucharo2.data.enums.OrderStatus;
import cz.kucharo2.data.enums.CategoryType;
import cz.kucharo2.event.ConfirmOrderEvent;
import cz.kucharo2.rest.model.RequestContext;
import cz.kucharo2.service.CashDeskService;
import cz.kucharo2.service.MenuService;
import cz.kucharo2.service.OrderingService;
import cz.kucharo2.service.TableService;
import cz.kucharo2.service.exception.ServiceException;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Roman on 12/2/2014.
 */
@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class OrderingServiceImpl implements OrderingService {

    @Inject
    private Logger logger;

    @Inject
    private CashDeskService cashDeskService;

    @Inject
    private TableService tableService;

    @Inject
    private MenuService menuService;

    @Inject
    private OrderItemDao orderItemDao;

    @Inject
    private OrderDao orderDao;

    @Inject
    private RequestContext requestContext;

    @Inject
    private Event<ConfirmOrderEvent> confirmOrderEventEvent;

    @Override
    public Integer orderItem(AddOrderItemModel model) throws ServiceException {
        if (model.getItemsToAdd().length < 1) {
            throw new IllegalArgumentException("At least one item id must be set.");
        }
        Order order;
        if (model.getOrderId() == null) {
            // check opened order on table
            order = getCreatedOrderOnTable(model.getTableId());
            if (order == null) {
                // create new order
                logger.info("Creating new order on table " + model.getTableId());
                RestaurantTable table = tableService.getTable(model.getTableId());
                order = cashDeskService.createOrderOnTable(table);
            }
        } else {
            order = getOrderById(model.getOrderId());
            Account loggedAccount = requestContext.getLoggedAccount();
            if(order.getAccount().isAnnonymousAccount() && !loggedAccount.isAnnonymousAccount()) {
                order.setAccount(loggedAccount);
                orderDao.createOrUpdate(order);
            } else if (!order.getAccount().getId().equals(loggedAccount.getId())) {
                throw new ServiceException("Cannot add order item into order that belongs to another account");
            }
        }
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new ServiceException("Not able to add another item on already confirmed order.");
        }
        List<Item> itemsToBeAddToOrder = new ArrayList<>();
        OrderItem mainFood = null;
        for (Integer itemId : model.getItemsToAdd()) {
            Item item = menuService.getItemById(itemId);

            if (mainFood == null && item.getCategory().getParentCategory().getCode() == CategoryType.MAIN_FOOD
                    && item.getCategory().getCode() != CategoryType.PRILOHA) {
                mainFood = createOrderItem(item, order);
                cashDeskService.createOrderItem(mainFood);
            } else {
                itemsToBeAddToOrder.add(item);
            }
        }
        for (Item item : itemsToBeAddToOrder) {
            OrderItem orderItem = createOrderItem(item, order);

            if (mainFood != null) {
                orderItem.setParentOrderItem(mainFood);
            }
            cashDeskService.createOrderItem(orderItem);
        }
        return order.getId();
    }

    @Override
    public Integer removeItemFomOrder(int orderItemId) throws ServiceException {
        Order order = orderItemDao.getById(orderItemId).getOrder();
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new ServiceException("Cannot delete item from order, because it's in different state than CREATED");
        }
        orderItemDao.delete(orderItemDao.getById(orderItemId));
        return order.getId();
    }

    @Override
    public Order confirmOrder(int orderId) throws ServiceException {
        Order order = orderDao.getById(orderId);
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new ServiceException("Cannot confirm order in different state than CREATED");
        }
        order.setStatus(OrderStatus.CONFIRMED);
        orderDao.createOrUpdate(order);
        confirmOrderEventEvent.fire(new ConfirmOrderEvent(order));
        return order;
    }

    @Override
    public void saveOrder(Order order) {
        orderDao.createOrUpdate(order);
    }

    @Override
    public Order cancelBIll(int orderId) throws ServiceException {
        Order order = orderDao.getById(orderId);
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new ServiceException("Cannot cancel order in different state than CREATED");
        }
        order.setStatus(OrderStatus.CANCELED);
        orderDao.createOrUpdate(order);
        return order;
    }

    @Override
    public Order getOrderById(int orderId) {
        return orderDao.getOrderWithItems(orderId);
    }

    @Override
    public Order getCreatedOrderOnTable(int tableId) {
        Account loggedAccount = requestContext.getLoggedAccount();
        return orderDao.getCreatedOrderByTableAndUser(tableId, loggedAccount.getId());
    }

    /**
     * Creates a order item from item
     *
     * @param item item
     * @param order order on witch is ordered
     * @return created order item food
     */
    private OrderItem createOrderItem(Item item, Order order) {
        OrderItem orderItemFood = new OrderItem();
        orderItemFood.setItem(item);
        orderItemFood.setPrice(item.getPrice());
        orderItemFood.setOrder(order);
        orderItemFood.setCreated(new Date());
        return orderItemFood;
    }

}
