package cz.kucharo2.data.dao;


import cz.kucharo2.data.entity.Bill;

import java.util.List;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public interface BillDao extends AbstractGenericDao<Bill> {

	/**
	 *  Get all unpaid bills for specific table.
	 *
	 * @param tableID table id
	 * @return list of unpaid bills
	 */
	List<Bill> getUnpaidBillOnTable(int tableID);


	/**
	 * Get CREATED bill on table.
	 *
	 * @param tableId table id
	 */
	Bill getCreatedBillOnTable(int tableId);

	Bill getBillWithItems(int billId);
}
