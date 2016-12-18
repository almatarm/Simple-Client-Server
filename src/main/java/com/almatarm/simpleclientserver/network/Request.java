package com.almatarm.simpleclientserver.network;

import com.almatarm.simpleclientserver.access.AccessImpl;
import com.almatarm.simpleclientserver.access.Access;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This class represents one client request to one of the method in <code>
 * Access</code> interface. It uses <code>SocketCommand</code> to specify the
 * method and to store its parameter values and it uses <code>SocketResult
 * </code> to get the output result executing that command. So basically, this 
 * class is our application protocol.
 * 
 * @author <a href="mailto:almatarm@gmail.com">Mufeed H. AlMatar</a>
 * @version 1.0
 */
public class Request implements Runnable {
    /**
     * The logger instance. We use a logger per class.
     */
    private static final Logger logger = Logger.getLogger(Request.class.getName());
    
    /**
     * Client socket
     */
    private final Socket client;
    
    /**
     * Our Access interface
     */
    private final Access access;

    /**
     * Construct <code>Request</code> object
     * 
     * @param client Client Socket that listens for request.
     */
    public Request(Socket client) {
        this.client = client;
        this.access = AccessImpl.getInstance();
        
        logger.fine("New socket request created!");
    }

    /**
     * In this method client made request and server execute them and write back
     * the result to the client. 
     */
    @Override
    public void run() {
        try {
            //Get input and output stream to client
            ObjectOutputStream out = new ObjectOutputStream(
                    client.getOutputStream());
            ObjectInputStream  in = new ObjectInputStream(
                            client.getInputStream());
            
            //1. Client made a request stored in cmd
            //2. Server execute the command in cmd
            //3. Server write the result back to the client.
            //4. Go to step 1.
            while(true) {
                SocketCommand cmd = (SocketCommand) in.readObject();
                out.writeObject(execute(cmd));
                
                //Break the loop on clinet request
                if(cmd.getCommand() == Command.DISCONNECT) {
                    logger.fine("Client disconnected!");
                    break;
                }
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Socket Server IOException: {0}", 
                    ex.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            logger.log(Level.SEVERE,"Socket Server ClassNotFoundException: {0}",
                    ex.getMessage());
        } 
    }
    
    /**
     * Execute the supplied command and return the result which could
     * be an exception if the execution encounter one.
     * 
     * @param cmd The command to be executed in the server.
     * @return The return value from executing the command. The value might be
     * an exception if the execution encounter one.
     */
    private SocketResult execute(SocketCommand cmd) {
        logger.log(Level.FINE, "Executing coomand: {0}", cmd);
        
        //Return value
        SocketResult result = null;
        
        try {
            //which command the client requested
            switch(cmd.getCommand()) {
                case ECHO:
                    String message = access.echo(cmd.getMessage());
                    result = new SocketResult();
                    result.setMessage(message);
                    break;
                default:

            }
        } catch (Exception ex) {
            //Exception occured
            result = new SocketResult(ex);
            logger.fine("Command execution failed!");
        }
        return result;
    }
}
