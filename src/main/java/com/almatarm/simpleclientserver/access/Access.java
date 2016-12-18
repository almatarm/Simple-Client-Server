package com.almatarm.simpleclientserver.access;

/**
 * An abstraction for classes that provide server services.
 *
 * @author <a href="mailto:almatarm@gmail.com">Mufeed H. AlMatar</a>
 * @version 1.0
 */
public interface Access {

    /**
     * Echo a message back to the client in ALL-CAPS
     * @param message the message
     * @return the message in ALL-CAPS
     */
    public String echo(String message);

}
