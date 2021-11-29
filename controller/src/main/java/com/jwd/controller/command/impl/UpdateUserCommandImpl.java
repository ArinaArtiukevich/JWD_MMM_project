package com.jwd.controller.command.impl;

import com.jwd.controller.command.AbstractCommand;
import com.jwd.controller.command.Command;
import com.jwd.controller.entity.CommandAnswer;
import com.jwd.controller.entity.enums.AnswerType;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.dao.entity.Registration;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.factory.ServiceFactory;
import com.jwd.service.serviceLogic.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.jwd.controller.command.ParameterAttributeType.*;
import static com.jwd.controller.util.Util.pathToJsp;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class UpdateUserCommandImpl extends AbstractCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(UpdateUserCommandImpl.class);
    private final ControllerValidator validator = new ControllerValidator();
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public CommandAnswer execute(HttpServletRequest request) throws ControllerException {
        LOGGER.info("Start UpdateUserCommandImpl.");
        CommandAnswer answer = new CommandAnswer();
        String path = null;
        Registration userInfo;
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String email = request.getParameter(EMAIL);
        String city = request.getParameter(CITY);
        validateParameters(firstName, lastName, email, city);
        String password = request.getParameter(PASSWORD);
        String confirmPassword = request.getParameter(CONFIRM_PASSWORD);
        boolean isUpdated = false;
        HttpSession session = request.getSession();
        try {
            Long idUser = getUserId(request);
            if (validatePasswords(password, confirmPassword)) {
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                String hashedConfirmPassword = BCrypt.hashpw(confirmPassword, BCrypt.gensalt());
                userInfo = new Registration(firstName, lastName, email, city, hashedPassword, hashedConfirmPassword);
                isUpdated = userService.updateUserWithPassword(idUser, userInfo);
            } else {
                userInfo = new Registration(firstName, lastName, email, city);
                isUpdated = userService.updateUserWithoutPassword(idUser, userInfo);
            }
            if (isUpdated) {
                path = GO_TO_WORK_PAGE;
                session.setAttribute(MESSAGE, "Profile information was updated.");
                session.setAttribute(USER, userInfo);
               // request.setAttribute(LAST_COMMAND, UPDATE_USER); todo ???
            } else {
                LOGGER.error("Personal information was not updated.");
                throw new ControllerException("Personal information was not updated.");
            }
        } catch (ServiceException | NumberFormatException e) {
            LOGGER.error("Could not update user information.");
            throw new ControllerException(e);
        }
        answer.setPath(path);
        answer.setAnswerType(AnswerType.REDIRECT);
        return answer;
    }

    private boolean validatePasswords(String password, String confirmPassword) {
        boolean isValidated = false;
        if (nonNull(password) && !password.isEmpty() && nonNull(confirmPassword) && !confirmPassword.isEmpty()) {
            if (password.equals(confirmPassword)) {
                isValidated = true;
            }
        }
        return isValidated;
    }

    private void validateParameters(String firstName, String lastName, String email, String city) throws ControllerException {
        validator.isValid(firstName);
        validator.isValid(lastName);
        validator.isValid(email);
        validator.isValid(city);
    }

}
