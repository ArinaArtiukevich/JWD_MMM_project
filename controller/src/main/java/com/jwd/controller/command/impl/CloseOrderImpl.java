package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jwd.controller.command.ParameterAttributeType;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CloseOrderImpl implements Command {
    private static final Logger logger = LogManager.getLogger(CloseOrderImpl.class);

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("Start CloseOrderImpl.");
        String page = null;
        try {
            String idOrderParameter = request.getParameter(ParameterAttributeType.ID_SERVICE);
            Long idOrder = Long.parseLong(String.valueOf(idOrderParameter));
            if (OrderService.setOrderStatus(idOrder, ServiceStatus.DONE)) {
                page = ConfigurationBundle.getProperty("path.page.services");
            } else {
                logger.error("Order was not closed.");
                page = ConfigurationBundle.getProperty("path.page.error");
            }
        } catch (NumberFormatException | ServiceException e) {
            logger.error("Could not close order.");
            page = ConfigurationBundle.getProperty("path.page.error");
        }
        return page;
    }
}
