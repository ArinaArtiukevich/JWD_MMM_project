package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.dao.entity.enums.ServiceType;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.OrderService;
import com.jwd.service.serviceLogic.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;

import static com.jwd.controller.command.ParameterAttributeType.*;


public class AddServiceOrderImpl implements Command {
    private static final Logger logger = LogManager.getLogger(RegistrationCommandImpl.class);
    private final OrderService orderService = new OrderServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("Start addServiceOrder.");

        String page = null;
        String description = request.getParameter(SERVICE_DESCRIPTION);
        String address = request.getParameter(SERVICE_ADDRESS);
        ServiceType serviceType = ServiceType.valueOf(request.getParameter(SERVICE_TYPE).toUpperCase());
        String login = (String)request.getSession().getAttribute("login");
        Date orderCreationDate = new Date();

        Order orderItem = new Order(description, address, serviceType, ServiceStatus.FREE, orderCreationDate);
        // TODO SHORT CONSTRUCTOR USED

        try {
            if (orderService.addServiceOrder(orderItem, login)){
                page = ConfigurationBundle.getProperty("path.page.work");
            }
            else {
                request.setAttribute("errorWorkMessage", "Could not add an order. Please, try again.");
                page = ConfigurationBundle.getProperty("path.page.work");
            }

        } catch (ServiceException e) {
            page = ConfigurationBundle.getProperty("path.page.error");
            logger.error("Problems with adding order.");
        }
        return page;
    }

}

