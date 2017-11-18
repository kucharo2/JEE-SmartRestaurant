package cz.kucharo2.service.impl;

import cz.kucharo2.common.model.AddOrderItemModel;
import cz.kucharo2.data.dao.BillDao;
import cz.kucharo2.data.dao.BillItemDao;
import cz.kucharo2.data.entity.Bill;
import cz.kucharo2.data.entity.BillItem;
import cz.kucharo2.data.entity.Item;
import cz.kucharo2.data.entity.RestaurantTable;
import cz.kucharo2.data.enums.BillStatus;
import cz.kucharo2.data.enums.CategoryType;
import cz.kucharo2.service.CashDeskService;
import cz.kucharo2.service.MenuService;
import cz.kucharo2.service.OrderingService;
import cz.kucharo2.service.TableService;
import cz.kucharo2.service.exception.ServiceException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 12/2/2014.
 */
@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
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
	public Bill orderItem(AddOrderItemModel model) throws ServiceException {
		if (model.getItemsToAdd().length < 1) {
			throw new IllegalArgumentException("At least one item id must be set.");
		}
		Bill bill;
		if (model.getBillId() == null) {
			// create new bill
			RestaurantTable table = tableService.getTable(model.getTableId());
			bill = cashDeskService.createBillOnTable(table);
		} else {
			bill = cashDeskService.getBillById(model.getBillId());
		}
		if (bill.getStatus() != BillStatus.CREATED) {
			throw new ServiceException("Not able to add another item on already confirmed order.");
		}
		List<Item> itemsToBeAddToBill = new ArrayList<>();
		BillItem mainFood = null;
		for (Integer itemId : model.getItemsToAdd()) {
			Item item = menuService.getItemById(itemId);

			if (mainFood == null && item.getCategory().getParentCategory().getCode() == CategoryType.MAIN_FOOD
					&& item.getCategory().getCode() != CategoryType.PRILOHA) {
				mainFood = createBillItem(item, bill);
				cashDeskService.createBillItem(mainFood);
			} else {
				itemsToBeAddToBill.add(item);
			}
		}
		for (Item item : itemsToBeAddToBill) {
			BillItem billItem = createBillItem(item, bill);

            if (mainFood != null) {
            	billItem.setParentBillItem(mainFood);
			}
			cashDeskService.createBillItem(billItem);
		}
		return cashDeskService.getBillById(bill.getId());
	}

	@Override
	public boolean removeItemFomOrder(int billItemId) throws ServiceException {
		Bill bill = billItemDao.getById(billItemId).getBill();
		if (bill.getStatus() != BillStatus.CREATED){
			throw new ServiceException("Cannot delete item from order, because it's in different state than CREATED");
		}

		return billItemDao.delete(billItemDao.getById(billItemId));
	}

	@Override
	public Bill confirmBill(int billId) throws ServiceException {
		Bill bill = billDao.getById(billId);
		if (bill.getStatus() != BillStatus.CREATED) {
			throw new ServiceException("Cannot confirm order in different state than CREATED");
		}
		bill.setStatus(BillStatus.CONFIRMED);
		billDao.createOrUpdate(bill);
		return bill;
	}

	@Override
	public Bill getBillById(int billId) {
		return billDao.getById(billId);
	}

	@Override
	public Bill getCreatedBillOnTable(int tableId) {
		return billDao.getCreatedBillOnTable(tableId);
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
