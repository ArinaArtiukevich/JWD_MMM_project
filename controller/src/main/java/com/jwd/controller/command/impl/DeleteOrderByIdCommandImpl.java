package com.jwd.controller.command.impl;

import com.jwd.controller.command.AbstractCommand;
import com.jwd.controller.command.Command;
import com.jwd.controller.command.ParameterAttributeType;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.dao.entity.enums.ServiceType;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.factory.ServiceFactory;
import com.jwd.service.serviceLogic.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.jwd.controller.command.ParameterAttributeType.*;
import static com.jwd.controller.util.Util.pathToJsp;

public class DeleteOrderByIdCommandImpl extends AbstractCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteOrderByIdCommandImpl.class);
    private final ControllerValidator validator = new ControllerValidator();
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();
    @Override
    public String execute(HttpServletRequest request) throws ControllerException {
        LOGGER.info("Start DeleteOrderByIdCommandImpl.");
        String page = null;
        try {
            Long idClient = getUserId(request);
            Long idService = getOrderId(request);

            if (orderService.deleteById(idService ,idClient)){
                request.setAttribute(MESSAGE, "Order was deleted.");
            } else {
                request.setAttribute(ERROR_WORK_MESSAGE, "Could not delete an order. Please, try again.");
                request.setAttribute(MESSAGE, "Order was not deleted.");
            }
            page = pathToJsp(ConfigurationBundle.getProperty("path.page.work"));

        } catch (NumberFormatException | ServiceException e) {
            LOGGER.error("Problems with deleting order.");
            throw new ControllerException(e);
        }
        return page;
    }
}