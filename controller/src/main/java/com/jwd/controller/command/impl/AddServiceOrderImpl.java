package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.dao.entity.enums.ServiceType;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.LoginService;
import com.jwd.service.serviceLogic.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.jwd.controller.command.ParameterAttributeType.*;


public class AddServiceOrderImpl implements Command {
    private static final Logger logger = LogManager.getLogger(RegistrationCommandImpl.class);

    @Override
    public String execute(HttpServletRequest request) {

        logger.info("Start addServiceOrder.");

        String page = null;
        String description = request.getParameter(SERVICE_DESCRIPTION);
        String address = request.getParameter(SERVICE_ADDRESS);
        ServiceType serviceType = ServiceType.valueOf(request.getParameter(SERVICE_TYPE).toUpperCase());
        String login = (String)request.getSession().getAttribute("login");

        Order orderItem = new Order(description, address, serviceType, ServiceStatus.FREE);
        // TODO SHORT CONSTRUCTOR USED

        try {
            if (OrderService.addServiceOrder(orderItem, login)){
                page = ConfigurationBundle.getProperty("path.page.work");
            }
            else {
                request.setAttribute("errorWorkMessage", "Could not add an order");
                page = ConfigurationBundle.getProperty("path.page.work");
            }

        } catch (ServiceException e) {
            request.setAttribute("errorAddServiceOrder", e.getMessage());
            page = ConfigurationBundle.getProperty("path.page.services");
            logger.error("Problems with adding service.");
        }
        return page;
    }

}

