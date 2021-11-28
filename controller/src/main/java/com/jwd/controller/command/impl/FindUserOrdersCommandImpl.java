package com.jwd.controller.command.impl;

import com.jwd.controller.command.AbstractCommand;
import com.jwd.controller.command.Command;
import com.jwd.controller.entity.CommandAnswer;
import com.jwd.controller.entity.enums.AnswerType;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
import com.jwd.dao.entity.enums.ServiceType;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.factory.ServiceFactory;
import com.jwd.service.serviceLogic.OrderService;
import com.jwd.service.serviceLogic.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.jwd.controller.command.ParameterAttributeType.*;
import static com.jwd.controller.util.Util.pathToJsp;
import static java.util.Objects.nonNull;

public class FindUserOrdersCommandImpl extends AbstractCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(FindUserOrdersCommandImpl.class);
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    public CommandAnswer execute(HttpServletRequest request) throws ControllerException {
        LOGGER.info("Start FindUserOrdersCommandImpl.");
        CommandAnswer answer = new CommandAnswer();
        String path = null;
        int currentPage = getCurrentPageParam(request);
        int pageLimit = getLimitPageParam(request);
        Page<Order> paginationRequest = new Page<>();
        paginationRequest.setPageNumber(currentPage);
        paginationRequest.setLimit(pageLimit);
        try {
            Long idUser = getUserId(request);
            String sortByParameter = getSortByParameter(request);
            paginationRequest.setSortBy(sortByParameter);
            String direction = request.getParameter(DIRECTION);
            if (nonNull(direction) && !direction.isEmpty()) {
                paginationRequest.setDirection(direction);
            }
            Page<Order> paginationResult = orderService.getOrdersByUserId(paginationRequest, idUser);
            setParametersToRequest(request, paginationResult, SHOW_USER_ORDERS, sortByParameter, direction);
            path = pathToJsp(ConfigurationBundle.getProperty("path.page.show.user.order"));
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.error("Could not find user's orders.");
            throw new ControllerException(e);
        }
        answer.setPath(path);
        answer.setAnswerType(AnswerType.FORWARD);
        return answer;
    }

}

