package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
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

import static com.jwd.controller.command.ParameterAttributeType.*;
import static java.util.Objects.nonNull;


public class FindOrdersByServiceTypeImpl implements Command {
    private static final Logger logger = LogManager.getLogger(FindOrdersByServiceTypeImpl.class);
    private final ControllerValidator validator = new ControllerValidator();
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    public String execute(HttpServletRequest request) throws ControllerException {
        logger.info("Start FindOrdersBySpecializationImpl.");
        String page = null;

        String currentPageParam = request.getParameter(CURRENT_PAGE);
        if (currentPageParam == null || currentPageParam.isEmpty()) {
            currentPageParam = "1";
        }
        String currentLimitParam = request.getParameter(PAGE_LIMIT);
        if (currentLimitParam == null || currentLimitParam.isEmpty()) {
            currentLimitParam = "5";
        }
        int currentPage = Integer.parseInt(currentPageParam);
        int pageLimit = Integer.parseInt(currentLimitParam);

        Page<Order> paginationRequest = new Page<>();
        paginationRequest.setPageNumber(currentPage);
        paginationRequest.setLimit(pageLimit);
        try {
            String sortByParameter = request.getParameter(SORT_BY);
            validator.isValid(sortByParameter);
            paginationRequest.setSortBy(sortByParameter);
            String direction = request.getParameter(DIRECTION);
            if (nonNull(direction) && !direction.isEmpty()) {
                paginationRequest.setDirection(direction);
            }
            String serviceTypeString = request.getParameter(SERVICE_TYPE);
            validator.isValid(serviceTypeString);
            ServiceType serviceType  = ServiceType.valueOf(serviceTypeString);
            Page<Order> paginationResult = orderService.getOrdersByServiceType(paginationRequest, serviceType);
            request.setAttribute(PAGEABLE, paginationResult);
            request.setAttribute(LAST_COMMAND, SHOW_ORDERS_BY_SERVICE_TYPE);
            request.setAttribute(SELECTED_SERVICE_TYPE, serviceType);
            request.setAttribute(SELECTED_SORT_BY_PARAMETER, sortByParameter);
            request.setAttribute(SELECTED_DIRECTION_PARAMETER, direction);
            page = ConfigurationBundle.getProperty("path.page.services");
        } catch (ServiceException e) {
            logger.error("Could not get a list of services.");
            throw new ControllerException("Could not get a list of services.");
        }
        return page;
    }

}