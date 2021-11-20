package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jwd.controller.command.ParameterAttributeType;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.factory.ServiceFactory;
import com.jwd.service.serviceLogic.OrderService;
import com.jwd.service.serviceLogic.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.jwd.controller.command.ParameterAttributeType.*;
import static com.jwd.controller.util.Util.pathToJsp;
import static java.util.Objects.nonNull;


public class FindWorkerResponseImpl implements Command {
    private static final Logger logger = LogManager.getLogger(FindWorkerResponseImpl.class);
    private final ControllerValidator validator = new ControllerValidator();
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    public String execute(HttpServletRequest request) throws ControllerException {
        logger.info("Start FindWorkerResponseImpl.");
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
            String idWorkerParameter = String.valueOf(session.getAttribute(USER_ID));
            validator.isValid(idWorkerParameter);
            Long idWorker = Long.parseLong(idWorkerParameter);
            validator.isValid(idWorker);
            String sortByParameter = request.getParameter(SORT_BY);
            validator.isValid(sortByParameter);
            paginationRequest.setSortBy(sortByParameter);
            String direction = request.getParameter(DIRECTION);
            if (nonNull(direction) && !direction.isEmpty()) {
                paginationRequest.setDirection(direction);
            }
            Page<Order> paginationResult = orderService.getOrdersByWorkerId(paginationRequest, idWorker);
            request.setAttribute(PAGEABLE, paginationResult);
            request.setAttribute(SELECTED_SORT_BY_PARAMETER, sortByParameter);
            request.setAttribute(SELECTED_DIRECTION_PARAMETER, direction);
            request.setAttribute(LAST_COMMAND, FIND_WORKER_RESPONSE);
            page = pathToJsp(ConfigurationBundle.getProperty("path.page.order.worker.responses"));

        } catch (NumberFormatException | ServiceException e) {
            logger.error("Could not find order.");
            throw new ControllerException(e);
        }
        return page;
    }
}
