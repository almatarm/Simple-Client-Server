package com.almatarm.simpleclientserver.gui;

/**
 * Different modes that the application can run on.
 * 
 * @author <a href="mailto:almatarm@gmail.com">Mufeed H. AlMatar</a>
 * @version 1.0
 */
public enum ApplicationMode {
    /**
     * Only the server is running, waiting for network clients.
     */
    SERVER,
    /**
     * GUI application is connecting directly to the server, no network is 
     * involved.
     */
    ALONE,
    /**
     * GUI application is connected to the server via network.
     */
    NETWORK;
}
