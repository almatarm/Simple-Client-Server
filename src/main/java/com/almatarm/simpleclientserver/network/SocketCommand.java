package com.almatarm.simpleclientserver.network;

import java.io.Serializable;

/**
 * Encapsulates a client request to the server by storing the 
 * command, see {@link Command}, and its data.
 * 
 * @author <a href="mailto:almatarm@gmail.com">Mufeed H. AlMatar</a>
 * @version 1.0
 */
public class SocketCommand implements Serializable {
    /**
     * A class serialization version number that is used for serialization 
     * without worrying about the underlying class changing between
     * serialization and deserialization.
     */
    private static final long serialVersionUID = 5484841971932L;
    
    /**
     * The command that the client will request form the server. 
     * There is a one-to-one mapping between <code>Command</code> and 
     * functions in <code>Access</code>. 
     */
    private Command command;
    
    /**
     * message is a input parameter to some of <code>Command</code>s.
     */
    private String message;
    
    /**
     * Default No-parameter constructor.
     */
    public SocketCommand() {
        this.command = Command.UNSPECIFIED;
    }

    /**
     * Construct <code>SocketCommand</code> object.
     * 
     * @param command The command that the client will request form the server.
     */
    public SocketCommand(Command command) {
        this.command = command;
    }

    /**
     * Construct <code>SocketCommand</code> object.
     * 
     * @param command The command that the client will request form the server.
     * @param message Message used as a parameter for the supplied command.
     */
    public SocketCommand(Command command, String message) {
        this.command = command;
        this.message = message;
    }

    /**
     * Get the command that the client will request form the server.
     * @return The command that the client will request form the server. 
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Set the command that the client will request form the server.
     * @param command The command that the client will request form the server.
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * Get the message used as a parameter for the command
     * @return message used as a parameter for the command
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message used as a parameter for the command
     * @param message Message used as a parameter for the command
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
