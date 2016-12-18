package com.almatarm.simpleclientserver.network;

/**
 * A list of commands that the client can request form the server, see
 * {@link com.almatarm.simpleclientserver.access.Access}.
 * 
 * @author <a href="mailto:almatarm@gmail.com">Mufeed H. AlMatar</a>
 * @version 1.0
 */
public enum Command {
    /** Echo a message in ALL-CAPS back to client */
    ECHO,
    /** Used for uninitialized command object */
    UNSPECIFIED,
    /** Client disconnecting */
    DISCONNECT
    ;
}
