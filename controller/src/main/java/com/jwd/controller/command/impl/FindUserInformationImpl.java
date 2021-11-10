package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.controller.validator.ControllerValidator;
import com.jwd.dao.entity.User;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.serviceLogic.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.jwd.controller.command.ParameterAttributeType.*;

public class FindUserInformationImpl implements Command {
    private static final Logger logger = LogManager.getLogger(FindUserInformationImpl.class);
    private final ControllerValidator validator = new ControllerValidator();
    private final UserService userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request) throws ControllerException {
        logger.info("Start FindUserInformationImpl.");
        String page = null;
        try {
            String idUserString = request.getParameter(USER_ID);
            validator.isValid(idUserString);
            Long idUser = Long.parseLong(idUserString);
            validator.isValid(idUser);
            User user = new User();
            user = userService.getUserById(idUser);
            // todo session?
            request.setAttribute(FIRST_NAME, user.getFirstName());
            request.setAttribute(LAST_NAME, user.getLastName());
            request.setAttribute(EMAIL, user.getEmail());
            request.setAttribute(CITY, user.getCity());
            request.setAttribute(LOGIN, user.getLogin());
            request.setAttribute(GENDER,user.getGender());
            request.setAttribute(LAST_COMMAND, FIND_USER_INFORMATION);
            page = ConfigurationBundle.getProperty("path.page.work");
        } catch (NumberFormatException | ServiceException e) {
            logger.error("Could not find user.");
            throw new ControllerException(e);
        }
        return page;

    }
}
