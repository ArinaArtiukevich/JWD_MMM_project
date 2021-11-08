package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;

import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
import com.jwd.dao.entity.enums.ServiceType;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.OrderService;
import com.jwd.service.serviceLogic.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.jwd.controller.command.ParameterAttributeType.*;


public class FindOrdersByServiceTypeImpl implements Command {
    private static final Logger logger = LogManager.getLogger(FindOrdersByServiceTypeImpl.class);
    private final OrderService orderService = new OrderServiceImpl();

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
            String serviceTypeString = request.getParameter(SERVICE_TYPE);
            if (serviceTypeString != null && !serviceTypeString.isEmpty()){
                ServiceType serviceType  = ServiceType.valueOf(serviceTypeString);
                Page<Order> paginationResult = orderService.getOrdersByServiceType(paginationRequest, serviceType);
                request.setAttribute(PAGEABLE, paginationResult);
                page = ConfigurationBundle.getProperty("path.page.services");
                request.setAttribute(LAST_COMMAND, SHOW_ORDERS_BY_SERVICE_TYPE);
                HttpSession session = request.getSession();
                session.getAttribute("userRole");
                request.setAttribute(SELECTED_SERVICE_TYPE, serviceType);
            } else {
                logger.error("Invalid type of service.");
                throw new ControllerException("Invalid type of service.");
            }
        } catch (ServiceException e) {
            logger.error("Could not get a list of services.");
            throw new ControllerException("Could not get a list of services.");
        }
        return page;
    }

}