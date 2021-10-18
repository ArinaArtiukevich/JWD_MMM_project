package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.dao.entity.enums.ServiceType;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.List;

import static com.jwd.controller.command.ParameterAttributeType.*;

public class FindUserOrdersImpl  implements Command {
    private static final Logger logger = LogManager.getLogger(FindUserOrdersImpl.class);

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("Start FindUserOrdersImpl.");

        String page = null;
        try {
            // TODO NULLPOINTER
            HttpSession session = request.getSession();
            Long idUser = Long.parseLong(String.valueOf(session.getAttribute(USER_ID)));
            List<Order> orderList = OrderService.getOrdersById(idUser);
            session.setAttribute("orderList", orderList);
            logger.info(" OrderList = " + orderList);

            page = ConfigurationBundle.getProperty("path.page.show.user.order");

        }  catch (ServiceException e) {
            logger.error("Could not find user's orders.");
            page = ConfigurationBundle.getProperty("path.page.work");
        }
        return page;
    }

}

