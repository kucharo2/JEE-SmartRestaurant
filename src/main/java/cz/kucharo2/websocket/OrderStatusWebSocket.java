package cz.kucharo2.websocket;

import cz.kucharo2.event.OrderStatusChange;
import org.jboss.logging.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */
@ServerEndpoint("/orderStatusWebSocket")
public class OrderStatusWebSocket {

    @Inject
    private Logger logger;

    private static final Set<Session> SESSIONS = Collections.synchronizedSet(new HashSet<Session>());

    /**
     * Handle order status change and notify all webSocket consumers.
     * Message template: <orderId>-<orderStatus>
     *
     * @param orderStatusChange order status change event
     */
    public void handleOrderStatusChange(@Observes OrderStatusChange orderStatusChange) {
        final String message = orderStatusChange.getOrder().getId() + "-" + orderStatusChange.getOrder().getStatus().name();
        sendAll(message);
    }


    /**
     * Sends message to all sessions.
     * @param message message
     */
    private static void sendAll(String message) {
        synchronized (SESSIONS) {
            for (Session session : SESSIONS) {
                if (session.isOpen()) {
                    session.getAsyncRemote().sendText(message);
                }
            }
        }
    }


    @OnOpen
    public void onOpen(Session session) {
        logger.info(session.getId() + " has opened a connection");
        SESSIONS.add(session);
        logger.info("Set size for websocket" + SESSIONS.size());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("Message from " + session.getId() + ": " + message);

    }

    @OnClose
    public void onClose(Session session) {
        logger.info("Session " +session.getId()+" has ended");
    }
}
