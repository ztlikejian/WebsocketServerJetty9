package com.likejian.websocket;

import javax.servlet.ServletException;
import javax.websocket.server.ServerContainer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import com.likejian.thread.ChatThread;
import com.likejian.thread.SendPingThread;

public class EventServer {
	
	public static void main(String[] args) {
		try {
			init();
			
			Server server = new Server();
			ServerConnector connector = new ServerConnector(server);
			connector.setPort(8081);
			server.addConnector(connector);

			// Setup the basic application "context" for this application at "/"
			// This is also known as the handler tree (in jetty speak)
			ServletContextHandler context = new ServletContextHandler(
					ServletContextHandler.SESSIONS);
			context.setContextPath("/WebsocketServerJetty9");
			server.setHandler(context);
			
			// Initialize javax.websocket layer
			ServerContainer wscontainer = WebSocketServerContainerInitializer
					.configureContext(context);

			// Add WebSocket endpoint to javax.websocket layer
			wscontainer.addEndpoint(EventSocket.class);

			server.start();
			server.dump(System.err);
			server.join();
		} catch (Throwable t) {
			t.printStackTrace(System.err);
		}
	}
	
	public static void init() throws ServletException {
		ChatThread chatThread = new ChatThread();
		chatThread.setDaemon(true);
		chatThread.start();
		
		new SendPingThread().start();
	}
}
