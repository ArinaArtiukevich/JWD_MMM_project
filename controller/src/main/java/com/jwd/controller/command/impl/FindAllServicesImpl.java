package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.resources.ConfigurationBundle;

import com.jwd.dao.entity.Order;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.jwd.controller.command.ParameterAttributeType.ALL_SERVICES;


public class FindAllServicesImpl implements Command {
    private static final Logger logger = LogManager.getLogger(FindAllServicesImpl.class);

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("Start FindAllServicesImpl.");
        String page = null;
        List<Order> orderList = new ArrayList<Order>();
        try {
            page = ConfigurationBundle.getProperty("path.page.services");;
            orderList = OrderService.getAllServices();
            request.getSession().setAttribute(ALL_SERVICES, orderList);
        } catch (ServiceException e) {
            logger.error("Could not get a list of services.");
            page = ConfigurationBundle.getProperty("path.page.error");
        }
        return page;
    }
}
