package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.dao.entity.enums.ServiceType;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.factory.ServiceFactory;
import com.jwd.service.serviceLogic.OrderService;
import com.jwd.service.serviceLogic.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;

import static com.jwd.controller.command.ParameterAttributeType.*;
import static com.jwd.controller.util.Util.pathToJsp;


public class AddServiceOrderImpl implements Command {
    private static final Logger logger = LogManager.getLogger(AddServiceOrderImpl.class);
    private final ControllerValidator validator = new ControllerValidator();
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    public String execute(HttpServletRequest request) throws ControllerException {
        logger.info("Start addServiceOrder.");

        String page = null;
        String description = request.getParameter(SERVICE_DESCRIPTION);
        String address = request.getParameter(SERVICE_ADDRESS);
        String serviceTypeString = request.getParameter(SERVICE_TYPE);
        String login = (String)request.getSession().getAttribute("login");
        validator.isValid(serviceTypeString);
        validator.isValid(serviceTypeString);
        Date orderCreationDate = new Date();
        ServiceType serviceType = ServiceType.valueOf(serviceTypeString.toUpperCase());

        validateParameters(description, address, serviceType, orderCreationDate, login);
        Order orderItem = new Order(description, address, serviceType, ServiceStatus.FREE, orderCreationDate);
        try {
            if (orderService.addServiceOrder(orderItem, login)){
                page = pathToJsp(ConfigurationBundle.getProperty("path.page.work"));
            }
            else {
                request.setAttribute("errorWorkMessage", "Could not add an order. Please, try again.");
                page = pathToJsp(ConfigurationBundle.getProperty("path.page.work"));
            }

        } catch (ServiceException e) {
            logger.error("Problems with adding order.");
            throw new ControllerException(e);
        }
        return page;
    }
    private void validateParameters(String description, String address, ServiceType serviceType, Date orderCreationDate, String login) throws ControllerException {
        validator.isValid(description);
        validator.isValid(address);
        validator.isValid(serviceType);
        validator.isValid(orderCreationDate);
        validator.isValid(login);
    }

}

