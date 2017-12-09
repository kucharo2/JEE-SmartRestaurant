package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.OrderItemDao;
import cz.kucharo2.data.entity.OrderItem;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String query = OrderItem.ORDER_ID + " = :orderId and " + OrderItem.PAID + " = :paid";
        Map<String, Object> params = new HashMap<>();

        params.put("orderId", orderId);
        params.put("paid", false);

        return getByWhereCondition(query, params);
    }
}
