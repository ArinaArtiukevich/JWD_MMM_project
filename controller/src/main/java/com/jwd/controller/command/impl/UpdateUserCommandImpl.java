package com.jwd.controller.command.impl;

import com.jwd.controller.command.AbstractCommand;
import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.dao.entity.Registration;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.factory.ServiceFactory;
import com.jwd.service.serviceLogic.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.jwd.controller.command.ParameterAttributeType.*;
import static com.jwd.controller.util.Util.pathToJsp;

public class UpdateUserCommandImpl extends AbstractCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(UpdateUserCommandImpl.class);
    private final ControllerValidator validator = new ControllerValidator();
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest request) throws ControllerException {
        LOGGER.info("Start UpdateUserCommandImpl.");
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
            Long idUser = getUserId(request);
            if (validatePassword(password)) {
                userInfo = new Registration(firstName, lastName, email, city);
                isUpdated = userService.updateUserWithoutPassword(idUser, userInfo);
            } else {
                userInfo = new Registration(firstName, lastName, email, city, password, confirmPassword);
                isUpdated = userService.updateUserWithPassword(idUser, userInfo);
            }
            if (isUpdated) {
                page = pathToJsp(ConfigurationBundle.getProperty("path.page.work"));
                request.setAttribute(MESSAGE, "Profile information was changed.");

                request.setAttribute(FIRST_NAME, userInfo.getFirstName());
                request.setAttribute(LAST_NAME, userInfo.getLastName());
                request.setAttribute(EMAIL, userInfo.getEmail());
                request.setAttribute(CITY, userInfo.getCity());
                request.setAttribute(LAST_COMMAND, UPDATE_USER);
            } else {
                LOGGER.error("Personal information was not updated.");
                throw new ControllerException("Personal information was not updated.");
            }
        } catch (ServiceException | NumberFormatException e) {
            LOGGER.error("Could not update user information.");
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
