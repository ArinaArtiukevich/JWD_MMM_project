package com.jwd.controller.command.impl;

import com.jwd.controller.command.AbstractCommand;
import com.jwd.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jwd.controller.command.ParameterAttributeType;
import com.jwd.controller.entity.CommandAnswer;
import com.jwd.controller.entity.enums.AnswerType;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.factory.ServiceFactory;
import com.jwd.service.serviceLogic.OrderService;
import com.jwd.service.serviceLogic.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.jwd.controller.command.ParameterAttributeType.MESSAGE;
import static com.jwd.controller.util.Util.pathToJsp;


public class TakeOrderCommandImpl extends AbstractCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(TakeOrderCommandImpl.class);
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    public CommandAnswer execute(HttpServletRequest request) throws ControllerException {
        LOGGER.info("Start TakeOrderCommandImpl.");
        CommandAnswer answer = new CommandAnswer();
        String path = null;
        try {
            Long idWorker = getUserId(request);
            Long idOrder = getOrderId(request);
            if (orderService.takeOrder(idOrder, idWorker, ServiceStatus.IN_PROCESS)) {
                request.setAttribute(MESSAGE, "Order was taken.");
                path = pathToJsp(ConfigurationBundle.getProperty("path.page.work"));
            } else {
                LOGGER.error("Could not take order.");
                path = pathToJsp(ConfigurationBundle.getProperty("path.page.error"));
            }
        } catch (ServiceException | NumberFormatException e) {
            LOGGER.error("Could not take order.");
            throw new ControllerException(e);
        }
        answer.setPath(path);
        answer.setAnswerType(AnswerType.FORWARD);
        return answer;
    }
}
