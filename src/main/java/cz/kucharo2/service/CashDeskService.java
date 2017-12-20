package cz.kucharo2.service;

import cz.kucharo2.data.entity.Account;
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
	 * @param orderId order id
	 * @return unpaid order items
	 */
	List<OrderItem> getUnpaidOrderItemFoodByOrder(Integer orderId);

	/**
	 * Returns unpaid finished order items for specific table and specific user
	 *
	 * @param tableId table id
	 * @return unpaid finished orders on given table
	 */
	List<OrderItem> getUnpaidFinishedOrderItemsOnTable(Integer tableId, Integer userId);

	/**
	 * Returns users which have unpaid order items on specific table
	 * @param tableId table id
	 * @return users having unpaid order items on table
	 */
	List<Account> getUsersHavingUnpaidFinishedOrdersOnTable(Integer tableId);

	/**
	 * Pay order items
	 * @param orderItemsIds order item ids of dishes to be paid
	 */
	void pay(List<Integer> orderItemsIds);

}
