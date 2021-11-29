package com.jwd.dao.repository;

import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
import com.jwd.dao.entity.enumType.ServiceStatus;
import com.jwd.dao.entity.enumType.ServiceType;
import com.jwd.dao.exception.DaoException;

public interface OrderDao {
    boolean add(Order order, Long idClient) throws DaoException;

    Page<Order> getServiceList(Page<Order> daoOrderPage) throws DaoException;

    Page<Order> getOrdersByServiceType(Page<Order> daoOrderPage, ServiceType serviceType) throws DaoException;

    Page<Order> findOrdersByIdUser(Page<Order> daoOrderPage, Long idUser) throws DaoException ;

    Order findOrderById(Long idService) throws DaoException;

    boolean takeOrder(Long idOrder, Long idWorker, ServiceStatus serviceStatus) throws DaoException;

    boolean setOrderStatus(Long idOrder, ServiceStatus serviceStatus) throws DaoException;

    Page<Order> getOrdersByWorkerId(Page<Order> daoOrderPage, Long idWorker) throws DaoException;

    Page<Order> getOrdersResponseByClientId(Page<Order> daoOrderPage, Long idClient) throws DaoException;

    Page<Order> getOrdersByServiceStatus(Page<Order> daoOrderPage, ServiceStatus serviceStatus, Long idClient) throws DaoException;

    boolean deleteById(Long idOrder) throws DaoException;

    ServiceStatus getServiceStatusById(Long idOrder) throws DaoException;
}
