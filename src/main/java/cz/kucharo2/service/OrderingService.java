package cz.kucharo2.service;

import cz.kucharo2.data.entity.Bill;

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
	Bill orderItem(int itemId, Integer billId, int tableId);

	/**
	 * Deletes bill item
	 * @param billItemId bill item id
	 */
	boolean removeItemFomOrder(int billItemId);

	/**
	 * Confirms bill by id
	 * @param billId bill id
	 */
	Bill confirmBill(int billId);

	/**
	 * Returns bill by id
	 * @param billId bill id
	 */
	Bill getBillById(int billId);

}
