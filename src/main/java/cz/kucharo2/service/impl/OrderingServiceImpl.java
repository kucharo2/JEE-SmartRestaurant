package cz.kucharo2.service.impl;

import cz.kucharo2.common.model.AddOrderItemModel;
import cz.kucharo2.data.dao.BillDao;
import cz.kucharo2.data.dao.BillItemDao;
import cz.kucharo2.data.entity.*;
import cz.kucharo2.data.enums.BillStatus;
import cz.kucharo2.data.enums.CategoryType;
import cz.kucharo2.rest.model.SessionContext;
import cz.kucharo2.service.CashDeskService;
import cz.kucharo2.service.MenuService;
import cz.kucharo2.service.OrderingService;
import cz.kucharo2.service.TableService;
import cz.kucharo2.service.exception.ServiceException;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Roman on 12/2/2014.
 */
@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class OrderingServiceImpl implements OrderingService {

    @Inject
    private Logger logger;

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

    @Inject
    private SessionContext sessionContext;

    @Override
    public Integer orderItem(AddOrderItemModel model) throws ServiceException {
        if (model.getItemsToAdd().length < 1) {
            throw new IllegalArgumentException("At least one item id must be set.");
        }
        Bill bill;
        if (model.getBillId() == null) {
            // check opened order on table
            bill = getCreatedBillOnTable(model.getTableId());
            if (bill == null) {
                // create new bill
                logger.info("Creating new order on table " + model.getTableId());
                RestaurantTable table = tableService.getTable(model.getTableId());
                bill = cashDeskService.createBillOnTable(table);
            }
        } else {
            bill = getBillById(model.getBillId());
            Account loggedAccount = sessionContext.getLoggedAccount();
            if(bill.getAccount().isAnnonymousAccount() && !loggedAccount.isAnnonymousAccount()) {
                bill.setAccount(loggedAccount);
                billDao.createOrUpdate(bill);
            } else if (!bill.getAccount().getId().equals(loggedAccount.getId())) {
                throw new ServiceException("Cannot add bill item into bill that belongs to another account");
            }
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
        return bill.getId();
    }

    @Override
    public Integer removeItemFomOrder(int billItemId) throws ServiceException {
        Bill bill = billItemDao.getById(billItemId).getBill();
        if (bill.getStatus() != BillStatus.CREATED) {
            throw new ServiceException("Cannot delete item from order, because it's in different state than CREATED");
        }
        billItemDao.delete(billItemDao.getById(billItemId));
        return bill.getId();
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
    public Bill cancelBIll(int billId) throws ServiceException {
        Bill bill = billDao.getById(billId);
        if (bill.getStatus() != BillStatus.CREATED) {
            throw new ServiceException("Cannot cancel order in different state than CREATED");
        }
        bill.setStatus(BillStatus.CANCELED);
        billDao.createOrUpdate(bill);
        return bill;
    }

    @Override
    public Bill getBillById(int billId) {
        return billDao.getBillWithItems(billId);
    }

    @Override
    public Bill getCreatedBillOnTable(int tableId) {
        Account loggedAccount = sessionContext.getLoggedAccount();
        return billDao.getCreatedBillByTableAndUser(tableId, loggedAccount.getId());
    }

    /**
     * Creates a bill item from item
     *
     * @param item item
     * @param bill bill on witch is ordered
     * @return created bill item food
     */
    private BillItem createBillItem(Item item, Bill bill) {
        BillItem billItemFood = new BillItem();
        billItemFood.setItem(item);
        billItemFood.setPrice(item.getPrice());
        billItemFood.setBill(bill);
        billItemFood.setCreated(new Date());
        return billItemFood;
    }

}
