package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.OrderDao;
import cz.kucharo2.data.entity.Order;
import cz.kucharo2.data.enums.OrderStatus;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@ApplicationScoped
public class OrderDaoImpl extends AbstractGenericDaoImpl<Order> implements OrderDao {

    public OrderDaoImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> getUnpaidOrderOnTable(int tableID) {

        Query query = getEntityManager().createQuery("select e from Order e left join fetch e.orderItems where e.id  in (select i.order.id " +
                "from OrderItem i where i.paid = :paid) and e.table.id = :tableId and e.status = :status")
                .setParameter("paid", false)
                .setParameter("tableId", tableID)
                .setParameter("status", OrderStatus.FINISHED);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Order> getUnpaidFinishedOrdersOnTableByUser(int tableID, int accountID) {
        Query query = getEntityManager().createQuery("select e from Order e where e.id in (select i.order.id " +
                "from OrderItem i where i.paid = :paid) and e.table.id = :tableId and e.status = :status and e.account.id = :accountId")
                .setParameter("paid", false)
                .setParameter("tableId", tableID)
                .setParameter("status", OrderStatus.FINISHED)
                .setParameter("accountId", accountID);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Order getCreatedOrderByTableAndUser(int tableId, int accountId) {
        Query query = getEntityManager().createQuery("select b from " + Order.class.getName() + " b left join fetch b.orderItems " +
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
