package com.jwd.service.factory;

import com.jwd.service.serviceLogic.OrderService;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.serviceLogic.impl.OrderServiceImpl;
import com.jwd.service.serviceLogic.impl.UserServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private final UserService userService = new UserServiceImpl();
    private final OrderService orderService = new OrderServiceImpl();

    private ServiceFactory() {}

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

