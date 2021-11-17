package com.jwd.controller.command.impl;

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

import static com.jwd.controller.command.ParameterAttributeType.USER_ID;
import static com.jwd.controller.util.Util.pathToJsp;

public class DeleteOrderByIdImpl implements Command {
    private static final Logger logger = LogManager.getLogger(FindAllServicesImpl.class);
    private final ControllerValidator validator = new ControllerValidator();
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();
    @Override
    public String execute(HttpServletRequest request) throws ControllerException {
        logger.info("Start DeleteOrderByIdImpl.");
        String page = null;
        try {
            HttpSession session = request.getSession();
            String idClientParameter = String.valueOf(session.getAttribute(USER_ID));
            validator.isValid(idClientParameter);
            Long idClient = Long.parseLong(idClientParameter);
            validator.isValid(idClient);

            String idServiceParameter = request.getParameter(ParameterAttributeType.ID_SERVICE);
            validator.isValid(idServiceParameter);
            Long idService = Long.parseLong(idServiceParameter);
            validator.isValid(idService);
            if (orderService.deleteById(idService ,idClient)){
                page = pathToJsp(ConfigurationBundle.getProperty("path.page.work"));
            }
            else {
                request.setAttribute("errorWorkMessage", "Could not delete an order. Please, try again.");
                page = pathToJsp(ConfigurationBundle.getProperty("path.page.work"));
            }

        } catch (NumberFormatException | ServiceException e) {
            logger.error("Problems with deleting order.");
            throw new ControllerException(e);
        }
        return page;
    }
}
