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


public class TakeOrderImpl implements Command {
    private static final Logger logger = LogManager.getLogger(TakeOrderImpl.class);
    private final ControllerValidator validator = new ControllerValidator();
    private final OrderService orderService = new OrderServiceImpl();

    @Override
    public String execute(HttpServletRequest request) throws ControllerException {
        logger.info("Start TakeOrderImpl.");
        String page = null;
        try {
            // todo check
            HttpSession session = request.getSession();
            Object idWorkerObject = session.getAttribute(ParameterAttributeType.USER_ID);
            validator.isValid(idWorkerObject);
            String idWorkerParameter = String.valueOf(idWorkerObject);
            String idOrderParameter = request.getParameter(ParameterAttributeType.ID_SERVICE);
            validator.isValid(idWorkerParameter);
            validator.isValid(idOrderParameter);
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
            throw new ControllerException(e);
        }
        return page;
    }
}
