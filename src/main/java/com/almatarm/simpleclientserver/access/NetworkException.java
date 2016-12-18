package com.almatarm.simpleclientserver.access;

/**
 * Signals that an I/O exception of some sort has occurred. 
 * 
 * @author <a href="mailto:almatarm@gmail.com">Mufeed H. AlMatar</a>
 * @version 1.0
 */
public class NetworkException extends RuntimeException {

    /**
     * Creates <code>DatabaseIOException</code> without detail message.
     */
    public NetworkException() {
    }

    /**
     * Constructs <code>DatabaseIOException</code> with the specified detail 
     * message.
     *
     * @param msg the detail message.
     */
    public NetworkException(String msg) {
        super(msg);
    }
    
    /**
     * Constructs <code>DatabaseIOException</code> with the specified detail 
     * message.
     *
     * @param msg the detail message.
     * @param cause the cause of the exception, retrieval by calling 
     * Throwable.getCause() method.(A null value indicates that the cause is 
     * does not exist or unknown.
     */
    public NetworkException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
