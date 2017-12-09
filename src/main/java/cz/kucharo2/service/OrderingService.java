package cz.kucharo2.service;

import cz.kucharo2.common.model.AddOrderItemModel;
import cz.kucharo2.data.entity.Order;
import cz.kucharo2.service.exception.ServiceException;

/**
 * Created by Roman on 12/2/2014.
 */
public interface OrderingService {

	/***
	 * Order a single item
	 *
	 * @param model
	 * @return return order id
	 * @throws ServiceException
	 */
	Integer orderItem(AddOrderItemModel model) throws ServiceException;

	/**
	 * Deletes order item
	 * @param orderItemId order item id
	 */
	Integer removeItemFomOrder(int orderItemId) throws ServiceException;

	/**
	 * Confirms order by id
	 * @param orderId order id
	 */
	Order confirmOrder(int orderId) throws ServiceException;

	/**
	 * Save order.
	 *
	 * @param order order to be saved.
	 */
	void saveOrder(Order order);

	/**
	 * Cancels order by id
	 * @param orderId order id
	 */
	Order cancelBIll(int orderId) throws ServiceException;

	/**
	 * Returns order by id
	 * @param orderId order id
	 */
	Order getOrderById(int orderId);

	/**
	 * Return order in state CREATED on table for logged user.
	 *
	 * @param tableId id of table
	 */
	Order getCreatedOrderOnTable(int tableId);

}
