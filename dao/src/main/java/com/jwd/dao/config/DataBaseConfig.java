package com.jwd.dao.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.MissingResourceException;

import com.jwd.dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;
import java.util.ResourceBundle;

import static com.jwd.dao.util.ParameterAttribute.*;


public class DataBaseConfig {
    public static Logger LOGGER = LogManager.getLogger(DataBaseConfig.class);
    //todo delete
    //    private final static ResourceBundle RESOURCE_BUNDLE =
//            ResourceBundle.getBundle("database_en_US", new Locale("en", "US"));

    private Properties properties;
    private static Properties propertyQueries = new Properties();

    private boolean driverIsLoaded = false;

    public DataBaseConfig() {
        loadProperties();
        loadQueryProperties();
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getQuery(String key) throws DaoException {
        String result = null;
        try {
            // result = RESOURCE_BUNDLE.getString(key);
            result = propertyQueries.getProperty(key);
        } catch (MissingResourceException e) {
            LOGGER.error("There is no such key in property file.");
            throw new DaoException("There is no such key in property file.");
        }
        return result;
    }

    public Connection getConnection() throws SQLException {
        LOGGER.info("Connection getConnection()");
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
        try (InputStream is = DataBaseConfig.class.getClassLoader().getResourceAsStream(FILE_PROPERTIES_NAME)) {
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadQueryProperties() {
        try (InputStream is = DataBaseConfig.class.getClassLoader().getResourceAsStream(FILE_QUERY_PROPERTIES_NAME)) {
            propertyQueries.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



