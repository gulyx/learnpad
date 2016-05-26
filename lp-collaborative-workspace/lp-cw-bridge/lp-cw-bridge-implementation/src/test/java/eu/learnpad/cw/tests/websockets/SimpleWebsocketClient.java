package eu.learnpad.cw.tests.websockets;

import java.io.IOException;
import java.net.URI;

import javax.inject.Inject;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.slf4j.Logger;

import eu.learnpad.cw.tests.websockets.handlers.SimpleMessageHandler;

/**
 * SimpleWebsocketsClient.
 *  This code has been taken and revised form the ChatServer Client by Jiji_Sasidharan 
 * 
 * @author Gulyx
 */
@ClientEndpoint
public class SimpleWebsocketClient {

	Session currentSession = null;
	private SimpleMessageHandler messageHandler;

	@Inject
	private Logger logger;
	
	public SimpleWebsocketClient(URI endpointURI) {
		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
// The invocation of this method should enable the configuration
// of the attribute this.currentSession, by calling the method onOpen			
			container.connectToServer(this, endpointURI);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Callback hook for Connection open events.
	 * 
	 * @param aSession
	 *            the new session which is opened.
	 */
	@OnOpen
	public void onOpen(Session aSession) {
		logger.info("opening websocket");
		this.currentSession = aSession;
	}

	/**
	 * Callback hook for Connection close events.
	 * 
	 * @param currentSession
	 *            the current session which is getting closed.
	 * @param reason
	 *            the reason for connection close
	 */
	@OnClose
	public void onClose(Session currentSession, CloseReason reason) {
		logger.info("closing websocket");
		try {
			currentSession.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.currentSession = null;
	}

	/**
	 * Callback hook for Message Events. This method will be invoked when a
	 * client send a message.
	 * 
	 * @param message
	 *            The text message
	 */
	@OnMessage
	public void onMessage(String message) {
		if (this.messageHandler != null) {
			this.messageHandler.handleMessage(message);
		}
	}

	/**
	 * register message handler
	 * 
	 * @param msgHandler
	 */
	public void addMessageHandler(SimpleMessageHandler msgHandler) {
		this.messageHandler = msgHandler;
	}

	/**
	 * Send a message.
	 * 
	 * @param message
	 */
	public void sendMessage(String message) {
		this.currentSession.getAsyncRemote().sendText(message);
	}

}