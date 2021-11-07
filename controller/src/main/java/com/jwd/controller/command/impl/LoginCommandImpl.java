package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.command.ParameterAttributeType;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.dao.entity.enums.UserRole;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.serviceLogic.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.jwd.controller.command.ParameterAttributeType.USER_ROLE;

public class LoginCommandImpl implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommandImpl.class);
    UserService userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("Start LoginCommandImpl.");
        String page = null;
        String login = request.getParameter(ParameterAttributeType.LOGIN);;
        String password = request.getParameter(ParameterAttributeType.PASSWORD);

        try {
            if(userService.checkLoginAndPassword(login, password)) {
                Long idUser = userService.getIdUserByLogin(login);
                UserRole userRole = userService.getRoleByID(idUser);

                page = ConfigurationBundle.getProperty("path.page.work");
                request.setAttribute(ParameterAttributeType.USER_ID, idUser);
                request.setAttribute(ParameterAttributeType.LOGIN, login);

                HttpSession session = request.getSession(true);
                session.setAttribute(ParameterAttributeType.USER_ID, idUser);
                session.setAttribute(ParameterAttributeType.LOGIN, login);
                session.setAttribute(USER_ROLE, userRole.getName());
            } else {
                request.setAttribute("errorLoginMessage", "Invalid login or password");
                page = ConfigurationBundle.getProperty("path.page.login");
            }
        } catch (ServiceException ex) {
            logger.error("Can't login user.");
            request.setAttribute("error","Can't login user.");
            page = ConfigurationBundle.getProperty("path.page.error");;
        }
        return page;
    }


}
