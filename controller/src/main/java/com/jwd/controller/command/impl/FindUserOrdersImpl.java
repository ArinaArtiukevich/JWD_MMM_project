package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.OrderService;
import com.jwd.service.serviceLogic.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.jwd.controller.command.ParameterAttributeType.*;

public class FindUserOrdersImpl  implements Command {
    private static final Logger logger = LogManager.getLogger(FindUserOrdersImpl.class);
    private final ControllerValidator validator = new ControllerValidator();
    private final OrderService orderService = new OrderServiceImpl();

    @Override
    public String execute(HttpServletRequest request) throws ControllerException {
        logger.info("Start FindUserOrdersImpl.");
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
            String idUserParameter =  String.valueOf(session.getAttribute(USER_ID));
            validator.isValid(idUserParameter);
            Long idUser = Long.parseLong(idUserParameter);
            validator.isValid(idUser);
            Page<Order> paginationResult = orderService.getOrdersByUserId(paginationRequest, idUser);
            request.setAttribute(PAGEABLE, paginationResult);
            page = ConfigurationBundle.getProperty("path.page.show.user.order");
        }  catch (NumberFormatException | ServiceException e) {
            logger.error("Could not find user's orders.");
            throw new ControllerException(e);
        }
        return page;
    }

}

