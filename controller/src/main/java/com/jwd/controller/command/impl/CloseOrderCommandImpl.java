package com.jwd.controller.command.impl;

import com.jwd.controller.command.AbstractCommand;
import com.jwd.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jwd.controller.command.ParameterAttributeType;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.factory.ServiceFactory;
import com.jwd.service.serviceLogic.OrderService;
import com.jwd.service.serviceLogic.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.jwd.controller.command.ParameterAttributeType.MESSAGE;
import static com.jwd.controller.util.Util.pathToJsp;


public class CloseOrderCommandImpl extends AbstractCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CloseOrderCommandImpl.class);
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    public String execute(HttpServletRequest request) throws ControllerException {
        LOGGER.info("Start CloseOrderCommandImpl.");
        String page = null;
        try {
            Long idOrder = getOrderId(request);
            if (orderService.setOrderStatus(idOrder, ServiceStatus.DONE)) {
                request.setAttribute(MESSAGE, "Order status was changed. Order is done.");
                page = pathToJsp(ConfigurationBundle.getProperty("path.page.work"));
            } else {
                LOGGER.error("Order was not closed.");
                page = pathToJsp(ConfigurationBundle.getProperty("path.page.error"));
            }
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.error("Could not close order.");
            throw new ControllerException(e);
        }
        return page;
    }
}