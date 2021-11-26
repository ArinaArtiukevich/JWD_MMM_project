package com.jwd.controller.resources;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.jwd.controller.command.ParameterAttributeType.CONFIGURATION;

public class ConfigurationBundle {
    public static Logger LOGGER = LogManager.getLogger(ConfigurationBundle.class);
    private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(CONFIGURATION);

    private ConfigurationBundle() {
    }

    public static String getProperty(String key) {
        String result = null;
        try {
            result = RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            LOGGER.error("There is no such key");
            result = "There is no such key";
        }
        return result;
    }

}
