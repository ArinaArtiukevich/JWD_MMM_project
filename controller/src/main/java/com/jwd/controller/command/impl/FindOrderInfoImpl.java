package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.command.ParameterAttributeType;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.User;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.serviceLogic.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jwd.service.serviceLogic.impl.OrderServiceImpl;
import com.jwd.service.serviceLogic.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class FindOrderInfoImpl implements Command {
    private static final Logger logger = LogManager.getLogger(FindOrderInfoImpl.class);
    private final OrderService orderService = new OrderServiceImpl();
    UserService userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("Start FindOrderInfoImpl.");

        String page = null;
        try {
            String idServiceParameter = request.getParameter(ParameterAttributeType.ID_SERVICE);
            Long idService = Long.parseLong(idServiceParameter);
            Order order = orderService.getOrderById(idService);
            Long idClient = order.getIdClient();
            User client = userService.getUserById(idClient);
            // TODO check parameters
            HttpSession session = request.getSession();
            session.setAttribute(ParameterAttributeType.ORDER, order);
            session.setAttribute(ParameterAttributeType.CLIENT, client);
            page = ConfigurationBundle.getProperty("path.page.order.info");

        } catch (NumberFormatException | ServiceException e) {
            logger.error("Could not find order.");
            page = ConfigurationBundle.getProperty("path.page.error");
        }
        return page;
    }
}
