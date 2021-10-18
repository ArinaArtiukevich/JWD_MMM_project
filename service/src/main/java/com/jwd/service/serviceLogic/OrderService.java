package com.jwd.service.serviceLogic;

import com.jwd.dao.repository.ClientDao;
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

    public static List<Order> getOrdersById(Long idUser) throws ServiceException {
        logger.info("Start  List<Order> getOrdersById(Long idUser) in OrderService.");
        List<Order> list = new ArrayList<>();
        try {
            OrderDao orderDao = new OrderDaoImpl();
            LoginDao loginDao = new LoginDaoImpl();
            list = orderDao.findOrdersByIdUser(idUser);
        }
        catch(DaoException e) {
            throw new ServiceException(e);
        }
        return list;
    }

    public static boolean addServiceOrder(Order order, String login) throws ServiceException {
        logger.info("Start void addServiceOrder() in OrderService.");
        ArrayList<Order> list = new ArrayList<Order>();
        boolean isAdded = false;
        ClientDao clientDao = new UserDaoImpl();
        OrderDao orderDao = new OrderDaoImpl();
        try {
            Long idClient = clientDao.findIdByLogin(login);
            isAdded =  orderDao.add(order, idClient);
        }
        catch(DaoException e) {
            throw new ServiceException(e);
        }
        return isAdded;
    }

}
