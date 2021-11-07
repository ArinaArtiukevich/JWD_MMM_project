package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.OrderService;
import com.jwd.service.serviceLogic.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import static com.jwd.controller.command.ParameterAttributeType.*;


public class FindClientResponseImpl implements Command {
    private static final Logger logger = LogManager.getLogger(FindClientResponseImpl.class);
    private final OrderService orderService = new OrderServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("Start FindClientResponseImpl.");
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
            HttpSession session = request.getSession();
            String idClientParameter = String.valueOf(session.getAttribute(USER_ID));
            Long idClient = Long.parseLong(idClientParameter);
            Page<Order> paginationResult = orderService.getOrdersResponseByClientId(paginationRequest, idClient);
            request.setAttribute(PAGEABLE, paginationResult);
            page = ConfigurationBundle.getProperty("path.page.order.client.order.responses");

        } catch (NumberFormatException | ServiceException e) {
            logger.error("Could not find order.");
            page = ConfigurationBundle.getProperty("path.page.error");
        }
        return page;
    }
}