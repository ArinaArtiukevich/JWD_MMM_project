package com.jwd.service.serviceLogic;

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

    public static List<Order> getAllServices() throws ServiceException {
        logger.info("Start List<Service> getAllServices() in OrderService.");
        List<Order> list = new ArrayList<Order>();
        OrderDao orderDao = new OrderDaoImpl();
        try {
            list = orderDao.getServiceList();
        }
        catch(DaoException e) {
            throw new ServiceException(e);
        }
        return list;
    }

    public static List<Order> getOrdersByLogin(String login) throws ServiceException {
        logger.info("Start List<Order> getOrdersByLogin(String login in OrderService.");
        List<Order> list = new ArrayList<>();
        try {
            OrderDao orderDao = new OrderDaoImpl();
            LoginDao loginDao = new LoginDaoImpl();
            Long idClient = loginDao.findIdByLogin(login);
            list = orderDao.findOrdersByIdUser(idClient);
        }
        catch(DaoException e) {
            throw new ServiceException(e);
        }
        return list;
    }

    public static List<Order> getOrdersByUserId(Long idUser) throws ServiceException {
        logger.info("Start  List<Order> getOrdersById(Long idUser) in OrderService.");
        List<Order> list = new ArrayList<>();
        try {
            OrderDao orderDao = new OrderDaoImpl();
            list = orderDao.findOrdersByIdUser(idUser);
        }
        catch(DaoException e) {
            throw new ServiceException(e);
        }
        return list;
    }

    public static Order getOrdersById(Long idService) throws ServiceException {
        logger.info("Start Order getOrdersById(Long idService) in OrderService.");
        Order order = new Order();
        try {
            OrderDao orderDao = new OrderDaoImpl();
            order = orderDao.findOrderById(idService);
        }
        catch(DaoException e) {
            throw new ServiceException(e);
        }
        return order;
    }

    public static boolean addServiceOrder(Order order, String login) throws ServiceException {
        logger.info("Start boolean addServiceOrder(Order order, String login) in OrderService.");
        ArrayList<Order> list = new ArrayList<Order>();
        boolean isAdded = false;
        UserDao userDao = new UserDaoImpl();
        OrderDao orderDao = new OrderDaoImpl();
        try {
            Long idClient = userDao.findIdByLogin(login);
            isAdded = orderDao.add(order, idClient);
        }
        catch(DaoException e) {
            throw new ServiceException(e);
        }
        return isAdded;
    }

    public static boolean takeOrder(Long idOrder, Long idWorker) throws ServiceException {
        logger.info("Start boolean setOrderStatus(Order order, ServiceStatus serviceStatus) in OrderService.");
        boolean isTaken = false;
        try {
            OrderDao orderDao = new OrderDaoImpl();
            isTaken = orderDao.takeOrder(idOrder, idWorker);
        }
        catch(DaoException e) {
            throw new ServiceException(e);
        }
        return isTaken;
    }

    public static boolean setOrderStatus(Long idOrder, ServiceStatus serviceStatus) throws ServiceException {
        logger.info("Start boolean setOrderStatus(Long idOrder, ServiceStatus serviceStatus)in OrderService.");
        boolean isSet = false;
        try {
            OrderDao orderDao = new OrderDaoImpl();
            isSet = orderDao.setOrderStatus(idOrder, serviceStatus);
        }
        catch(DaoException e) {
            throw new ServiceException(e);
        }
        return isSet;
    }

    public static List<Order> getOrdersByWorkerId(Long idWorker) throws ServiceException {
        logger.info("Start List<Order> getOrdersByWorkerId(Long idWorker) in OrderService.");
        List<Order> list = new ArrayList<>();
        try {
            OrderDao orderDao = new OrderDaoImpl();
            list = orderDao.getOrdersByWorkerId(idWorker);
        }
        catch(DaoException e) {
            throw new ServiceException(e);
        }
        return list;
    }

    public static List<Order> getOrdersResponseByClientId(Long idClient) throws ServiceException {
        logger.info("Start List<Order> getOrdersByClientId(Long idClient) in OrderService.");
        List<Order> list = new ArrayList<>();
        try {
            OrderDao orderDao = new OrderDaoImpl();
            list = orderDao.getOrdersResponseByClientId(idClient);
        }
        catch(DaoException e) {
            throw new ServiceException(e);
        }
        return list;
    }

}
