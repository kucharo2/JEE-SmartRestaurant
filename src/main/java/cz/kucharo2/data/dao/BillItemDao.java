package cz.kucharo2.data.dao;


import cz.kucharo2.data.entity.BillItem;

import java.util.List;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public interface BillItemDao extends AbstractGenericDao<BillItem> {

	/**
	 * Return list of Unpaid bill items for specific bill
	 *
	 * @param billId bill id
	 * @return  list of unpaid bill item of drink
	 */
	List<BillItem> getUnpaidBillItemByBill(int billId);

}
