package cz.kucharo2.event;

import cz.kucharo2.data.entity.Order;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public class ConfirmOrderEvent {

    private Order order;

    public ConfirmOrderEvent(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
