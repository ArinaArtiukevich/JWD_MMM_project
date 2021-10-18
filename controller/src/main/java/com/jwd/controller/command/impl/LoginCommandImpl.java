package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.command.ParameterAttributeType;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommandImpl implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommandImpl.class);

    @Override
    public String execute(HttpServletRequest request) {
        logger.error("Start login.");
        String page = null;
        String login = request.getParameter(ParameterAttributeType.LOGIN);;
        String password = request.getParameter(ParameterAttributeType.PASSWORD);

        try {
            if(LoginService.checkLoginAndPassword(login, password)) {
                Long idUser = LoginService.getIdUserByLogin(login);
                page = ConfigurationBundle.getProperty("path.page.work");
                request.setAttribute(ParameterAttributeType.USER_ID, idUser);
                request.setAttribute(ParameterAttributeType.LOGIN, login);

                HttpSession session = request.getSession(true);
                session.setAttribute(ParameterAttributeType.USER_ID, idUser);
                session.setAttribute(ParameterAttributeType.LOGIN,login);
            } else {
                request.setAttribute("errorLoginMessage", "Invalid login or password");
                page = ConfigurationBundle.getProperty("path.page.login");
            }
        } catch (ServiceException ex) {
            logger.error("Can't activate user.");
            request.setAttribute("internalError","internalError");
            page = ConfigurationBundle.getProperty("path.page.authorization");;
        }
        return page;
    }


}
