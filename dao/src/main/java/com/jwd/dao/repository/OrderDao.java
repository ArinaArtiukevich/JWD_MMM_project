package com.jwd.dao.repository;

import com.jwd.dao.entity.Order;
import com.jwd.dao.exception.DaoException;

import java.util.List;

public interface OrderDao {
    boolean add(Order order, Long idClient) throws DaoException;

    List<Order> getServiceList() throws DaoException;

    List<Order> findOrdersByIdUser(Long idUser) throws DaoException;
}
