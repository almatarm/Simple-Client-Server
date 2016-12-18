package com.almatarm.simpleclientserver.gui;

import com.almatarm.simpleclientserver.network.Server;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ToolTipManager;

/**
 * This is the start point of the application, i.e. the main class. It will 
 * parse the command line argument and present the user with configuration 
 * dialog.
 * 
 * @author <a href="mailto:almatarm@gmail.com">Mufeed H. AlMatar</a>
 * @version 1.0
 */
public class Starter {
    /**
     * The logger instance. We use a logger per class.
     */
    private static final Logger logger = Logger.getLogger(
            Starter.class.getName());
    
    public static void main(String args[]) {        
        //Parse the agruments 
        //Which mode the application is running on
        ApplicationMode mode = null;
        if(args.length == 0) {
            //No argument provided 
            mode = ApplicationMode.NETWORK;
        } else {
            //We have at least one argument but we only care about the first 
            //one; ignore the others
            if(args[0].equals(ApplicationMode.SERVER.toString().toLowerCase())){
                mode = ApplicationMode.SERVER;
            
            } else if(args[0].equals(ApplicationMode.ALONE.toString()
                    .toLowerCase())){
                mode = ApplicationMode.ALONE;
            
            } else {
                //Invalid argument passed.
                System.err.println("Argument can be one of the following:\n"
                        + "server         run server only\n"
                        + "alone          run standalone application without "
                        +                 "networking\n"
                        + "               empty argument: run network client");
                logger.log(Level.INFO, "Invalid argument passed: {0}", args[0]);
                System.exit(-1);
            }
        }
        logger.log(Level.INFO, "Starting the application in {0} mode.", mode);
        
        ToolTipManager.sharedInstance().setDismissDelay(3000);
        //Present the user with Configuration dialog 
        RunConfigurationDialog configDialog = new RunConfigurationDialog(mode);
        configDialog.setModal(true);
        configDialog.setVisible(true);
        ConfigManager config = configDialog.getConfig();
        mode = configDialog.getMode(); //Get Mode from the dialog
             
        switch(mode) {
            case SERVER:
                //Run server window
                Server server = new Server(
                        Integer.parseInt(config.getProperty(ConfigManager.Key.PORT)));
                new Thread(server).start();
                ServerWindow serverWindow = new ServerWindow(
                        config.getProperty(ConfigManager.Key.PORT));
                serverWindow.setVisible(true);                
                break;
            case NETWORK:
            case ALONE:
                //run client window
                MainWindow window = new MainWindow(mode);
                window.setVisible(true);
                break;
        }
    }
}
