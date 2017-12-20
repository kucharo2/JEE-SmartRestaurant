package cz.kucharo2.service.impl;

import cz.kucharo2.data.dao.OrderDao;
import cz.kucharo2.data.dao.OrderItemDao;
import cz.kucharo2.data.entity.Account;
import cz.kucharo2.data.entity.Order;
import cz.kucharo2.data.entity.OrderItem;
import cz.kucharo2.data.entity.RestaurantTable;
import cz.kucharo2.data.enums.AccountRole;
import cz.kucharo2.data.enums.OrderStatus;
import cz.kucharo2.rest.model.RequestContext;
import cz.kucharo2.service.CashDeskService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * Created by Roman on 12/2/2014.
 */
@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class CashDeskServiceImpl implements CashDeskService {

    @Inject
    private OrderDao orderDao;

    @Inject
    private OrderItemDao orderItemDao;

    @Inject
    private RequestContext requestContext;

    @Override
    public Order createOrderOnTable(RestaurantTable table) {
        Order order = new Order();
        order.setTable(table);
        order.setStatus(OrderStatus.CREATED);
        order.setAccount(requestContext.getLoggedAccount());
        orderDao.createOrUpdate(order);
        return order;
    }

    @Override
    public void createOrderItem(OrderItem orderItemFoods) {
        orderItemDao.createOrUpdate(orderItemFoods);
    }

    @Override
    public List<OrderItem> getUnpaidOrderItemFoodByOrder(Integer orderId) {
        return orderItemDao.getUnpaidOrderItemByOrder(orderId);
    }

    @Override
    public List<OrderItem> getUnpaidFinishedOrderItemsOnTable(Integer tableId, Integer userId) {
        if (userId == null) {
            userId = requestContext.getLoggedAccount().getId();
        }
        List<Order> ordersWithUnpaidItems = orderDao.getUnpaidFinishedOrdersOnTableByUser(tableId, userId);
        return ordersWithUnpaidItems.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .filter(i -> !i.isPaid())
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> getUsersHavingUnpaidFinishedOrdersOnTable(Integer tableId) {
        List<Order> ordersWithUnpaidItems = orderDao.getUnpaidOrderOnTable(tableId);

        return ordersWithUnpaidItems.stream()
                .map(Order::getAccount)
                .collect(collectingAndThen(
                            toMap(Account::getId, account -> account, (accountOld, accountNew) -> accountOld),
                            m -> new ArrayList<>(m.values())
                        )
                );
    }

    @Override
    public void pay(List<Integer> orderItemsIds) {
        if (orderItemsIds != null && orderItemsIds.size() > 0) {
            for (Integer orderItemId : orderItemsIds) {
                OrderItem orderItem = orderItemDao.getById(orderItemId);
                orderItem.setPaid(true);
                orderItemDao.createOrUpdate(orderItem);
            }
        }
    }


}
