package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.entity.CommandAnswer;
import com.jwd.controller.entity.enumType.AnswerType;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;

import com.jwd.controller.validator.ControllerValidator;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.factory.ServiceFactory;
import com.jwd.service.serviceLogic.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.jwd.controller.command.ParameterAttributeType.*;
import static com.jwd.controller.util.Util.*;
import static java.util.Objects.nonNull;


public class FindOrdersByServiceTypeCommandImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(FindOrdersByServiceTypeCommandImpl.class);
    private final ControllerValidator validator = new ControllerValidator();
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    public CommandAnswer execute(HttpServletRequest request) throws ControllerException {
        LOGGER.info("Start FindOrdersByServiceTypeCommandImpl.");
        CommandAnswer answer = new CommandAnswer();
        int currentPage = getCurrentPageParam(request);
        int pageLimit = getLimitPageParam(request);
        Page<Order> paginationRequest = new Page<>();
        paginationRequest.setPageNumber(currentPage);
        paginationRequest.setLimit(pageLimit);
        try {
            String sortByParameter = getSortByParameter(request);
            paginationRequest.setSortBy(sortByParameter);
            String direction = request.getParameter(DIRECTION);
            if (nonNull(direction) && !direction.isEmpty()) {
                paginationRequest.setDirection(direction);
            }
            String serviceTypeString = request.getParameter(SERVICE_TYPE);
            validator.isValid(serviceTypeString);

            Page<Order> paginationResult = orderService.getOrdersByServiceType(paginationRequest, serviceTypeString);

            setParametersToRequest(request, paginationResult, SHOW_ORDERS_BY_SERVICE_TYPE, sortByParameter, direction);
            request.setAttribute(SELECTED_SERVICE_TYPE, serviceTypeString);
            answer.setPath(pathToJsp(ConfigurationBundle.getProperty("path.page.services")));
            answer.setAnswerType(AnswerType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Could not get a list of services.");
            throw new ControllerException("Could not get a list of services.");
        }
        return answer;
    }

}