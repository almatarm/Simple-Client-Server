package com.almatarm.simpleclientserver.network;

import com.almatarm.simpleclientserver.util.MessageHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author <a href="mailto:almatarm@gmail.com">Mufeed H. AlMatar</a>
 * @version 1.0
 */
public class Server implements Runnable {
    /**
     * default server host/IP 
     */
    public static final String DEFAULT_HOST = "localhost";
    
    /**
     * default port for the server to listen for client.
     */
    public static final int DEFAULT_PORT = 10670;
    
    /**
     * The logger instance. We use a logger per class.
     */
    private static final Logger logger = Logger.getLogger(
            Server.class.getName());
    
    /**
     * The port for the server to listen for client.
     */
    private int port;

    /**
     * Construct a new <code>Server</code> using default port.
     */
    public Server() {
        this(DEFAULT_PORT);
    }

    
    /**
     * Construct a new <code>Server</code> 
     * @param port The port for the server to listen for client.
     */
    public Server(int port) {
        this.port = port;
    }

    /**
     * Listens for client connections and creating thread for each request
     */
    @Override
    public void run() {
        try {
            //Create the socket server.
            ServerSocket server = new ServerSocket(port);
            logger.log(Level.INFO, "Server is listen on port {0}", port);
            
            //listen to clients request and execute them
            while(true) {
                Socket client = server.accept();
                Request request = new Request(client);
                new Thread(request).start();
                logger.fine("New client connected!");
            }
            
        } catch (IOException ex) {
            MessageHandler.showMessage(String.format(
                    "Server Failure: %s %nThe system will now exit.", ex.getMessage()), 
                    null, ex, MessageHandler.Type.FATAL);
        }
    }

}
