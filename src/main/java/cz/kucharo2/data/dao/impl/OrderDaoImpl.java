package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.OrderDao;
import cz.kucharo2.data.entity.Order;
import cz.kucharo2.data.entity.OrderItem;
import cz.kucharo2.data.enums.OrderStatus;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@ApplicationScoped
public class OrderDaoImpl extends AbstractGenericDaoImpl<Order> implements OrderDao {

    public OrderDaoImpl() {
        super(Order.class);
    }

    public List<Order> getUnpaidOrderOnTable(int tableID) {
        String query = Order.BILL_ID + " in (select " + OrderItem.ID_COLUMN + " from " + OrderItem.TABLE_NAME +
                " where " + OrderItem.PAID + " = :paid) and " + Order.TABLE_ID + " = :tableId";

        Map<String, Object> params = new HashMap<>();
        params.put("paid", false);
        params.put("tableId", tableID);

        return getByWhereCondition(query, params);
    }

    @Override
    public Order getCreatedOrderByTableAndUser(int tableId, int accountId) {
        Query query = getEntityManager().createQuery("select b from Order b left join fetch b.orderItems " +
                "where b.table.id = :tableId and b.account.id = :accountId and b.status = :status")
                .setParameter("tableId", tableId)
                .setParameter("accountId", accountId)
                .setParameter("status", OrderStatus.CREATED);

        try {
            return (Order) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Order getOrderWithItems(int orderId) {
        Query query = getEntityManager().createQuery("select b from Order b left join fetch b.orderItems where b.id = :orderId")
                .setParameter("orderId", orderId);
        try {
            return (Order) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
