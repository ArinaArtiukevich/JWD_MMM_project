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

import static com.jwd.controller.command.ParameterAttributeType.*;
import static com.jwd.controller.util.Util.pathToJsp;


public class TakeOrderCommandImpl extends AbstractCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(TakeOrderCommandImpl.class);
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    public CommandAnswer execute(HttpServletRequest request) throws ControllerException {
        LOGGER.info("Start TakeOrderCommandImpl.");
        CommandAnswer answer = new CommandAnswer();
        try {
            Long idWorker = getUserId(request);
            Long idOrder = getOrderId(request);
            HttpSession session = request.getSession();
            if (orderService.takeOrder(idOrder, idWorker, ServiceStatus.IN_PROCESS)) {
                session.setAttribute(MESSAGE, "Order was taken.");
            } else {
                LOGGER.error("Could not take order.");
                session.setAttribute(ERROR_WORK_MESSAGE, "Could not take order.");
            }
            answer.setPath(GO_TO_WORK_PAGE);
            answer.setAnswerType(AnswerType.REDIRECT);
        } catch (ServiceException | NumberFormatException e) {
            LOGGER.error("Could not take order.");
            throw new ControllerException(e);
        }
        return answer;
    }
}
