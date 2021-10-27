package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jwd.controller.command.ParameterAttributeType;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.dao.entity.Order;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class FindWorkerResponseImpl implements Command {
    private static final Logger logger = LogManager.getLogger(FindWorkerResponseImpl.class);

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("Start FindWorkerResponseImpl.");
        String page = null;
        try {
            String idWorkerParameter = request.getParameter(ParameterAttributeType.ID_WORKER);
            Long idWorker = Long.parseLong(idWorkerParameter);
            List<Order> responses = OrderService.getOrdersByWorkerId(idWorker);

            // TODO check parameters
            HttpSession session = request.getSession();
            session.setAttribute(ParameterAttributeType.RESPONSES, responses);
            page = ConfigurationBundle.getProperty("path.page.order.responses");

        } catch (NumberFormatException | ServiceException e) {
            logger.error("Could not find order.");
            page = ConfigurationBundle.getProperty("path.page.error");
        }
        return page;
    }
}
