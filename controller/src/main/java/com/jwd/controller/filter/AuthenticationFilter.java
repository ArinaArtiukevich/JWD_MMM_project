package com.jwd.controller.filter;

import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.dao.entity.enumType.UserRole;
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
import static com.jwd.controller.factory.enumType.CommandEnum.*;
import static com.jwd.controller.util.Util.pathToJspIndexPage;

public class AuthenticationFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationFilter.class);

    private static final List<String> alwaysAvailableCommands = Arrays.asList(
            REGISTRATION.toString().toLowerCase(),
            LOGIN.toString().toLowerCase(),
            CHANGE_LANGUAGE.toString().toLowerCase(),
            SHOW_ORDERS_BY_SERVICE_TYPE.toString().toLowerCase(),
            FIND_ORDER_INFO.toString().toLowerCase(),
            GO_TO_PAGE.toString().toLowerCase()
    );
    private static final List<String> clientAvailableCommands = Arrays.asList(
            FIND_CLIENT_RESPONSE.toString().toLowerCase(),
            APPROVE_ORDER.toString().toLowerCase(),
            WORK.toString().toLowerCase(),
            ADD_SERVICE_ORDER.toString().toLowerCase(),
            LOGOUT.toString().toLowerCase(),
            UPDATE_USER.toString().toLowerCase(),
            FIND_USER_INFORMATION.toString().toLowerCase(),
            FIND_CLIENT_ORDER_BY_STATUS.toString().toLowerCase(),
            DELETE_ORDER_BY_ID.toString().toLowerCase()
    );
    private static final List<String> workerAvailableCommands = Arrays.asList(
            FIND_WORKER_RESPONSE.toString().toLowerCase(),
            CLOSE_ORDER.toString().toLowerCase(),
            TAKE_ORDER.toString().toLowerCase(),
            WORK.toString().toLowerCase(),
            LOGOUT.toString().toLowerCase(),
            UPDATE_USER.toString().toLowerCase(),
            FIND_USER_INFORMATION.toString().toLowerCase()
    );

    @Override
    public void init(FilterConfig filterConfig) {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.debug("Start AuthenticationFilter");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
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
            LOGGER.debug("Current command: " + currentCommand + " is unavailable.");
            response.sendRedirect(pathToJspIndexPage(ConfigurationBundle.getProperty("path.page.index")));
        }
    }

    @Override
    public void destroy() {
    }
}
