package com.almatarm.simpleclientserver.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author almatarm
 */
public class ConfigManager {
    
    public static final String CONFIG_FILE = System.getProperty("user.home")
            + File.separator + "." + Constants.APPLICATOIN_NAME.
                    toLowerCase().replaceAll(" ", "_");
    
    public enum Key {
        PORT(Integer.toString(Constants.DEFAULT_PORT)),
        SERVER_HOST(Constants.DEFAULT_HOST),
        ;
        
        String defaultValue;

        Key(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }
    }
    
    Properties prop;
    
    private ConfigManager() {
        try {
            readConfig();
        } catch (IOException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ConfigManager getInstance() {
        return ConfigManagerHolder.INSTANCE;
    }
    
    private static class ConfigManagerHolder {

        private static final ConfigManager INSTANCE = new ConfigManager();
    }
    
    private void readConfig() throws IOException {
        //If file does not exist, create it with default values
        File propFile = new File(CONFIG_FILE);
        if (!propFile.exists()) {
            prop = new Properties();
            for (Key key : Key.values()) {
                prop.setProperty(key.name(), key.getDefaultValue());
            }
            prop.store(new FileOutputStream(CONFIG_FILE), null);
        } else {
            prop = new Properties();
            prop.load(new FileInputStream(CONFIG_FILE));
        }
    }
    
    public void writeConfig() throws IOException {
        prop.store(new FileOutputStream(CONFIG_FILE), null);
    }
    
    public String getProperty(Key key) {
        if(!prop.containsKey(key)) {
            return prop.getProperty(key.name()).trim();
        }
        return key.getDefaultValue();
    }
    
    public void setProperty(Key key, String value) {
        prop.setProperty(key.name(), value);
    }
    
}
