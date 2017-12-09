package cz.kucharo2.service.impl;

import cz.kucharo2.data.dao.OrderDao;
import cz.kucharo2.data.dao.OrderItemDao;
import cz.kucharo2.data.entity.Order;
import cz.kucharo2.data.entity.OrderItem;
import cz.kucharo2.data.entity.RestaurantTable;
import cz.kucharo2.data.enums.OrderStatus;
import cz.kucharo2.rest.model.SessionContext;
import cz.kucharo2.service.CashDeskService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

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
	private SessionContext sessionContext;

	@Override
	public Order createOrderOnTable(RestaurantTable table) {
		Order order = new Order();
		order.setTable(table);
		order.setStatus(OrderStatus.CREATED);
		order.setAccount(sessionContext.getLoggedAccount());
		orderDao.createOrUpdate(order);
		return order;
	}

	@Override
	public void createOrderItem(OrderItem orderItemFoods) {
		orderItemDao.createOrUpdate(orderItemFoods);
	}

	@Override
	public List<OrderItem> getUnpaidOrderItemFoodByOrder(Order order) {
		return orderItemDao.getUnpaidOrderItemByOrder(order.getId());
	}

	@Override
	public void pay(List<OrderItem> orderItems) {
		if(orderItems != null && orderItems.size() > 0) {
			for(OrderItem orderItemFood: orderItems) {
				orderItemFood.setPaid(true);
				orderItemDao.createOrUpdate(orderItemFood);
			}
		}
	}


}
