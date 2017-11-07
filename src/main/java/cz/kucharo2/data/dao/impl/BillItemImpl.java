package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.BillItemDao;
import cz.kucharo2.data.entity.BillItem;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@ApplicationScoped
public class BillItemImpl extends AbstractGenericDaoImpl<BillItem> implements BillItemDao {

    public BillItemImpl() {
        super(BillItem.class);
    }

    @Override
    public List<BillItem> getUnpaidBillItemByBill(int billId) {
        String query = BillItem.BILL_ID + " = :billId and " + BillItem.PAID + " = :paid";
        Map<String, Object> params = new HashMap<>();

        params.put("billId", billId);
        params.put("paid", false);

        return getByWhereCondition(query, params);
    }
}
