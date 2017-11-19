package cz.kucharo2.service;

import cz.kucharo2.data.entity.Bill;
import cz.kucharo2.data.entity.BillItem;
import cz.kucharo2.data.entity.RestaurantTable;

import java.util.List;

/**
 * Created by Roman on 12/2/2014.
 */
public interface CashDeskService {

	/**
	 * Create bill for specific table
	 *
	 * @param table table
	 * @return id of created bill
	 */
	Bill createBillOnTable(RestaurantTable table);

	/**
	 * Create bill item of food
	 *
	 * @param billItemFoods bill item to be created
	 */
	void createBillItem(BillItem billItemFoods);

	/**
	 * Returns unpaid bill items of food for specific bill
	 *
	 * @param bill bill
	 * @return unpaid bill items
	 */
	List<BillItem> getUnpaidBillItemFoodByBill(Bill bill);

	/**
	 * Pay bill items
	 * @param billItems bill items of foods to be paid
	 */
	void pay(List<BillItem> billItems);

}
