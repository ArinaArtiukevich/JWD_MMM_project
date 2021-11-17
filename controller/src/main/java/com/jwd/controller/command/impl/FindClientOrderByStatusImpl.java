package com.jwd.controller.command.impl;

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


public class FindClientOrderByStatusImpl implements Command {
    private static final Logger logger = LogManager.getLogger(FindClientOrderByStatusImpl.class);
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
            HttpSession session = request.getSession();
            String idClientParameter = String.valueOf(session.getAttribute(USER_ID));
            validator.isValid(idClientParameter);
            Long idClient = Long.parseLong(idClientParameter);
            validator.isValid(idClient);

            String sortByParameter = request.getParameter(SORT_BY);
            validator.isValid(sortByParameter);
            paginationRequest.setSortBy(sortByParameter);
            String direction = request.getParameter(DIRECTION);
            if (nonNull(direction) && !direction.isEmpty()) {
                paginationRequest.setDirection(direction);
            }
            String serviceStatusString = request.getParameter(SERVICE_STATUS);
            validator.isValid(serviceStatusString);
            ServiceStatus serviceStatus = ServiceStatus.valueOf(serviceStatusString);
            Page<Order> paginationResult = orderService.getOrdersByServiceStatus(paginationRequest, serviceStatus);
            request.setAttribute(PAGEABLE, paginationResult);
            request.setAttribute(LAST_COMMAND, FIND_CLIENT_ORDER_BY_STATUS);
            request.setAttribute(SELECTED_SERVICE_STATUS, serviceStatus);
            request.setAttribute(SELECTED_SORT_BY_PARAMETER, sortByParameter);
            request.setAttribute(SELECTED_DIRECTION_PARAMETER, direction);
            page = pathToJsp(ConfigurationBundle.getProperty("path.page.show.user.order"));
        } catch (NumberFormatException | ServiceException e) {
            logger.error("Could not get a list of responses.");
            throw new ControllerException("Could not get a list of responses.");
        }
        return page;
    }

}