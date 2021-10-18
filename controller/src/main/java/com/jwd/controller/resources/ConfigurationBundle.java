package com.jwd.controller.resources;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigurationBundle {
    private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("configuration");
    public static Logger logger = LogManager.getLogger(ConfigurationBundle.class);

    private ConfigurationBundle() {
    }

    public static String getProperty(String key) {
        String result = null;
        try {
            result = RESOURCE_BUNDLE.getString(key);
        }
        catch(MissingResourceException e) {
            logger.error("There is no such key");
            result = "There is no such key";
        }
        return result;
    }

}
