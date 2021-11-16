package com.jwd.dao.factory;

import com.jwd.dao.config.DataBaseConfig;
import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.repository.LoginDao;
import com.jwd.dao.repository.OrderDao;
import com.jwd.dao.repository.UserDao;
import com.jwd.dao.repository.impl.LoginDaoImpl;
import com.jwd.dao.repository.impl.OrderDaoImpl;
import com.jwd.dao.repository.impl.UserDaoImpl;

public class DaoFactory {
    private static final DaoFactory INSTANCE = new DaoFactory();

    private final ConnectionPool connectionPool = new ConnectionPoolImpl(new DataBaseConfig());
    private final UserDao userDao = new UserDaoImpl(connectionPool);
    private final LoginDao loginDao = new LoginDaoImpl(connectionPool);
    private final OrderDao orderDao = new OrderDaoImpl(connectionPool);

    private DaoFactory() {}

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public LoginDao getLoginDao() {
        return loginDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }
}
