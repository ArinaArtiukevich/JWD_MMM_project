package com.jwd.controller.command.impl;

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

import static com.jwd.controller.util.Util.pathToJsp;


public class CloseOrderImpl implements Command {
    private static final Logger logger = LogManager.getLogger(CloseOrderImpl.class);
    private final ControllerValidator validator = new ControllerValidator();
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    public String execute(HttpServletRequest request) throws ControllerException {
        logger.info("Start CloseOrderImpl.");
        String page = null;
        try {
            String idOrderString = request.getParameter(ParameterAttributeType.ID_SERVICE);
            validator.isValid(idOrderString);
            Long idOrder = Long.parseLong(String.valueOf(idOrderString));
            if (orderService.setOrderStatus(idOrder, ServiceStatus.DONE)) {
                page = pathToJsp(ConfigurationBundle.getProperty("path.page.work"));
            } else {
                logger.error("Order was not closed.");
                page = pathToJsp(ConfigurationBundle.getProperty("path.page.error"));
            }
        } catch (NumberFormatException | ServiceException e) {
            logger.error("Could not close order.");
            throw new ControllerException(e);
        }
        return page;
    }
}
