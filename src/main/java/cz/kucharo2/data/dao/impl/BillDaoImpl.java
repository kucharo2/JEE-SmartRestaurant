package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.BillDao;
import cz.kucharo2.data.entity.Bill;
import cz.kucharo2.data.entity.BillItem;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@ApplicationScoped
public class BillDaoImpl extends AbstractGenericDaoImpl<Bill> implements BillDao {

    public BillDaoImpl() {
        super(Bill.class);
    }

    public List<Bill> getUnpaidBillOnTable(int tableID) {
        String query = Bill.BILL_ID + " in (select " + BillItem.ID_COLUMN + " from " + BillItem.TABLE_NAME +
                " where " + BillItem.PAID + " = :paid) and " + Bill.TABLE_ID + " = :tableId";

        Map<String, Object> params = new HashMap<>();
        params.put("paid", false);
        params.put("tableId", tableID);

        return getByWhereCondition(query, params);
    }

    @Override
    public Bill getCreatedBillByTableAndUser(int tableId, int accountId) {
        String query =  Bill.TABLE_ID + " = :tableId and " + Bill.ACCOUNT_ID + " = :accountId" + Bill.STATUS + " = 'CREATED'";
        Map<String, Object> params = new HashMap<>();
        params.put("tableId", tableId);
        params.put("accountId", accountId);

        return getByWhereConditionSingleResult(query, params);
    }

    @Override
    public Bill getBillWithItems(int billId) {
        Query query = getEntityManager().createQuery("select b from Bill b left join fetch b.billItems where b.id = :billId")
                .setParameter("billId", billId);
        return (Bill) query.getSingleResult();
    }
}
