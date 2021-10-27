package com.jwd.dao.connection;

import java.sql.*;
import java.util.Iterator;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.jwd.dao.resources.DataBaseBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.util.Objects.nonNull;


public class ConnectionPool {
    private final int SIZE = Integer.parseInt(DataBaseBundle.getProperty("pool.size"));
    private final String URL = DataBaseBundle.getProperty("database.url");
    private final String USERNAME = DataBaseBundle.getProperty("database.username");
    private final String DATABASE_NAME = DataBaseBundle.getProperty("database.name");
    private final String PASSWORD = DataBaseBundle.getProperty("database.password");
    private final String DRIVER = DataBaseBundle.getProperty("database.driver");

    private BlockingDeque<Connection> connection;
    private static ReentrantLock lockConnection = new ReentrantLock();
    public static Logger logger = LogManager.getLogger(ConnectionPool.class);

    private static ConnectionPool connectionPool;

    private ConnectionPool() {
        connection = new LinkedBlockingDeque<>();
        try {
            Class.forName(DRIVER);
            for (int i = 0; i < SIZE; i++) {
                Connection localConnection = DriverManager.getConnection(URL + DATABASE_NAME, USERNAME, PASSWORD);
                connection.add(localConnection);
            }
            logger.debug("Connection pool with 20 connections was created.");
        } catch (ClassNotFoundException | SQLException e) {
            throw new ExceptionInInitializerError("Connection error.");
        }
    }

    public static ConnectionPool getInstance() {
        lockConnection.lock();
        try {
            if (connectionPool == null) {
                connectionPool = new ConnectionPool();
            }
            return connectionPool;
        } finally {
            lockConnection.unlock();
        }
    }


    public Connection getConnection() {
        Connection result = null;
        try {
            result = connection.poll(10, TimeUnit.SECONDS);
            logger.debug("Connection was allocated.");
            if (result == null) {
                throw new ExceptionInInitializerError("Connection error.");
            }
        } catch (InterruptedException e) {
            throw new ExceptionInInitializerError("Connection error.");
        }
        return result;
    }

    public void releaseConnection(Connection con) {
        if (con != null) {
            connection.push(con);
            logger.debug("Connection was returned.");
        }
    }

    public void closeAll() {
        Iterator<Connection> i = connection.iterator();
        while (i.hasNext()) {
            try {
                i.next().close();
            } catch (SQLException e) {
                logger.error("Can't close connection.", e);
            }
        }
        connection = null;
    }
}
