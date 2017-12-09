package cz.kucharo2.event;

import cz.kucharo2.data.entity.Order;

/**
 * @Author Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */
public class OrderStatusChange {
    private Order order;

    public OrderStatusChange(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
