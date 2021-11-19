package com.jwd.dao.repository;

import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
import com.jwd.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.util.Objects.nonNull;

public abstract class AbstractDao {

    private final ConnectionPool connectionPool;

    public AbstractDao(ConnectionPool connectionPool) {
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
        if (nonNull(resultSets)) {
            for (final ResultSet resultSet : resultSets) {
                if (nonNull(resultSet)) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    protected void close(final PreparedStatement... preparedStatements) {
        if (nonNull(preparedStatements)) {
            for (final PreparedStatement preparedStatement : preparedStatements) {
                if (nonNull(preparedStatement)) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    protected Page<Order> setPageResult(Page<Order> daoOrderPage, long totalElements, List<Order> orders) {
        Page<Order> page = new Page<>();
        page.setPageNumber(daoOrderPage.getPageNumber());
        page.setLimit(daoOrderPage.getLimit());
        page.setTotalElements(totalElements);
        page.setElements(orders);
        page.setFilter(daoOrderPage.getFilter());
        page.setSortBy(daoOrderPage.getSortBy());
        page.setDirection(daoOrderPage.getDirection());
        return page;
    }

}
