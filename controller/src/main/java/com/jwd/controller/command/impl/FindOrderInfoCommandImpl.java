package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.command.ParameterAttributeType;
import com.jwd.controller.entity.CommandAnswer;
import com.jwd.controller.entity.enumType.AnswerType;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.User;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.factory.ServiceFactory;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.serviceLogic.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.jwd.controller.util.Util.*;


public class FindOrderInfoCommandImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(FindOrderInfoCommandImpl.class);
    private final ControllerValidator validator = new ControllerValidator();
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public CommandAnswer execute(HttpServletRequest request) throws ControllerException {
        LOGGER.info("Start FindOrderInfoCommandImpl.");
        CommandAnswer answer = new CommandAnswer();
        try {
            Long idService = getOrderId(request);
            Order order = orderService.getOrderById(idService);
            Long idClient = order.getIdClient();
            validator.isValid(idClient);
            User client = userService.getUserById(idClient);
            HttpSession session = request.getSession();
            session.setAttribute(ParameterAttributeType.ORDER, order);
            session.setAttribute(ParameterAttributeType.CLIENT, client);
            answer.setPath(pathToJsp(ConfigurationBundle.getProperty("path.page.order.info")));
            answer.setAnswerType(AnswerType.FORWARD);
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.error("Could not find order.");
            throw new ControllerException(e);
        }
        return answer;
    }
}
