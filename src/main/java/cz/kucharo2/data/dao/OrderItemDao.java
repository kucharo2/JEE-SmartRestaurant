package cz.kucharo2.data.dao;


import cz.kucharo2.data.entity.OrderItem;

import java.util.List;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public interface OrderItemDao extends AbstractGenericDao<OrderItem> {

	/**
	 * Return list of Unpaid order items for specific order
	 *
	 * @param orderId order id
	 * @return  list of unpaid order item of drink
	 */
	List<OrderItem> getUnpaidOrderItemByOrder(int orderId);

}
