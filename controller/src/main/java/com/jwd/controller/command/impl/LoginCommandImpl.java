package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.command.ParameterAttributeType;
import com.jwd.controller.entity.CommandAnswer;
import com.jwd.controller.entity.enums.AnswerType;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.dao.entity.User;
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
        HttpSession session = request.getSession(true);
        try {
            String login = request.getParameter(ParameterAttributeType.LOGIN);
            String password = request.getParameter(ParameterAttributeType.PASSWORD);
            validateParameters(login, password);
            if (userService.isLoginAndPasswordExist(login, password)) {
                User user = userService.getUserByLogin(login);// todo in one request?
                session.setAttribute(USER_ID, user.getIdUser());
                session.setAttribute(LOGIN, login);
                session.setAttribute(USER_ROLE, user.getUserRole().getName());
                session.setAttribute(USER, user);
                path = GO_TO_WORK_PAGE;
            } else {
                session.setAttribute(ERROR_LOGIN_MESSAGE, "Invalid login or password");
                path = GO_TO_LOGIN_PAGE;
            }
            answer.setPath(path);
            answer.setAnswerType(AnswerType.REDIRECT);
        } catch (ServiceException e) {
            LOGGER.error("Can't login user.");
            session.setAttribute(ERROR, "Can't login user.");
            throw new ControllerException(e);
        }
        return answer;
    }

    private void validateParameters(String login, String password) throws ControllerException {
        validator.isValid(login);
        validator.isValid(password);
    }

}
