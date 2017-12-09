package cz.kucharo2.websocket;

import org.jboss.logging.Logger;

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
@ServerEndpoint("/newWebSocket")
public class WebSocket {

    @Inject
    private Logger logger;

    private static final Set<Session> SESSIONS = Collections.synchronizedSet(new HashSet<Session>());

    public void handleOrderStatusChange(/*@Observes */ ) {
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
