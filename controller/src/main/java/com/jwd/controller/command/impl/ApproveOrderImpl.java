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
import com.jwd.service.serviceLogic.OrderService;
import com.jwd.service.serviceLogic.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ApproveOrderImpl implements Command {
    private static final Logger logger = LogManager.getLogger(ApproveOrderImpl.class);
    private final ControllerValidator validator = new ControllerValidator();
    private final OrderService orderService = new OrderServiceImpl();

    @Override
    public String execute(HttpServletRequest request) throws ControllerException {
        logger.info("Start ApproveOrderImpl.");
        String page = null;
        try {
            String idOrderString = String.valueOf(request.getParameter(ParameterAttributeType.ID_SERVICE));
            validator.isValid(idOrderString);
            Long idOrder = Long.parseLong(idOrderString);
            if (orderService.setOrderStatus(idOrder, ServiceStatus.APPROVED)) {
                page = ConfigurationBundle.getProperty("path.page.services");
            } else {
                logger.error("Could not approve order.");
                page = ConfigurationBundle.getProperty("path.page.error");
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid number format.");
            page = ConfigurationBundle.getProperty("path.page.error");
        } catch (ServiceException e) {
            logger.error("Could not approve order.");
            throw new ControllerException(e);
        }
        return page;
    }
}
