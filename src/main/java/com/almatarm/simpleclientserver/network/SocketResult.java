package com.almatarm.simpleclientserver.network;

import java.io.Serializable;

/**
 * Encapsulates a result from a client request to the server. The 
 * result might be a return value form one of <code>Access</code> methods or 
 * an exception thrown by them.
 * 
 * @author <a href="mailto:almatarm@gmail.com">Mufeed H. AlMatar</a>
 * @version 1.0
 */
public class SocketResult implements Serializable {
    /**
     * A class serialization version number that is used for serialization 
     * without worrying about the underlying class changing between
     * serialization and deserialization.
     */
    private static final long serialVersionUID = 5484841971932L;
    
    /**
     * Stores the message returned from some of <code>Access</code> methods.
     */
    private String message;
    
    /**
     * Holds the thrown exception in case of the request ended with an 
     * exception.
     */
    private Exception exception;

    /**
     * Default no-parameter Constructor
     */
    public SocketResult() {
    }
    
    /**
     * Construct a <code>SocketResult</code> object.
     * 
     * @param message Represents a message returned from some of 
     * <code>Access</code> methods.
     */
    public SocketResult(String message) {
        this.message = message;
    }

    /**
     * Construct a <code>SocketResult</code> object.
     *  
     * @param exception the thrown exception in case of the request ended with 
     * an exception.
     */
    public SocketResult(Exception exception) {
        this.exception = exception;
    }

     /**
     * Get a message returned from some of <code>Access</code> methods.
     * @return a message returned from some of <code>Access</code> methods.
     * methods.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set a message returned from some of <code>Access</code> methods.
     * @param message a message returned from some of <code>Access</code> 
     * methods.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get the thrown exception in case of the request ended with an exception.
     * @return The thrown exception in case of the request ended with an 
     * exception.
     */
    public Exception getException() {
        return exception;
    }

    /**
     * Sets the thrown exception in case of the request ended with an exception.
     * @param exception the thrown exception in case of the request ended with 
     * an exception.
     */
    public void setException(Exception exception) {
        this.exception = exception;
    }
    
    /**
     * Check if we encounter an exception
     * @return true if we encountered and exception.
     */
    public boolean hasException() {
        return exception != null;
    }
    
}
