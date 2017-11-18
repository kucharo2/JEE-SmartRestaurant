package cz.kucharo2.service.impl;

import cz.kucharo2.data.dao.BillDao;
import cz.kucharo2.data.dao.BillItemDao;
import cz.kucharo2.data.entity.Bill;
import cz.kucharo2.data.entity.BillItem;
import cz.kucharo2.data.entity.Item;
import cz.kucharo2.data.entity.RestaurantTable;
import cz.kucharo2.service.CashDeskService;
import cz.kucharo2.service.MenuService;
import cz.kucharo2.service.OrderingService;
import cz.kucharo2.service.TableService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * Created by Roman on 12/2/2014.
 */
@ApplicationScoped
@Transactional
public class OrderingServiceImpl implements OrderingService {
	
	@Inject
	private CashDeskService cashDeskService;

	@Inject
	private TableService tableService;

	@Inject
	private MenuService menuService;

	@Inject
	private BillItemDao billItemDao;

	@Inject
	private BillDao billDao;

	@Override
	public Bill orderItem(int itemId, Integer billId, int tableId) {
		Bill bill = null;
		if (billId == null) {
			RestaurantTable table = tableService.getTable(tableId);
			bill = cashDeskService.createBillOnTable(table);
		} else {
			bill = cashDeskService.getBillById(billId);
		}
		Item item = menuService.getItemById(itemId);
		cashDeskService.createBillItem(createBillItem(item, bill));
		return cashDeskService.getBillById(bill.getId());
	}

	@Override
	public boolean removeItemFomOrder(int billItemId) {
		return billItemDao.delete(billItemDao.getById(billItemId));
	}

	@Override
	public Bill confirmBill(int billId) {
		Bill bill = billDao.getById(billId);
		billDao.createOrUpdate(bill);
		return bill;
	}

	@Override
	public Bill getBillById(int billId) {
		return billDao.getById(billId);
	}

	/**
	 * Creates a bill item from item
	 *
	 * @param item item
	 * @param bill bill on witch is ordered
	 *
	 * @return created bill item food
	 */
	private BillItem createBillItem(Item item, Bill bill) {
		BillItem billItemFood = new BillItem();
		billItemFood.setItem(item);
		billItemFood.setPrice(item.getPrice());
		billItemFood.setBill(bill);
		return billItemFood;
	}

}
