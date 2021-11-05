package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.resources.ConfigurationBundle;

import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static com.jwd.controller.command.ParameterAttributeType.*;


public class FindAllServicesImpl implements Command {
    private static final Logger logger = LogManager.getLogger(FindAllServicesImpl.class);
    private final OrderService orderService = new OrderService();

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("Start FindAllServicesImpl.");
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
            Page<Order> paginationResult = orderService.getAllServices(paginationRequest);
            request.setAttribute(PAGEABLE, paginationResult);
            page = ConfigurationBundle.getProperty("path.page.services");
            HttpSession session = request.getSession();
            session.getAttribute("userRole");
        } catch (ServiceException e) {
            logger.error("Could not get a list of services.");
            page = ConfigurationBundle.getProperty("path.page.error");
        }
        return page;
    }

}