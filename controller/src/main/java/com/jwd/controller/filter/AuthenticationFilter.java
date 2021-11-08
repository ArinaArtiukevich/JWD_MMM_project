package com.jwd.controller.filter;

import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.dao.entity.enums.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.jwd.controller.command.ParameterAttributeType.COMMAND;
import static com.jwd.controller.command.ParameterAttributeType.USER_ROLE;
import static com.jwd.controller.factory.enums.CommandEnum.*;

public class AuthenticationFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(AuthenticationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("Start AuthenticationFilter");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        final List<String> alwaysAvailableCommands = Arrays.asList(
                REGISTRATION.toString().toLowerCase(),
                LOGIN.toString().toLowerCase(),
                SHOW_ALL_SERVICES.toString().toLowerCase(),
                CHANGE_LANGUAGE.toString().toLowerCase(),
                SHOW_ORDERS_BY_SERVICE_TYPE.toString().toLowerCase(),
                FIND_ORDER_INFO.toString().toLowerCase()
        );
        final List<String> clientAvailableCommands = Arrays.asList(
                FIND_CLIENT_RESPONSE.toString().toLowerCase(),
                APPROVE_ORDER.toString().toLowerCase(),
                SHOW_USER_ORDERS.toString().toLowerCase(),
                WORK.toString().toLowerCase(),
                ADD_SERVICE_ORDER.toString().toLowerCase(),
                LOGOUT.toString().toLowerCase()
        );
        final List<String> workerAvailableCommands = Arrays.asList(
                FIND_WORKER_RESPONSE.toString().toLowerCase(),
                CLOSE_ORDER.toString().toLowerCase(),
                TAKE_ORDER.toString().toLowerCase(),
                WORK.toString().toLowerCase(),
                LOGOUT.toString().toLowerCase()
        );
        boolean result = false;
        String currentCommand = req.getParameter(COMMAND);
        if ((request.getSession().getAttribute(USER_ROLE) == null)
                && (alwaysAvailableCommands.contains(currentCommand))) {
            result = true;
        } else if (request.getSession().getAttribute(USER_ROLE) != null) {
            if (((request.getSession().getAttribute(USER_ROLE).toString().toLowerCase()).equals(UserRole.CLIENT.getName()))
                    && (alwaysAvailableCommands.contains(currentCommand) || clientAvailableCommands.contains(currentCommand))) {
                result = true;
            } else if (((request.getSession().getAttribute(USER_ROLE).toString().toLowerCase()).equals(UserRole.WORKER.getName())) &&
                    (alwaysAvailableCommands.contains(currentCommand) || workerAvailableCommands.contains(currentCommand))) {
                result = true;
            }
        }
        if (result) {
            filterChain.doFilter(request, response);
        } else {
            logger.debug("Current command: " + currentCommand + " is unavailable.");
            response.sendRedirect(ConfigurationBundle.getProperty("path.page.index"));
        }
    }

    @Override
    public void destroy() {
    }
}
