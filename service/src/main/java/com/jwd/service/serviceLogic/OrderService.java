package com.jwd.service.serviceLogic;

import com.jwd.dao.config.DataBaseConfig;
import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.entity.Page;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.dao.repository.UserDao;
import com.jwd.dao.repository.LoginDao;
import com.jwd.dao.repository.OrderDao;
import com.jwd.dao.repository.impl.UserDaoImpl;
import com.jwd.dao.repository.impl.LoginDaoImpl;
import com.jwd.dao.repository.impl.OrderDaoImpl;
import com.jwd.dao.entity.Order;
import com.jwd.dao.exception.DaoException;
import com.jwd.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private static final Logger logger = LogManager.getLogger(OrderService.class);
    private final OrderDao orderDao = new OrderDaoImpl(new ConnectionPoolImpl(new DataBaseConfig()));

    public Page<Order> getAllServices(Page<Order> orderPageRequest) throws ServiceException {
        logger.info("Start Page<Order> getAllServices(Page<Order> orderPageRequest) in OrderService.");
        Page<Order> orderPage = new Page<>();
        try {
           orderPage = orderDao.getServiceList(orderPageRequest);
        }
        catch(DaoException e) {
            throw new ServiceException(e);
        }
        return orderPage;
    }


    public Page<Order> getOrdersByUserId(Page<Order> orderPageRequest, Long idUser) throws ServiceException {
        logger.info("Start Page<Order> getOrdersByUserId(Page<Order> orderPageRequest, Long idUser) in OrderService.");
        Page<Order> orderPage = new Page<>();
        try {
            orderPage = orderDao.findOrdersByIdUser(orderPageRequest, idUser);
        }
        catch(DaoException e) {
            throw new ServiceException(e);
        }
        return orderPage;
    }

    public Order getOrderById(Long idService) throws ServiceException {
        logger.info("Start Order getOrderById(Long idService) in OrderService.");
        Order order = new Order();
        try {
            order = orderDao.findOrderById(idService);
        }
        catch(DaoException e) {
            throw new ServiceException(e);
        }
        return order;
    }

    public boolean addServiceOrder(Order order, String login) throws ServiceException {
        logger.info("Start boolean addServiceOrder(Order order, String login) in OrderService.");
        ArrayList<Order> list = new ArrayList<>();
        boolean isAdded = false;
        UserDao userDao = new UserDaoImpl(new ConnectionPoolImpl(new DataBaseConfig()));
        try {
            Long idClient = userDao.findIdByLogin(login);
            isAdded = orderDao.add(order, idClient);
        }
        catch(DaoException e) {
            throw new ServiceException(e);
        }
        return isAdded;
    }

    public boolean takeOrder(Long idOrder, Long idWorker) throws ServiceException {
        logger.info("Start boolean setOrderStatus(Order order, ServiceStatus serviceStatus) in OrderService.");
        boolean isTaken = false;
        try {
            isTaken = orderDao.takeOrder(idOrder, idWorker);
        }
        catch(DaoException e) {
            throw new ServiceException(e);
        }
        return isTaken;
    }

    public boolean setOrderStatus(Long idOrder, ServiceStatus serviceStatus) throws ServiceException {
        logger.info("Start boolean setOrderStatus(Long idOrder, ServiceStatus serviceStatus)in OrderService.");
        boolean isSet = false;
        try {
            isSet = orderDao.setOrderStatus(idOrder, serviceStatus);
        }
        catch(DaoException e) {
            throw new ServiceException(e);
        }
        return isSet;
    }

    public Page<Order> getOrdersByWorkerId(Page<Order> orderPageRequest, Long idWorker) throws ServiceException {
        logger.info("Start Page<Order> getOrdersByWorkerId(Page<Order> orderPageRequest, Long idWorker) in OrderService.");
        Page<Order> orderPage = new Page<>();
        try {
            orderPage = orderDao.getOrdersByWorkerId(orderPageRequest, idWorker);
        }
        catch(DaoException e) {
            throw new ServiceException(e);
        }
        return orderPage;
    }

    public Page<Order> getOrdersResponseByClientId(Page<Order> orderPageRequest, Long idClient) throws ServiceException {
        logger.info("Start Page<Order> getOrdersResponseByClientId(Page<Order> orderPageRequest, Long idClient)in OrderService.");
        Page<Order> orderPage = new Page<>();
        try {
            orderPage = orderDao.getOrdersResponseByClientId(orderPageRequest, idClient);
        }
        catch(DaoException e) {
            throw new ServiceException(e);
        }
        return orderPage;
    }
}
