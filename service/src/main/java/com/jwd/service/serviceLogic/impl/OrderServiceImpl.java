package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.dao.entity.enums.ServiceType;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.OrderDao;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.OrderService;
import com.jwd.service.validator.ServiceValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
    private final OrderDao orderDao;
    private final ServiceValidator validator = new ServiceValidator();

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Page<Order> getAllServices(Page<Order> orderPageRequest) throws ServiceException {
        logger.info("Start Page<Order> getAllServices(Page<Order> orderPageRequest) in OrderService.");
        Page<Order> orderPage = new Page<>();
        try {
            validator.validate(orderPageRequest);
            orderPage = orderDao.getServiceList(orderPageRequest);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
        return orderPage;
    }

    @Override
    public Page<Order> getOrdersByServiceType(Page<Order> orderPageRequest, ServiceType serviceType) throws ServiceException {
        logger.info("Start age<Order> getOrdersByServiceType(Page<Order> orderPageRequest, ServiceType serviceType) in OrderService.");
        Page<Order> orderPage = new Page<>();
        try {
            validator.validate(orderPageRequest);
            validator.validate(serviceType);
            orderPage = orderDao.getOrdersByServiceType(orderPageRequest, serviceType);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
        return orderPage;
    }

    @Override
    public Page<Order> getOrdersByUserId(Page<Order> orderPageRequest, Long idUser) throws ServiceException {
        logger.info("Start Page<Order> getOrdersByUserId(Page<Order> orderPageRequest, Long idUser) in OrderService.");
        Page<Order> orderPage = new Page<>();
        try {
            validator.validate(idUser);
            validator.validate(orderPageRequest);
            orderPage = orderDao.findOrdersByIdUser(orderPageRequest, idUser);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
        return orderPage;
    }

    @Override
    public Order getOrderById(Long idService) throws ServiceException {
        logger.info("Start Order getOrderById(Long idService) in OrderService.");
        Order order = new Order();
        try {
            validator.validate(idService);
            order = orderDao.findOrderById(idService);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
        return order;
    }

    @Override
    public boolean addServiceOrder(Order order, Long idClient) throws ServiceException {
        logger.info("Start boolean addServiceOrder(Order order, Long idClient) in OrderService.");
        boolean isAdded = false;
        try {
            validator.validate(order);
            validator.validate(idClient);
            isAdded = orderDao.add(order, idClient);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isAdded;
    }

    @Override
    public boolean takeOrder(Long idOrder, Long idWorker) throws ServiceException {
        logger.info("Start boolean setOrderStatus(Order order, ServiceStatus serviceStatus) in OrderService.");
        boolean isTaken = false;
        try {
            validator.validate(idOrder);
            validator.validate(idWorker);
            isTaken = orderDao.takeOrder(idOrder, idWorker);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isTaken;
    }

    @Override
    public boolean setOrderStatus(Long idOrder, ServiceStatus serviceStatus) throws ServiceException {
        logger.info("Start boolean setOrderStatus(Long idOrder, ServiceStatus serviceStatus)in OrderService.");
        boolean isSet = false;
        try {
            validator.validate(idOrder);
            validator.validate(serviceStatus);
            isSet = orderDao.setOrderStatus(idOrder, serviceStatus);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isSet;
    }

    @Override
    public Page<Order> getOrdersByWorkerId(Page<Order> orderPageRequest, Long idWorker) throws ServiceException {
        logger.info("Start Page<Order> getOrdersByWorkerId(Page<Order> orderPageRequest, Long idWorker) in OrderService.");
        Page<Order> orderPage = new Page<>();
        try {
            validator.validate(orderPageRequest);
            validator.validate(idWorker);
            orderPage = orderDao.getOrdersByWorkerId(orderPageRequest, idWorker);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
        return orderPage;
    }

    @Override
    public Page<Order> getOrdersResponseByClientId(Page<Order> orderPageRequest, Long idClient) throws ServiceException {
        logger.info("Start Page<Order> getOrdersResponseByClientId(Page<Order> orderPageRequest, Long idClient)in OrderService.");
        Page<Order> orderPage = new Page<>();
        try {
            validator.validate(idClient);
            validator.validate(orderPageRequest);
            orderPage = orderDao.getOrdersResponseByClientId(orderPageRequest, idClient);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
        return orderPage;
    }

    @Override
    public Page<Order> getOrdersByServiceStatus(Page<Order> orderPageRequest, ServiceStatus serviceStatus, Long idClient) throws ServiceException {
        logger.info("Start age<Order> getOrdersByServiceStatus(Page<Order> orderPageRequest, ServiceStatus serviceStatus, Long idClient) in OrderService.");
        Page<Order> orderPage = new Page<>();
        try {
            validator.validate(orderPageRequest);
            validator.validate(serviceStatus);
            validator.validate(idClient);
            orderPage = orderDao.getOrdersByServiceStatus(orderPageRequest, serviceStatus, idClient);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
        return orderPage;
    }

    @Override
    public boolean deleteById(Long idOrder, Long idUser) throws ServiceException {
        logger.info("Start boolean deleteById(Long idOrder) in OrderService.");
        boolean isDeleted = false;
        try {
            validator.validate(idOrder);
            validator.validate(idUser);
            Long localIdClient = orderDao.findOrderById(idOrder).getIdClient();
            if (!localIdClient.equals(idUser)) {
                throw new ServiceException("You are not allowed to do this operation.");
            }
            ServiceStatus serviceStatus = orderDao.getServiceStatusById(idOrder);
            validator.validate(serviceStatus);
            if (!(serviceStatus.toString()).equals(ServiceStatus.FREE.toString().toUpperCase())) {
                throw new ServiceException("It is impossible to delete order.");
            }
            isDeleted = orderDao.deleteById(idOrder);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isDeleted;
    }
}
