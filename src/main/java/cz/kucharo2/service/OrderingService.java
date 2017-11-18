package cz.kucharo2.service;

import cz.kucharo2.data.entity.Bill;
import cz.kucharo2.service.exception.ServiceException;

/**
 * Created by Roman on 12/2/2014.
 */
public interface OrderingService {

	/**
	 * Order a single item
	 *
	 * @param itemId item to order
	 * @param billId  bill on which is ordering
	 * @param tableId table where is ordering
	 *
	 * @return created or updated bill
	 */
	Bill orderItem(Integer billId, int tableId, Integer... itemId) throws ServiceException;

	/**
	 * Deletes bill item
	 * @param billItemId bill item id
	 */
	boolean removeItemFomOrder(int billItemId) throws ServiceException;

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
	 * Return bill in state CREATED on table.
	 *
	 * @param tableId id of table
	 */
	Bill getCreatedBillOnTable(int tableId);

}
