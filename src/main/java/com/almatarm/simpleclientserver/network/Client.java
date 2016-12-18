package com.almatarm.simpleclientserver.network;

import com.almatarm.simpleclientserver.access.Access;
import com.almatarm.simpleclientserver.access.NetworkException;
import com.almatarm.simpleclientserver.gui.Constants;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A client socket that exposes all <code>Access</code> methods.
 * 
 * @author <a href="mailto:almatarm@gmail.com">Mufeed H. AlMatar</a>
 * @version 1.0'
 */
public class Client implements Access {
    /**
     * The logger instance. We use a logger per class.
     */
    private static final Logger logger = Logger.getLogger(Client.class.getName());

    /**
     * Server host name
     */
    private String host;
    
    /**
     * Server port
     */
    private int port;
    
    /**
     * Client socket
     */
    private Socket socket;
    
    /**
     * Handles server responses, i.e. reads serialized result objects
     */
    private ObjectInputStream in = null;
    
    /**
     * Writes serialized command objects to a socket server.
     */
    private ObjectOutputStream out = null;

    /**
     * Default constructor that assumes the server running locally and that it 
     * uses the default port.
     * 
     * @throws java.io.IOException if network error occurs
     */
    public Client() throws IOException {
       this(Constants.DEFAULT_HOST, Integer.toString(Constants.DEFAULT_PORT));
    }

    /** 
     * Construct a <code>Client</code> object
     * 
     * @param host server host name
     * @param portStr server listening port
     * @throws java.io.IOException if network error occurs
     */
    public Client(String host, String portStr) throws IOException {
        this.host = host;
        this.port = Integer.parseInt(portStr);
        
        //Initalize connection to the server, input and output streams 
        socket = new Socket(host, port);
        out    = new ObjectOutputStream(socket.getOutputStream());        
        in     = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String echo(String message) {
        try {
            SocketCommand cmd   = new SocketCommand(Command.ECHO, message);
            SocketResult result = getResult(cmd);
            
            //No Exception
            return result.getMessage();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Write <code>SocketCommand</code> to the server and read the output result
     * back <code>SocketResult</code>.
     * @param cmd The command to be executed
     * @return The return value from executing the command. The value might be
     * an exception if the execution encounter one.
     * @throws IOException if a network error occur.
     */
    private SocketResult getResult(SocketCommand cmd) throws IOException {
        try {
            //Write Socket Command to the server
            out.writeObject(cmd);
            
            //Read SocketResult object
            SocketResult result = (SocketResult) in.readObject();
            return result;
        } catch (ClassNotFoundException ex) {
            //wrap the ClassNotFoundException into a database IO exception 
            throw new NetworkException("A demarshelling I/O Error has "
                    + "occured!", ex);
        }
    }

    /**
     * disconnect this client from the server.
     * @throws IOException 
     */
    public void disconnect() throws IOException {
        SocketCommand cmd = new SocketCommand(Command.DISCONNECT);
        getResult(cmd);
    }
    
    /**
     * Close opened connections when this connection ends.
     *
     * @throws IOException if network error occur.
     */
    @Override
    public void finalize() throws java.io.IOException, Throwable {
        try {
            in.close();
            out.close();
            socket.close();
        } finally {
            super.finalize();
        }
    }
}
