package cz.kucharo2.service;

import cz.kucharo2.common.model.AddOrderItemModel;
import cz.kucharo2.data.entity.Bill;
import cz.kucharo2.service.exception.ServiceException;

/**
 * Created by Roman on 12/2/2014.
 */
public interface OrderingService {

	/***
	 * Order a single item
	 *
	 * @param model
	 * @return return bill id
	 * @throws ServiceException
	 */
	Integer orderItem(AddOrderItemModel model) throws ServiceException;

	/**
	 * Deletes bill item
	 * @param billItemId bill item id
	 */
	Integer removeItemFomOrder(int billItemId) throws ServiceException;

	/**
	 * Confirms bill by id
	 * @param billId bill id
	 */
	Bill confirmBill(int billId) throws ServiceException;

	/**
	 * Returns bill by id
	 * @param billId bill id
	 */
	Bill getBillById(int billId);

	/**
	 * Return bill in state CREATED on table for logged user.
	 *
	 * @param tableId id of table
	 */
	Bill getCreatedBillOnTable(int tableId);

}
