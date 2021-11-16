package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.command.ParameterAttributeType;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.dao.entity.enums.Gender;
import com.jwd.dao.entity.enums.UserRole;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.factory.ServiceFactory;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.serviceLogic.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.jwd.controller.command.ParameterAttributeType.ERROR;
import static com.jwd.controller.command.ParameterAttributeType.USER_ROLE;

public class LoginCommandImpl implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommandImpl.class);
    private final ControllerValidator validator = new ControllerValidator();
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest request) throws ControllerException {
        logger.info("Start LoginCommandImpl.");
        String page = null;
        String login = request.getParameter(ParameterAttributeType.LOGIN);
        String password = request.getParameter(ParameterAttributeType.PASSWORD);
        validateParameters(login, password);

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
        } catch (ServiceException e) {
            logger.error("Can't login user.");
            HttpSession session = request.getSession(true);
            session.setAttribute(ERROR,"Can't login user.");
            throw new ControllerException(e);
        }
        return page;
    }

    private void validateParameters(String login, String password) throws ControllerException {
        validator.isValid(login);
        validator.isValid(password);
    }

}
