package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.dao.entity.enums.UserRole;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.LoginService;
import com.jwd.service.serviceLogic.RegistrationService;
import com.jwd.dao.entity.Registration;
import com.jwd.dao.entity.enums.Gender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.jwd.controller.command.ParameterAttributeType.*;

public class RegistrationCommandImpl implements Command {
    private static final Logger logger = LogManager.getLogger(RegistrationCommandImpl.class);

    public String execute(HttpServletRequest request) {
        logger.info("Start registration.");

        String page = null;
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String email = request.getParameter(EMAIL);
        String city = request.getParameter(CITY);
        String login = request.getParameter(LOGIN);
        //TODO char[] = request.getParameter(ParameterAttributeType.PASSWORD).toCharArray()
        String password = request.getParameter(PASSWORD);
        String confirmPassword = request.getParameter(CONFIRM_PASSWORD);
        Gender gender = Gender.valueOf(request.getParameter(GENDER).toUpperCase());
        UserRole userRole = UserRole.valueOf(request.getParameter(USER_ROLE).toUpperCase());

        Registration registration = new Registration(firstName, lastName, email, city , login, password, confirmPassword, gender, userRole);
        boolean isRegistered = false;
        Long idUser = 0L;
        try {
            isRegistered =  RegistrationService.register(registration);
            idUser = LoginService.getIdUserByLogin(login);

        } catch (ServiceException e) {
            request.setAttribute("errorRegistration", e.getMessage());
            page = ConfigurationBundle.getProperty("path.page.registration");;
            logger.error("Problems with user registration.");
        }
        if(isRegistered) {
            page = ConfigurationBundle.getProperty("path.page.login");

//            request.setAttribute(LOGIN, login);
//            request.setAttribute("message", "Add service/ Find service");
//
//            HttpSession session = request.getSession(true);
//            // TODO check id
//            session.setAttribute(USER_ID, idUser);
//            session.setAttribute(LOGIN, login);
        }
        return page;
    }


}


