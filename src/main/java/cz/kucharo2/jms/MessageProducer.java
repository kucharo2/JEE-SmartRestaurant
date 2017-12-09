package cz.kucharo2.jms;

import cz.kucharo2.event.ConfirmOrderEvent;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.jboss.logging.Logger;

import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.*;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@Singleton
public class MessageProducer {

    public static final String BILLS_QUEUE_NAME = "ordersQueue";

    @Inject
    private Logger logger;
    private ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);

    public void handleConfirmOrderEvent(@Observes ConfirmOrderEvent event) {
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(BILLS_QUEUE_NAME);
            javax.jms.MessageProducer producer = session.createProducer(queue);
            MapMessage message = session.createMapMessage();
//            message.setInt("seat", reservation.getSeat());
//            Flight flight = reservation.getFlight();
//            message.setDouble("price", flight.getPrice());
//            message.setString("from", flight.getFrom().getName());
//            message.setString("to", flight.getTo().getName());
//            message.setString("date", flight.getDate().toString());
            producer.send(queue, message);
        } catch (JMSException e) {
            logger.error("Cannot open JMS connection", e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException e) {
                logger.error("Cannot close JMS connection", e);
            }
        }
    }

}
