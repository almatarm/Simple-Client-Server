package com.almatarm.simpleclientserver.util;

import java.text.MessageFormat;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Provides static method to show messages to the user
 * 
 * @author <a href="mailto:almatarm@gmail.com">Mufeed H. AlMatar</a>
 * @version 1.0
 */
public class MessageHandler {
    /**
     * Message Type
     */
    public static enum Type {
        /** FATAL Message which cause the system to shutdown */
        FATAL,
        /** WARNING Message */
        WARNING,
        /** Information Message */
        INFO;
    }
    
    /** Database network error message */
    public static final String NETWORK_ERROR =
            "Network Failure: Make the the server is running.";
    
    /** Internal application error error message */
    public static final String INTERNAL_ERROR = 
            "Internal Application Error!\n"
            + "Please contact system administrator.";
    
    /**
     * The logger instance. We use a logger per class.
     */
    private static final Logger logger = Logger.getLogger(
            MessageHandler.class.getName());
    
    /**
     * SHow a warning dialog box message to the user
     * @param msg the message to be shown to the user.
     */
    public static void warn(String msg) {
        showMessage(msg, null, null, Type.WARNING);
    }
    
    /**
     * Show a dialog box with a message to the user.
     * @param msg Initial message to shown to the user. Other message might be
     * appended to this message like the exception cause.
     * @param args argument for formatting the message, see {@link 
     * MessageFormat#format(java.lang.String, java.lang.Object...)}.
     * @param ex The source exception if exists. 
     * @param messageType message type, e.g. FATAL, WARNNING or INFO.
     */
    public static void showMessage(String msg, Object[] args, Exception ex,
            Type messageType) {
        //Create message if needed
        String message = msg;
        String exMessage = ex == null ? "" : ex.getMessage();
        if(args != null) {
            message = MessageFormat.format(msg, args);
        }
        
        //Get the right icon for the dialog
        //Get title and log the message
        int type = JOptionPane.PLAIN_MESSAGE;
        String title = "";
        String logMessage = message + "\nException Message:" + exMessage;
        switch(messageType) {
            case FATAL:
                type  = JOptionPane.ERROR_MESSAGE;
                title = "Error!"; 
                logger.severe(logMessage);
                message += "\nThe applicatoin will no exit!";
                break;
            case WARNING:
                type  = JOptionPane.WARNING_MESSAGE;
                title = "Warning!";
                logger.warning(logMessage);
                break;
            case INFO:
                type  = JOptionPane.INFORMATION_MESSAGE;
                title = "Information";
                logger.info(logMessage);
                break; 
        }
        
        //Show the message to the user
        JOptionPane.showMessageDialog(null, message, title, type);

        //fatel messages quit the applications
        if (messageType == Type.FATAL) {
            System.exit(-1);
        }
    }
    
}
