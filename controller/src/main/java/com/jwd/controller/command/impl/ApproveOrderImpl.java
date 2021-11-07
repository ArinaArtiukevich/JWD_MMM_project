package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jwd.controller.command.ParameterAttributeType;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.OrderService;
import com.jwd.service.serviceLogic.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ApproveOrderImpl implements Command {
    private static final Logger logger = LogManager.getLogger(ApproveOrderImpl.class);
    private final OrderService orderService = new OrderServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("Start ApproveOrderImpl.");
        String page = null;
        try {
            // todo runtime exc
            Long idOrder = Long.parseLong(String.valueOf(request.getParameter(ParameterAttributeType.ID_SERVICE)));
            if (orderService.setOrderStatus(idOrder, ServiceStatus.APPROVED)) {
                page = ConfigurationBundle.getProperty("path.page.services");
            } else {
                logger.error("Could not approve order.");
                page = ConfigurationBundle.getProperty("path.page.error");
            }
        } catch (ServiceException e) {
            logger.error("Could not approve order.");
            page = ConfigurationBundle.getProperty("path.page.error");
        }
        return page;
    }
}
