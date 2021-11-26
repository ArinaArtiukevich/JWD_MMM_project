package com.jwd.controller.command.impl;

import com.jwd.controller.command.AbstractCommand;
import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.dao.entity.enums.ServiceType;
import com.jwd.dao.repository.AbstractDao;
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


public class AddServiceOrderCommandImpl extends AbstractCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddServiceOrderCommandImpl.class);
    private final ControllerValidator validator = new ControllerValidator();
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    public String execute(HttpServletRequest request) throws ControllerException {
        LOGGER.info("Start addServiceOrder.");
        String page = null;
        try {
            String description = request.getParameter(SERVICE_DESCRIPTION);
            String address = request.getParameter(SERVICE_ADDRESS);
            String serviceTypeString = request.getParameter(SERVICE_TYPE);
            Long idClient = getUserId(request); // todo correct?
            ServiceType serviceType = ServiceType.valueOf(serviceTypeString.toUpperCase());
            Date orderCreationDate = new Date();
            Order orderItem = new Order(description, address, serviceType, ServiceStatus.FREE, orderCreationDate);

            validateParameters(orderItem, idClient);

            if (orderService.addServiceOrder(orderItem, idClient)) {
                request.setAttribute(MESSAGE, "Order was added.");
            } else {
                request.setAttribute(ERROR_WORK_MESSAGE, "Could not add an order. Please, try again.");
            }
            page = pathToJsp(ConfigurationBundle.getProperty("path.page.work"));

        } catch (ServiceException e) {
            LOGGER.error("Problems with adding order.");
            throw new ControllerException(e);
        }
        return page;
    }

    private void validateParameters(Order orderItem, Long idClient) throws ControllerException {
        validator.isValid(orderItem.getDescription());
        validator.isValid(orderItem.getAddress());
        validator.isValid(orderItem.getServiceType());
        validator.isValid(orderItem.getOrderCreationDate());
        validator.isValid(orderItem.getStatus());
        validator.isValid(idClient);
    }
}
