package com.jwd.dao.connection;

import com.jwd.dao.exception.DaoException;

import java.sql.Connection;

public interface ConnectionPool {
    Connection take() throws DaoException;
    void retrieve(final Connection connection);
}
