package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.entity.CommandAnswer;
import com.jwd.controller.entity.enumType.AnswerType;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.dao.entity.User;
import com.jwd.dao.entity.enumType.UserRole;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.factory.ServiceFactory;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.dao.entity.Registration;
import com.jwd.dao.entity.enumType.Gender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.jwd.controller.command.ParameterAttributeType.*;

public class RegistrationCommandImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(RegistrationCommandImpl.class);
    private final ControllerValidator validator = new ControllerValidator();
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    public CommandAnswer execute(HttpServletRequest request) throws ControllerException {
        LOGGER.info("Start registration.");
        CommandAnswer answer = new CommandAnswer();
        String path = null;
        try {
            String firstName = request.getParameter(FIRST_NAME);
            String lastName = request.getParameter(LAST_NAME);
            String email = request.getParameter(EMAIL);
            String city = request.getParameter(CITY);
            String login = request.getParameter(LOGIN);
            String genderString = request.getParameter(GENDER);
            validator.isValid(genderString);
            String userRoleString = request.getParameter(USER_ROLE);
            validator.isValid(userRoleString);
            Gender gender = Gender.valueOf(genderString.toUpperCase());
            UserRole userRole = UserRole.valueOf(userRoleString.toUpperCase());
            String password = request.getParameter(PASSWORD);
            String confirmPassword = request.getParameter(CONFIRM_PASSWORD);
            validatePassword(password, confirmPassword);
            HttpSession session = request.getSession(true);
            if (password.equals(confirmPassword)) {
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                String hashedConfirmPassword = BCrypt.hashpw(confirmPassword, BCrypt.gensalt());
                Registration registration = new Registration(firstName, lastName, email, city, login, hashedPassword, hashedConfirmPassword, gender, userRole);
                validateParameters(registration);
                boolean isRegistered = false;
                Long idUser = 0L;
                isRegistered = userService.register(registration);
                User user = userService.getUserByLogin(login);

                if (isRegistered) {
                    session.setAttribute(USER, user);
                    session.setAttribute(USER_ID, user.getIdUser());
                    session.setAttribute(LOGIN, login);
                    session.setAttribute(USER_ROLE, userRole.getName());
                    path = GO_TO_WORK_PAGE;
                } else {
                    LOGGER.error("Registration failed.");
                    throw new ControllerException("Registration failed. Please, try again.");
                }
            } else {
                session.setAttribute(ERROR_REGISTRATION, "Passwords are not equal.");
                path = GO_TO_REGISTRATION_PAGE;
            }
        } catch (ServiceException e) {
            LOGGER.error("Problems with user registration.");
            throw new ControllerException("Registration failed " + e.getMessage());
        }
        answer.setPath(path);
        answer.setAnswerType(AnswerType.REDIRECT);
        return answer;
    }

    private void validateParameters(Registration registration) throws ControllerException {
        validator.isValid(registration.getFirstName());
        validator.isValid(registration.getLastName());
        validator.isValid(registration.getEmail());
        validator.isValid(registration.getCity());
        validator.isValid(registration.getLogin());
        validator.isValid(registration.getGender());
        validator.isValid(registration.getUserRole());
    }

    private void validatePassword(String password, String confirmPassword) throws ControllerException {
        validator.isValid(password);
        validator.isValid(confirmPassword);
    }

}


