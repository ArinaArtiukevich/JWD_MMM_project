package com.jwd.controller.command;

import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;

import javax.servlet.Registration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.jwd.controller.command.ParameterAttributeType.*;
import static java.util.Objects.isNull;

public abstract class AbstractCommand {
    private final ControllerValidator validator = new ControllerValidator();

    public Long getUserId(HttpServletRequest request) throws ControllerException {
        HttpSession session = request.getSession();
        String idUserParameter = String.valueOf(session.getAttribute(USER_ID));
        validator.isValid(idUserParameter);
        Long idUser = Long.parseLong(idUserParameter);
        validator.isValid(idUser);
        return idUser;
    }

    public Long getOrderId(HttpServletRequest request) throws ControllerException {
        String idOrderString = request.getParameter(ID_SERVICE);
        validator.isValid(idOrderString);
        Long idOrder = Long.parseLong(idOrderString);
        validator.isValid(idOrder);
        return idOrder;
    }

    public int getCurrentPageParam(HttpServletRequest request) {
        String currentPageParam = request.getParameter(CURRENT_PAGE);
        if (isNull(currentPageParam) || currentPageParam.isEmpty()) {
            currentPageParam = DEFAULT_CURRENT_PAGE_PARAM;
        }
        return Integer.parseInt(currentPageParam);
    }

    public int getLimitPageParam(HttpServletRequest request) {
        String currentLimitParam = request.getParameter(PAGE_LIMIT);
        if (currentLimitParam == null || currentLimitParam.isEmpty()) {
            currentLimitParam = DEFAULT_CURRENT_LIMIT_PARAM;
        }
        return Integer.parseInt(currentLimitParam);
    }

    public String getSortByParameter(HttpServletRequest request) throws ControllerException {
        String sortByParameter = request.getParameter(SORT_BY);
        validator.isValid(sortByParameter);
        return sortByParameter;
    }
    public void setParametersToRequest(HttpServletRequest request, Page<Order> paginationResult, String command, String sortByParameter, String direction) {
        request.setAttribute(PAGEABLE, paginationResult);
        request.setAttribute(LAST_COMMAND, command);
        request.setAttribute(SELECTED_SORT_BY_PARAMETER, sortByParameter);
        request.setAttribute(SELECTED_DIRECTION_PARAMETER, direction);
    }
}
