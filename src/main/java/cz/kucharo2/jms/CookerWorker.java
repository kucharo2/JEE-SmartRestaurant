package cz.kucharo2.jms;

import cz.kucharo2.data.entity.Order;
import cz.kucharo2.data.enums.OrderStatus;
import cz.kucharo2.event.OrderStatusChange;
import cz.kucharo2.service.OrderingService;
import org.jboss.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = MessageProducer.ORDERS_QUEUE_NAME)
})
public class CookerWorker implements MessageListener {

    @Inject
    private Logger logger;

    @Inject
    private OrderingService orderingService;

    @Inject
    private Event<OrderStatusChange> orderStatusChangeEvent;

    @Override
    public void onMessage(Message message) {
        if (message instanceof MapMessage) {
            MapMessage mapMessage = (MapMessage) message;
            try {
                logger.info("JSM accepted");
                Thread.sleep(5000);

                Order order = orderingService.getOrderById(mapMessage.getInt("orderId"));
                if (order.getStatus() == OrderStatus.CONFIRMED) {
                    order.setStatus(OrderStatus.FINISHED);
                    orderStatusChangeEvent.fire(new OrderStatusChange(order));
                }
            } catch (JMSException e) {
                logger.error("Cannot retrieve text from the message", e);
            } catch (InterruptedException e) {
                logger.error("Cooker has to go poop, so he was interrupted.", e);
            }
        } else {
            logger.error("Incorrect message type, expected: javax.jms.MapMessage, received: " + message.getClass());
        }
    }
}
