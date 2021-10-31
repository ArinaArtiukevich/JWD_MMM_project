package com.jwd.dao.repository;

import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.util.Objects.nonNull;

public class AbstractDao {

    private final ConnectionPoolImpl connectionPool;

    public AbstractDao(ConnectionPoolImpl connectionPool) {
        this.connectionPool = connectionPool;
    }

    protected Connection getConnection(final boolean hasAutocommit) throws DaoException, SQLException {
        final Connection connection = connectionPool.take();
        connection.setAutoCommit(hasAutocommit);
        return connection;
    }

    protected void retrieve(final Connection connection) {
        connectionPool.retrieve(connection);
    }

    protected void close(final ResultSet... resultSets) {
        try {
            if (nonNull(resultSets)) {
                for (final ResultSet resultSet : resultSets) {
                    if (nonNull(resultSet)) {
                        resultSet.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void close(final PreparedStatement... preparedStatements) {
        try {
            if (nonNull(preparedStatements)) {
                for (final PreparedStatement preparedStatement : preparedStatements) {
                    if (nonNull(preparedStatement)) {
                        preparedStatement.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    protected void processAbnormalCase(boolean isTrue, String message) throws DaoException {
//        if (isTrue) {
//            throw new DaoException(message);
//        }
//    } todo
}
