package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.command.ParameterAttributeType;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.dao.entity.enums.UserRole;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.factory.ServiceFactory;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.dao.entity.Registration;
import com.jwd.dao.entity.enums.Gender;
import com.jwd.service.serviceLogic.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.jwd.controller.command.ParameterAttributeType.*;
import static com.jwd.controller.util.Util.pathToJsp;

public class RegistrationCommandImpl implements Command {
    private static final Logger logger = LogManager.getLogger(RegistrationCommandImpl.class);
    private final ControllerValidator validator = new ControllerValidator();
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    public String execute(HttpServletRequest request) throws ControllerException {
        logger.info("Start registration.");
        String page = null;
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String email = request.getParameter(EMAIL);
        String city = request.getParameter(CITY);
        String login = request.getParameter(LOGIN);
//         todo
//        char[] password = request.getParameter(PASSWORD).toCharArray();
//        char[] confirmPassword = request.getParameter(CONFIRM_PASSWORD).toCharArray();
        String password = request.getParameter(PASSWORD);
        String confirmPassword = request.getParameter(CONFIRM_PASSWORD);
        String genderString = request.getParameter(GENDER);
        validator.isValid(genderString);
        String userRoleString = request.getParameter(USER_ROLE);
        validator.isValid(userRoleString);
        Gender gender = Gender.valueOf(genderString.toUpperCase());
        UserRole userRole = UserRole.valueOf(userRoleString.toUpperCase());

        validateParameters(firstName, lastName, email, city, login, password, confirmPassword, genderString, gender, userRole);
        Registration registration = new Registration(firstName, lastName, email, city , login, password, confirmPassword, gender, userRole);
        boolean isRegistered = false;
        Long idUser = 0L;
        try {
            isRegistered = userService.register(registration);
            idUser = userService.getIdUserByLogin(login);

            if(isRegistered) {
                page = pathToJsp(ConfigurationBundle.getProperty("path.page.work"));

                request.setAttribute(LOGIN, login);

                HttpSession session = request.getSession(true);
                session.setAttribute(USER_ID, idUser);
                session.setAttribute(LOGIN, login);
                session.setAttribute(USER_ROLE, userRole.getName());
            } // TODO else
        } catch (ServiceException e) {
            HttpSession session = request.getSession(true);
            session.setAttribute(ERROR, "Registration failed " + e.getMessage());
            logger.error("Problems with user registration.");
            throw new ControllerException(e);
        }
        return page;
    }

    private void validateParameters(String firstName, String lastName, String email, String city, String login, String password, String confirmPassword, String genderString, Gender gender, UserRole userRole) throws ControllerException {
        validator.isValid(firstName);
        validator.isValid(lastName);
        validator.isValid(email);
        validator.isValid(city);
        validator.isValid(login);
        validator.isValid(password);
        validator.isValid(confirmPassword);
        validator.isValid(genderString);
        validator.isValid(gender);
        validator.isValid(userRole);
    }


}


