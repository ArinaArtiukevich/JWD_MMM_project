package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.command.ParameterAttributeType;
import com.jwd.controller.entity.CommandAnswer;
import com.jwd.controller.entity.enums.AnswerType;
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

import static com.jwd.controller.command.ParameterAttributeType.*;
import static com.jwd.controller.util.Util.pathToJsp;

public class LoginCommandImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LoginCommandImpl.class);
    private final ControllerValidator validator = new ControllerValidator();
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public CommandAnswer execute(HttpServletRequest request) throws ControllerException {
        LOGGER.info("Start LoginCommandImpl.");
        CommandAnswer answer = new CommandAnswer();
        String path = null;
        try {
            String login = request.getParameter(ParameterAttributeType.LOGIN);
            String password = request.getParameter(ParameterAttributeType.PASSWORD);
            validateParameters(login, password);
            if (userService.isLoginAndPasswordExist(login, password)) {
                Long idUser = userService.getIdUserByLogin(login);// todo in one request?
                UserRole userRole = userService.getRoleByID(idUser);

                path = pathToJsp(ConfigurationBundle.getProperty("path.page.work"));
                request.setAttribute(ParameterAttributeType.USER_ID, idUser);
                request.setAttribute(ParameterAttributeType.LOGIN, login);

                HttpSession session = request.getSession(true);
                session.setAttribute(ParameterAttributeType.USER_ID, idUser);
                session.setAttribute(ParameterAttributeType.LOGIN, login);
                session.setAttribute(USER_ROLE, userRole.getName());
            } else {
                request.setAttribute(ERROR_LOGIN_MESSAGE, "Invalid login or password");
                path = pathToJsp(ConfigurationBundle.getProperty("path.page.login"));
            }

        } catch (ServiceException e) {
            LOGGER.error("Can't login user.");
            HttpSession session = request.getSession(true);
            session.setAttribute(ERROR, "Can't login user.");
            throw new ControllerException(e);
        }
        answer.setPath(path);
        answer.setAnswerType(AnswerType.FORWARD);
        return answer;
    }

    private void validateParameters(String login, String password) throws ControllerException {
        validator.isValid(login);
        validator.isValid(password);
    }

}
