package com.jwd.controller.command.impl;

import com.jwd.controller.command.AbstractCommand;
import com.jwd.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.smartcardio.CommandAPDU;

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


public class CloseOrderCommandImpl extends AbstractCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CloseOrderCommandImpl.class);
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    public CommandAnswer execute(HttpServletRequest request) throws ControllerException {
        LOGGER.info("Start CloseOrderCommandImpl.");
        CommandAnswer answer = new CommandAnswer();
        String path = null;
        try {
            Long idOrder = getOrderId(request);
            HttpSession session = request.getSession();
            if (orderService.setOrderStatus(idOrder, ServiceStatus.DONE)) {
                session.setAttribute(MESSAGE, "Order status was changed. Order is done.");
            } else {
                LOGGER.error("Order was not closed.");
                session.setAttribute(ERROR_WORK_MESSAGE, "Order was not closed.");
            }
            answer.setPath(GO_TO_WORK_PAGE);
            answer.setAnswerType(AnswerType.REDIRECT);

        } catch (NumberFormatException | ServiceException e) {
            LOGGER.error("Could not close order.");
            throw new ControllerException(e);
        }
        return answer;
    }
}
