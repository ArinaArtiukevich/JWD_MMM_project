package com.jwd.dao.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.MissingResourceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;
import java.util.ResourceBundle;


public class DataBaseConfig {
    public static Logger logger = LogManager.getLogger(DataBaseConfig.class);
    private final static ResourceBundle RESOURCE_BUNDLE =
            ResourceBundle.getBundle("database_en_US", new Locale("en", "US"));
    private final static String FILE_PROPERTIES_NAME ="database_en_US.properties";
    private final String
            URL = "database.url",
            USERNAME = "database.username",
            DATABASE_NAME = "database.name",
            PASSWORD = "database.password",
            DRIVER = "database.driver";
    private Properties properties;

    private boolean  driverIsLoaded = false;

    public DataBaseConfig() {
        loadProperties();
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getQuery(String key) {
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

    public Connection getConnection() throws SQLException {
        loadJdbcDriver();
        Connection connection;
        Properties properties = new Properties();
        properties.setProperty("user", getProperty(USERNAME));
        properties.setProperty("password", getProperty(PASSWORD));
        connection = DriverManager.getConnection(getProperty(URL) + getProperty(DATABASE_NAME), properties);
        return connection;

    }

    private void loadJdbcDriver() {
        if (!driverIsLoaded) {
            try {
                Class.forName(getProperty(DRIVER));
                driverIsLoaded = true;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadProperties() {
        try(InputStream is = DataBaseConfig.class.getClassLoader().getResourceAsStream(FILE_PROPERTIES_NAME)) {
            System.out.println(is);
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



