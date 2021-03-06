package com.jwd.service.factory;

import com.jwd.dao.factory.DaoFactory;
import com.jwd.service.serviceLogic.OrderService;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.serviceLogic.impl.OrderServiceImpl;
import com.jwd.service.serviceLogic.impl.UserServiceImpl;
import com.jwd.service.validator.ServiceValidator;

public class ServiceFactory {
    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private DaoFactory daoFactory = DaoFactory.getInstance();
    private ServiceValidator serviceValidator = new ServiceValidator();
    private final UserService userService = new UserServiceImpl(daoFactory.getUserDao(), serviceValidator);
    private final OrderService orderService = new OrderServiceImpl(daoFactory.getOrderDao(), serviceValidator);

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public UserService getUserService() {
        return userService;
    }

    public OrderService getOrderService() {
        return orderService;
    }
}

