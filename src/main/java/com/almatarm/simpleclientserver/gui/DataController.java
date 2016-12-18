package com.almatarm.simpleclientserver.gui;

import com.almatarm.simpleclientserver.access.Access;
import com.almatarm.simpleclientserver.access.AccessImpl;
import com.almatarm.simpleclientserver.network.Client;
import com.almatarm.simpleclientserver.util.MessageHandler;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Provides Search and Book services to GUI components. 
 * 
 * @author <a href="mailto:almatarm@gmail.com">Mufeed H. AlMatar</a>
 * @version 1.0
 */
public class DataController {
    
    
    /**
     * The logger instance. We use a logger per class.
     */
    private static final Logger logger = Logger.getLogger(
            DataController.class.getName());
    
    /**
     * Application Mode
     */
    ApplicationMode mode;
    
    /**
     * interface that provides services
     */
    private Access access;
   
    private ConfigManager config = ConfigManager.getInstance();
    /**
     * Construct <code>DataController</code> object
     * @param mode application mode
     */
    public DataController(ApplicationMode mode) {
        this.mode = mode;
        try {
            switch(mode) {
                case ALONE:
                    //local access
                    access = AccessImpl.getInstance();
                    break;
                case NETWORK:
                    //network
                    String host = config.getProperty(ConfigManager.Key.SERVER_HOST);
                    String port = config.getProperty(ConfigManager.Key.PORT); 
                    access = new Client(host, port);
                    break;
                default:
                    //Cann't continue
                    MessageHandler.showMessage(MessageHandler.INTERNAL_ERROR, 
                            null, null, MessageHandler.Type.FATAL);
            }
        } catch (IOException ex) {
            MessageHandler.showMessage(
                    MessageHandler.NETWORK_ERROR, null, ex, 
                    MessageHandler.Type.FATAL);
        }
    }
    
    public String echo(String message) {
        return access.echo(message);
    }
     
    /**
     * Disconnect clients from the server.
     */
    public void disconnect() {
        if(mode == ApplicationMode.NETWORK) {
            try {
                ((Client) access).disconnect();
            } catch (IOException ex) {
                //log the message; we are exiting anyway.
                logger.severe(ex.getMessage());
            }
        }
    }
}
