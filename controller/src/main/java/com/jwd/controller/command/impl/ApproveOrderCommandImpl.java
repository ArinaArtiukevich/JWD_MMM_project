package com.jwd.controller.command.impl;

import com.jwd.controller.command.AbstractCommand;
import com.jwd.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jwd.controller.entity.CommandAnswer;
import com.jwd.controller.entity.enumType.AnswerType;
import com.jwd.controller.exception.ControllerException;
import com.jwd.dao.entity.enumType.ServiceStatus;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.factory.ServiceFactory;
import com.jwd.service.serviceLogic.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.jwd.controller.command.ParameterAttributeType.*;


public class ApproveOrderCommandImpl extends AbstractCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ApproveOrderCommandImpl.class);
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    public CommandAnswer execute(HttpServletRequest request) throws ControllerException {
        LOGGER.info("Start ApproveOrderCommandImpl.");
        CommandAnswer answer = new CommandAnswer();
        try {
            Long idOrder = getOrderId(request);
            Long idClient = getUserId(request);
            HttpSession session = request.getSession();
            if (orderService.setApproveOrderStatus(idOrder, idClient)) {
                session.setAttribute(MESSAGE, "Order was approved.");
            } else {
                LOGGER.error("Could not approve order.");
                session.setAttribute(ERROR_WORK_MESSAGE, "Could not approve an order. Please, try again.");
            }
            answer.setPath(GO_TO_WORK_PAGE);
            answer.setAnswerType(AnswerType.REDIRECT);
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid number format.");
            throw new ControllerException("Invalid number format.");
        } catch (ServiceException e) {
            LOGGER.error("Could not approve order.");
            throw new ControllerException(e);
        }
        return answer;
    }
}
