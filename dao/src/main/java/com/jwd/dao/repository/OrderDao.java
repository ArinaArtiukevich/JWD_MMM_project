package com.jwd.dao.repository;

import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.dao.exception.DaoException;

import java.util.List;

public interface OrderDao {
    boolean add(Order order, Long idClient) throws DaoException;

    List<Order> getServiceList() throws DaoException;

    List<Order> findOrdersByIdUser(Long idUser) throws DaoException;

    Order findOrderById(Long idService) throws DaoException;

    boolean takeOrder(Long idOrder, Long idWorker) throws DaoException;

    boolean setOrderStatus(Long idOrder, ServiceStatus serviceStatus) throws DaoException;

    List<Order> getOrdersByWorkerId(Long idWorker) throws DaoException;

    List<Order> getOrdersResponseByClientId(Long idClient) throws DaoException;

}
