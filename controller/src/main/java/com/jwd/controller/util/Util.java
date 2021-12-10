package com.jwd.controller.util;

import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.jwd.controller.command.ParameterAttributeType.*;
import static java.util.Objects.isNull;

public class Util {
    public static Long getUserId(HttpServletRequest request) throws ControllerException {
        HttpSession session = request.getSession();
        String idUserParameter = String.valueOf(session.getAttribute(USER_ID));
        validate(idUserParameter);
        Long idUser = Long.parseLong(idUserParameter);
        validate(idUser);
        return idUser;
    }

    public static Long getOrderId(HttpServletRequest request) throws ControllerException {
        String idOrderString = request.getParameter(ID_SERVICE);
        validate(idOrderString);
        Long idOrder = Long.parseLong(idOrderString);
        validate(idOrder);
        return idOrder;
    }

    public static int getCurrentPageParam(HttpServletRequest request) {
        String currentPageParam = request.getParameter(CURRENT_PAGE);
        if (isNull(currentPageParam) || currentPageParam.isEmpty()) {
            currentPageParam = DEFAULT_CURRENT_PAGE_PARAM;
        }
        return Integer.parseInt(currentPageParam);
    }

    public static int getLimitPageParam(HttpServletRequest request) {
        String currentLimitParam = request.getParameter(PAGE_LIMIT);
        if (currentLimitParam == null || currentLimitParam.isEmpty()) {
            currentLimitParam = DEFAULT_CURRENT_LIMIT_PARAM;
        }
        return Integer.parseInt(currentLimitParam);
    }

    public static String getSortByParameter(HttpServletRequest request) throws ControllerException {
        String sortByParameter = request.getParameter(SORT_BY);
        validate(sortByParameter);
        return sortByParameter;
    }

    public static void setParametersToRequest(HttpServletRequest request, Page<Order> paginationResult, String command, String sortByParameter, String direction) {
        request.setAttribute(PAGEABLE, paginationResult);
        request.setAttribute(LAST_COMMAND, command);
        request.setAttribute(SELECTED_SORT_BY_PARAMETER, sortByParameter);
        request.setAttribute(SELECTED_DIRECTION_PARAMETER, direction);
    }

    public static String pathToJspCheckIsIndexPage(final String page_path) {
        return ConfigurationBundle.getProperty("path.page.index").equals(page_path) ?
                pathToJspIndexPage(page_path) :
                pathToJsp(page_path);
    }

    public static String pathToJsp(final String page_path) {
        return ConfigurationBundle.getProperty("path.folder.web.inf") + ConfigurationBundle.getProperty("path.folder.jsp") + page_path + ConfigurationBundle.getProperty("path.jsp");
    }

    public static String pathToJspIndexPage(final String page_path) {
        return ConfigurationBundle.getProperty("path.folder.empty") + page_path + ConfigurationBundle.getProperty("path.jsp");
    }

    public static void validate(String string) throws ControllerException {
        if (isNull(string) || string.isEmpty()) {
            throw new ControllerException("String is null or empty.");
        }
    }

    public static void validate(Long id) throws ControllerException {
        if (isNull(id)) {
            throw new ControllerException("Id is invalid.");
        }
    }
}
