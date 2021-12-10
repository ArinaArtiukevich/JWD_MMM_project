package com.jwd.dao.connection;

import com.jwd.dao.exception.DaoException;

import java.sql.Connection;

public interface ConnectionPool {
    /**
     * Provides with an existing free connection from pool
     *
     * @return Connection from pool
     * @throws DaoException
     */
    Connection take() throws DaoException;

    /**
     * Returns connection back the pool
     *
     * @param connection of java.sql.Connection
     */
    void retrieve(final Connection connection);
}
