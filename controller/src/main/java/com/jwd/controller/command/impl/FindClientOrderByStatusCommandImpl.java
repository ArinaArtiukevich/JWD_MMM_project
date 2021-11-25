package com.jwd.controller.command.impl;

import com.jwd.controller.command.AbstractCommand;
import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;

import com.jwd.controller.validator.ControllerValidator;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
import com.jwd.dao.entity.enums.ServiceStatus;
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


public class FindClientOrderByStatusCommandImpl extends AbstractCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(FindClientOrderByStatusCommandImpl.class);
    private final ControllerValidator validator = new ControllerValidator();
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    public String execute(HttpServletRequest request) throws ControllerException {
        LOGGER.info("Start FindClientOrderByStatusCommandImpl.");
        String page = null;
        int currentPage = getCurrentPageParam(request);
        int pageLimit = getLimitPageParam(request);
        Page<Order> paginationRequest = new Page<>();
        paginationRequest.setPageNumber(currentPage);
        paginationRequest.setLimit(pageLimit);
        try {
            Long idClient = getUserId(request);
            String sortByParameter = getSortByParameter(request);
            paginationRequest.setSortBy(sortByParameter);
            String direction = request.getParameter(DIRECTION);
            if (nonNull(direction) && !direction.isEmpty()) {
                paginationRequest.setDirection(direction);
            }
            String serviceStatusString = request.getParameter(SERVICE_STATUS);
            validator.isValid(serviceStatusString);
            ServiceStatus serviceStatus = ServiceStatus.valueOf(serviceStatusString);
            Page<Order> paginationResult = orderService.getOrdersByServiceStatus(paginationRequest, serviceStatus, idClient);
            request.setAttribute(PAGEABLE, paginationResult);
            request.setAttribute(LAST_COMMAND, FIND_CLIENT_ORDER_BY_STATUS);
            request.setAttribute(SELECTED_SERVICE_STATUS, serviceStatus);
            request.setAttribute(SELECTED_SORT_BY_PARAMETER, sortByParameter);
            request.setAttribute(SELECTED_DIRECTION_PARAMETER, direction);
            page = pathToJsp(ConfigurationBundle.getProperty("path.page.show.user.order"));
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.error("Could not get a list of responses.");
            throw new ControllerException("Could not get a list of responses.");
        }
        return page;
    }

}