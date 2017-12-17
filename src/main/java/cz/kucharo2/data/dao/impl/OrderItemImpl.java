package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.OrderItemDao;
import cz.kucharo2.data.entity.OrderItem;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@ApplicationScoped
public class OrderItemImpl extends AbstractGenericDaoImpl<OrderItem> implements OrderItemDao {

    public OrderItemImpl() {
        super(OrderItem.class);
    }

    @Override
    public List<OrderItem> getUnpaidOrderItemByOrder(int orderId) {
        Query query = getEntityManager().createQuery("select e from OrderItem e where e.order.id = :orderId and e.paid = :paid")
                .setParameter("paid", false)
                .setParameter("orderId", orderId);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
