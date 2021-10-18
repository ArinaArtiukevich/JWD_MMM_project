package com.jwd.dao.connection;


import com.jwd.dao.resources.DataBaseBundle;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import static java.util.Objects.nonNull;

public class DataBaseConfig {

    private final String URL = DataBaseBundle.getProperty("database.url");
    private final String USERNAME = DataBaseBundle.getProperty("database.username");
    private final String DATABASE_NAME = DataBaseBundle.getProperty("database.name");
    private final String PASSWORD = DataBaseBundle.getProperty("database.password");
    private final String DRIVER = DataBaseBundle.getProperty("database.driver");

    private static final String DATABASE_CONFIG_PATH = "db.properties";

    private Properties properties;

    private boolean driverIsLoaded = false;

    public DataBaseConfig() {
        loadProperties();
    }

    public Connection getConnection() throws SQLException {
        loadJdbcDriver();

        Connection connection;
        Properties properties = new Properties();
        properties.setProperty("user", USERNAME);
        properties.setProperty("password", PASSWORD);
        connection = DriverManager.getConnection(URL + DATABASE_NAME, properties);

        return connection;
    }

    private void loadJdbcDriver() {
        if (!driverIsLoaded) {
            try {
                Class.forName(DRIVER);
                driverIsLoaded = true;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private void loadProperties() {
        // TODO change to controller properties
        try (InputStream is = DataBaseConfig.class.getClassLoader().getResourceAsStream(DATABASE_CONFIG_PATH)) {
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (nonNull(resultSet)) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (nonNull(preparedStatement)) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (nonNull(connection)) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close(Connection connection, PreparedStatement preparedStatement) {
        try {
            if (nonNull(preparedStatement)) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (nonNull(connection)) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}