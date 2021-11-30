package com.jwd.service.serviceLogic;

import com.jwd.dao.entity.Page;
import com.jwd.dao.entity.enumType.ServiceStatus;
import com.jwd.dao.entity.Order;
import com.jwd.service.exception.ServiceException;

public interface OrderService {
    Page<Order> getAllServices(Page<Order> orderPageRequest) throws ServiceException;

    Page<Order> getOrdersByServiceType(Page<Order> orderPageRequest, String serviceType) throws ServiceException;

    Page<Order> getOrdersByUserId(Page<Order> orderPageRequest, Long idUser) throws ServiceException;

    Order getOrderById(Long idService) throws ServiceException;

    boolean addServiceOrder(Order order, Long idClient) throws ServiceException;

    boolean takeOrder(Long idOrder, Long idWorker, ServiceStatus serviceStatus) throws ServiceException;

    boolean setDoneOrderStatus(Long idOrder, Long idWorker) throws ServiceException;

    boolean setApproveOrderStatus(Long idOrder, Long idClient) throws ServiceException;

    Page<Order> getOrdersByWorkerId(Page<Order> orderPageRequest, Long idWorker) throws ServiceException;

    Page<Order> getOrdersResponseByClientId(Page<Order> orderPageRequest, Long idClient) throws ServiceException;

    Page<Order> getOrdersByServiceStatus(Page<Order> orderPageRequest, String serviceStatus, Long idClient) throws ServiceException;

    boolean deleteById(Long idOrder, Long idUser) throws ServiceException;
}
