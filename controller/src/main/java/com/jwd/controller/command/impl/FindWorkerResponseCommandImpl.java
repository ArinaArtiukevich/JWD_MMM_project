package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

import com.jwd.controller.entity.CommandAnswer;
import com.jwd.controller.entity.enumType.AnswerType;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.factory.ServiceFactory;
import com.jwd.service.serviceLogic.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.jwd.controller.command.ParameterAttributeType.*;
import static com.jwd.controller.util.Util.*;
import static java.util.Objects.nonNull;


public class FindWorkerResponseCommandImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(FindWorkerResponseCommandImpl.class);
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    public CommandAnswer execute(HttpServletRequest request) throws ControllerException {
        LOGGER.info("Start FindWorkerResponseCommandImpl.");
        CommandAnswer answer = new CommandAnswer();
        int currentPage = getCurrentPageParam(request);
        int pageLimit = getLimitPageParam(request);
        Page<Order> paginationRequest = new Page<>();
        paginationRequest.setPageNumber(currentPage);
        paginationRequest.setLimit(pageLimit);
        try {
            Long idWorker = getUserId(request);
            String sortByParameter = getSortByParameter(request);
            paginationRequest.setSortBy(sortByParameter);
            String direction = request.getParameter(DIRECTION);
            if (nonNull(direction) && !direction.isEmpty()) {
                paginationRequest.setDirection(direction);
            }
            Page<Order> paginationResult = orderService.getOrdersByWorkerId(paginationRequest, idWorker);
            setParametersToRequest(request, paginationResult, FIND_WORKER_RESPONSE, sortByParameter, direction);
            answer.setPath(pathToJsp(ConfigurationBundle.getProperty("path.page.order.worker.responses")));
            answer.setAnswerType(AnswerType.FORWARD);
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.error("Could not find order.");
            throw new ControllerException(e);
        }
        return answer;
    }
}
