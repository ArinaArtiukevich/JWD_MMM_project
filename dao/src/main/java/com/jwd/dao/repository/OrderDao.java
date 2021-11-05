package com.jwd.dao.repository;

import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.dao.exception.DaoException;

import java.util.List;

public interface OrderDao {
    boolean add(Order order, Long idClient) throws DaoException;

    Page<Order> getServiceList(Page<Order> daoOrderPage) throws DaoException;

    Page<Order> findOrdersByIdUser(Page<Order> daoOrderPage, Long idUser) throws DaoException ;

    Order findOrderById(Long idService) throws DaoException;

    boolean takeOrder(Long idOrder, Long idWorker) throws DaoException;

    boolean setOrderStatus(Long idOrder, ServiceStatus serviceStatus) throws DaoException;

    Page<Order> getOrdersByWorkerId(Page<Order> daoOrderPage, Long idWorker) throws DaoException;

    Page<Order> getOrdersResponseByClientId(Page<Order> daoOrderPage, Long idClient) throws DaoException ;

}
