package com.almatarm.simpleclientserver.access;

/**
 * A singleton implementation of the Access services
 * 
 * @author <a href="mailto:almatarm@gmail.com">Mufeed H. AlMatar</a>
 * @version 1.0
 */
public class AccessImpl implements Access {

    private AccessImpl() {
    }

    /**
     * Get an instance of this class
     * @return an instance of AccessImpl
     */
    public static AccessImpl getInstance() {
        return AccessImplHolder.INSTANCE;
    }

    private static class AccessImplHolder {
        private static final AccessImpl INSTANCE = new AccessImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String echo(String message) {
        if(message != null) {
            return message.toUpperCase();
        }
        return message;
    }
 }
