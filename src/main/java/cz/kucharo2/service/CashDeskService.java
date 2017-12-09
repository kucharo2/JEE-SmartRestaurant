package cz.kucharo2.service;

import cz.kucharo2.data.entity.Order;
import cz.kucharo2.data.entity.OrderItem;
import cz.kucharo2.data.entity.RestaurantTable;

import java.util.List;

/**
 * Created by Roman on 12/2/2014.
 */
public interface CashDeskService {

	/**
	 * Create order for specific table
	 *
	 * @param table table
	 * @return id of created order
	 */
	Order createOrderOnTable(RestaurantTable table);

	/**
	 * Create order item of food
	 *
	 * @param orderItemFoods order item to be created
	 */
	void createOrderItem(OrderItem orderItemFoods);

	/**
	 * Returns unpaid order items of food for specific order
	 *
	 * @param order order
	 * @return unpaid order items
	 */
	List<OrderItem> getUnpaidOrderItemFoodByOrder(Order order);

	/**
	 * Pay order items
	 * @param orderItems order items of foods to be paid
	 */
	void pay(List<OrderItem> orderItems);

}
