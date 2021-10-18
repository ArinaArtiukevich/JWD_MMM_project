package com.jwd.dao.resources;

import java.io.InputStream;
import java.util.Locale;
import java.util.MissingResourceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ResourceBundle;


public class DataBaseBundle {
    private final static ResourceBundle RESOURCE_BUNDLE =
            ResourceBundle.getBundle("database", new Locale("en", "US"));
    public static Logger logger = LogManager.getLogger(DataBaseBundle.class);

    private DataBaseBundle() {
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
