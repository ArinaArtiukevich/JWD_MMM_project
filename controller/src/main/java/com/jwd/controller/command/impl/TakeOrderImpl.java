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


public class TakeOrderImpl implements Command {
    private static final Logger logger = LogManager.getLogger(TakeOrderImpl.class);
    private final OrderService orderService = new OrderService();

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("Start TakeOrderImpl.");
        String page = null;
        try {
            // todo check
            HttpSession session = request.getSession();
            String idWorkerParameter = String.valueOf(session.getAttribute(ParameterAttributeType.USER_ID));
            String idOrderParameter = String.valueOf(request.getParameter(ParameterAttributeType.ID_SERVICE));
            Long idWorker = Long.parseLong(idWorkerParameter);
            Long idOrder = Long.parseLong(idOrderParameter);
            if (orderService.takeOrder(idOrder, idWorker)) {
                orderService.setOrderStatus(idOrder, ServiceStatus.IN_PROCESS);
                page = ConfigurationBundle.getProperty("path.page.services");
            } else {
                logger.error("Could not take order.");
                page = ConfigurationBundle.getProperty("path.page.error");
            }
        } catch (ServiceException e) {
            logger.error("Could not take order.");
            page = ConfigurationBundle.getProperty("path.page.error");
        }
        return page;
    }
}
