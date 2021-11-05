package com.jwd.dao.connection.impl;

import com.jwd.dao.config.DataBaseConfig;
import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.util.Objects.nonNull;

public class ConnectionPoolImpl implements ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPoolImpl.class);
    private static final int CONNECTION_POOL_SIZE = 5;
    private final DataBaseConfig dataBaseConfig;
    private final BlockingQueue<Connection> pool;
    private final BlockingQueue<Connection> taken;

    public ConnectionPoolImpl(final DataBaseConfig dataBaseConfig) {
        this.dataBaseConfig = dataBaseConfig;
        pool = new LinkedBlockingQueue<>(CONNECTION_POOL_SIZE);
        taken = new LinkedBlockingQueue<>(CONNECTION_POOL_SIZE);
        initConnectionPool();
    }

    private void initConnectionPool() {
        try {
            for (int i = 0; i < CONNECTION_POOL_SIZE; i++) {
                pool.add(dataBaseConfig.getConnection());
            }
        } catch (SQLException e) {

        }
        LOGGER.info("init pool.size() is " + pool.size());
        LOGGER.info("init taken.size() is " + taken.size());
    }

    @Override
    public Connection take() throws DaoException {
        Connection newConnection;
        try {
            LOGGER.info("#take()");
            newConnection = pool.take();
            taken.add(newConnection);
        } catch (InterruptedException e) {
            LOGGER.info("Exception: #take()");
            throw new DaoException(e);
        }
        LOGGER.info("pool.size() is " + pool.size());
        LOGGER.info("taken.size() is " + taken.size());
        return newConnection;
    }

    @Override
    public void retrieve(final Connection connection) {
        LOGGER.info("#retrieve(final Connection connection)");
        if (nonNull(connection)) {
            taken.remove(connection);
            pool.add(connection);
        } else {
            LOGGER.info("connection = null");
        }
        LOGGER.info("pool.size() is " + pool.size());
        LOGGER.info("taken.size() is " + taken.size());
    }
}
