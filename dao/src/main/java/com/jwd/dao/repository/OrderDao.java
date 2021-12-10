package com.jwd.dao.repository;

import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
import com.jwd.dao.entity.enumType.ServiceStatus;
import com.jwd.dao.entity.enumType.ServiceType;
import com.jwd.dao.entity.enumType.UserRole;
import com.jwd.dao.exception.DaoException;

public interface OrderDao {
    /**
     * adds new order
     * @param order
     * @param idClient is client's id
     * @return true when order added
     * @throws DaoException
     */
    boolean add(Order order, Long idClient) throws DaoException;

    /**
     * gets all orders
     * @param daoOrderPage with request parameters and empty list
     * @return Page with orders
     * @throws DaoException
     */
    Page<Order> getServiceList(Page<Order> daoOrderPage) throws DaoException;

    /**
     * gets orders by service type
     * @param daoOrderPage with request parameters and empty list
     * @param serviceType required service type
     * @return Page with orders
     * @throws DaoException
     */
    Page<Order> getOrdersByServiceType(Page<Order> daoOrderPage, ServiceType serviceType) throws DaoException;

    /**
     * finds orders by id user
     * @param daoOrderPage with request parameters and empty list
     * @param idUser is client's id
     * @return Page with orders
     * @throws DaoException
     */
    Page<Order> findOrdersByIdUser(Page<Order> daoOrderPage, Long idUser) throws DaoException ;

    /**
     * finds an order by id
     * @param idService is required order id
     * @return required order
     * @throws DaoException
     */
    Order findOrderById(Long idService) throws DaoException;

    /**
     * takes order by id worker, id client, service status
     * @param idOrder is order id
     * @param idWorker is worker id
     * @param serviceStatus is order status
     * @return true when order was taken
     * @throws DaoException
     */
    boolean takeOrder(Long idOrder, Long idWorker, ServiceStatus serviceStatus) throws DaoException;

    /**
     * sets status to the order
     * @param idOrder is order id
     * @param serviceStatus is status to be set
     * @return true if status was set
     * @throws DaoException
     */
    boolean setOrderStatus(Long idOrder, ServiceStatus serviceStatus) throws DaoException;

    /**
     * gets orders by worker id
     * @param daoOrderPage with request parameters and empty list
     * @param idWorker is worker id
     * @return Page with orders
     * @throws DaoException
     */
    Page<Order> getOrdersByWorkerId(Page<Order> daoOrderPage, Long idWorker) throws DaoException;

    /**
     * gets order responses by id client
     * @param daoOrderPage with request parameters and empty list
     * @param idClient is client id
     * @return Page with orders
     * @throws DaoException
     */
    Page<Order> getOrdersResponseByClientId(Page<Order> daoOrderPage, Long idClient) throws DaoException;

    /**
     * gets orders by service status
     * @param daoOrderPage with request parameters and empty list
     * @param serviceStatus is required status
     * @param idClient is client id
     * @return Page with orders
     * @throws DaoException
     */
    Page<Order> getOrdersByServiceStatus(Page<Order> daoOrderPage, ServiceStatus serviceStatus, Long idClient) throws DaoException;

    /**
     * deletes order by id order
     * @param idOrder
     * @return true when order was deleted
     * @throws DaoException
     */
    boolean deleteById(Long idOrder) throws DaoException;

    /**
     * gets service status by order id
     * @param idOrder
     * @return service status of order
     * @throws DaoException
     */
    ServiceStatus getServiceStatusById(Long idOrder) throws DaoException;

    /**
     * deletes orders by id client
     * @param idClient
     * @return true when order was deleted
     * @throws DaoException
     */
    boolean deleteByIdClient(Long idClient) throws DaoException;

    /**
     * finds worker role by order id
     * @param idOrder
     * @return role of user
     * @throws DaoException
     */
    UserRole findWorkerRoleByIdOrder(Long idOrder) throws DaoException;

    /**
     * finds client role by order id
     * @param idOrder
     * @return role of user
     * @throws DaoException
     */
    UserRole findClientRoleByIdOrder(Long idOrder) throws DaoException;
}
