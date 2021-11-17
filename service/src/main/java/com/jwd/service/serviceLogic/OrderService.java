package com.jwd.service.serviceLogic;

import com.jwd.dao.entity.Page;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.enums.ServiceType;
import com.jwd.service.exception.ServiceException;

public interface OrderService {
    Page<Order> getAllServices(Page<Order> orderPageRequest) throws ServiceException;

    Page<Order> getOrdersByServiceType(Page<Order> orderPageRequest, ServiceType serviceType) throws ServiceException;

    Page<Order> getOrdersByUserId(Page<Order> orderPageRequest, Long idUser) throws ServiceException;

    Order getOrderById(Long idService) throws ServiceException;

    boolean addServiceOrder(Order order, String login) throws ServiceException;

    boolean takeOrder(Long idOrder, Long idWorker) throws ServiceException;

    boolean setOrderStatus(Long idOrder, ServiceStatus serviceStatus) throws ServiceException;

    Page<Order> getOrdersByWorkerId(Page<Order> orderPageRequest, Long idWorker) throws ServiceException;

    Page<Order> getOrdersResponseByClientId(Page<Order> orderPageRequest, Long idClient) throws ServiceException;

    Page<Order> getOrdersByServiceStatus(Page<Order> orderPageRequest, ServiceStatus serviceStatus) throws ServiceException;
}
