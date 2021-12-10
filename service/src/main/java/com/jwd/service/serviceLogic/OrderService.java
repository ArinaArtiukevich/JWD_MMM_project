package com.jwd.service.serviceLogic;

import com.jwd.dao.entity.Page;
import com.jwd.dao.entity.enumType.ServiceStatus;
import com.jwd.dao.entity.Order;
import com.jwd.service.exception.ServiceException;

public interface OrderService {
    /**
     * gets all orders
     *
     * @param orderPageRequest with request parameters and empty list
     * @return Page with orders
     * @throws ServiceException
     */
    Page<Order> getAllServices(Page<Order> orderPageRequest) throws ServiceException;

    /**
     * gets orders by service type
     *
     * @param orderPageRequest with request parameters and empty list
     * @param serviceType      is required service type
     * @return Page with orders
     * @throws ServiceException
     */
    Page<Order> getOrdersByServiceType(Page<Order> orderPageRequest, String serviceType) throws ServiceException;

    /**
     * gets orders by user id
     *
     * @param orderPageRequest with request parameters and empty list
     * @param idUser
     * @return Page with orders
     * @throws ServiceException
     */
    Page<Order> getOrdersByUserId(Page<Order> orderPageRequest, Long idUser) throws ServiceException;

    /**
     * gets order by id order
     *
     * @param idService
     * @return required order
     * @throws ServiceException
     */
    Order getOrderById(Long idService) throws ServiceException;

    /**
     * adds order
     *
     * @param order
     * @param idClient who wants to add an order
     * @return true when order was added
     * @throws ServiceException
     */
    boolean addServiceOrder(Order order, Long idClient) throws ServiceException;

    /**
     * takes order
     *
     * @param idOrder       to be added
     * @param idWorker      who wants to add an order
     * @param serviceStatus
     * @return true when order was added
     * @throws ServiceException
     */
    boolean takeOrder(Long idOrder, Long idWorker, ServiceStatus serviceStatus) throws ServiceException;

    /**
     * sets done order status
     *
     * @param idOrder
     * @param idWorker who sets the status
     * @return true when status was set
     * @throws ServiceException
     */
    boolean setDoneOrderStatus(Long idOrder, Long idWorker) throws ServiceException;

    /**
     * sets approve order status
     *
     * @param idOrder
     * @param idClient who sets the status
     * @return true when status was set
     * @throws ServiceException
     */
    boolean setApproveOrderStatus(Long idOrder, Long idClient) throws ServiceException;

    /**
     * gets orders by worker id
     *
     * @param orderPageRequest with request parameters and empty list
     * @param idWorker
     * @return Page with orders
     * @throws ServiceException
     */
    Page<Order> getOrdersByWorkerId(Page<Order> orderPageRequest, Long idWorker) throws ServiceException;

    /**
     * gets orders response by client id
     *
     * @param orderPageRequest with request parameters and empty list
     * @param idClient
     * @return Page with orders
     * @throws ServiceException
     */
    Page<Order> getOrdersResponseByClientId(Page<Order> orderPageRequest, Long idClient) throws ServiceException;

    /**
     * gets orders by service status
     *
     * @param orderPageRequest with request parameters and empty list
     * @param serviceStatus    service status to  be found
     * @param idClient
     * @return Page with orders
     * @throws ServiceException
     */
    Page<Order> getOrdersByServiceStatus(Page<Order> orderPageRequest, String serviceStatus, Long idClient) throws ServiceException;

    /**
     * deletes order by id order and id user
     *
     * @param idOrder
     * @param idUser
     * @return true when order was deleted
     * @throws ServiceException
     */
    boolean deleteById(Long idOrder, Long idUser) throws ServiceException;
}
