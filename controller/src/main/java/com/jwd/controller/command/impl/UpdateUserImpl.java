package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.dao.config.DataBaseConfig;
import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.entity.Registration;
import com.jwd.dao.entity.UserDTO;
import com.jwd.dao.repository.LoginDao;
import com.jwd.dao.repository.impl.LoginDaoImpl;
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

public class UpdateUserImpl implements Command {
    private static final Logger logger = LogManager.getLogger(RegistrationCommandImpl.class);
    private final ControllerValidator validator = new ControllerValidator();
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest request) throws ControllerException {
        logger.info("Start UpdateUserImpl.");
        String page = null;
        Registration userInfo;
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String email = request.getParameter(EMAIL);
        String city = request.getParameter(CITY);
//         todo
//        char[] password = request.getParameter(PASSWORD).toCharArray();
//        char[] confirmPassword = request.getParameter(CONFIRM_PASSWORD).toCharArray();
        String password = request.getParameter(PASSWORD);
        String confirmPassword = request.getParameter(CONFIRM_PASSWORD);
        validateParameters(firstName, lastName, email, city);
        boolean isUpdated = false;
        try {
            String idUserString = request.getParameter(USER_ID);
            validator.isValid(idUserString);
            Long idUser = Long.parseLong(idUserString);
            validator.isValid(idUser);
            if (validatePassword(password)) {
                userInfo = new Registration(firstName, lastName, email, city) ;
                isUpdated = userService.updateUserWithoutPassword(idUser, userInfo);
            } else {
                userInfo = new Registration(firstName, lastName, email, city, password, confirmPassword) ;
                isUpdated = userService.updateUserWithPassword(idUser, userInfo);
            }
            if(isUpdated) {
                page = pathToJsp(ConfigurationBundle.getProperty("path.page.work"));
                request.setAttribute(MESSAGE, "Profile information was changed.");

                request.setAttribute(FIRST_NAME, userInfo.getFirstName());
                request.setAttribute(LAST_NAME, userInfo.getLastName());
                request.setAttribute(EMAIL, userInfo.getEmail());
                request.setAttribute(CITY, userInfo.getCity());
                request.setAttribute(LAST_COMMAND, UPDATE_USER);
            } // TODO else
        } catch (ServiceException e) {
            logger.error("Could not update user information.");
            throw new ControllerException(e);
        }
        return page;
    }

    private boolean validatePassword(String password) {
        return (password.isEmpty());
    }

    private void validateParameters(String firstName, String lastName, String email, String city) throws ControllerException {
        validator.isValid(firstName);
        validator.isValid(lastName);
        validator.isValid(email);
        validator.isValid(city);
    }

}
