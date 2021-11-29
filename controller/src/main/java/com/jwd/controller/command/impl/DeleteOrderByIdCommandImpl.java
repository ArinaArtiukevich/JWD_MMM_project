package com.jwd.controller.command.impl;

import com.jwd.controller.command.AbstractCommand;
import com.jwd.controller.command.Command;
import com.jwd.controller.entity.CommandAnswer;
import com.jwd.controller.entity.enumType.AnswerType;
import com.jwd.controller.exception.ControllerException;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.factory.ServiceFactory;
import com.jwd.service.serviceLogic.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.jwd.controller.command.ParameterAttributeType.*;

public class DeleteOrderByIdCommandImpl extends AbstractCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteOrderByIdCommandImpl.class);
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();
    @Override
    public CommandAnswer execute(HttpServletRequest request) throws ControllerException {
        LOGGER.info("Start DeleteOrderByIdCommandImpl.");
        CommandAnswer answer = new CommandAnswer();
        String path = null;
        try {
            Long idClient = getUserId(request);
            Long idService = getOrderId(request);
            HttpSession session = request.getSession();
            if (orderService.deleteById(idService ,idClient)){
                session.setAttribute(MESSAGE, "Order was deleted.");
            } else {
                request.setAttribute(ERROR_WORK_MESSAGE, "Could not delete an order. Please, try again.");
                session.setAttribute(MESSAGE, "Order was not deleted.");
            }
            answer.setPath(GO_TO_WORK_PAGE);
            answer.setAnswerType(AnswerType.REDIRECT);
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.error("Problems with deleting order.");
            throw new ControllerException(e);
        }
        return answer;
    }
}
