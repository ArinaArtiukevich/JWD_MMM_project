package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.entity.CommandAnswer;
import com.jwd.controller.entity.enumType.AnswerType;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.dao.entity.User;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.factory.ServiceFactory;
import com.jwd.service.serviceLogic.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.jwd.controller.command.ParameterAttributeType.*;
import static com.jwd.controller.util.Util.*;

public class FindUserInformationCommandImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(FindUserInformationCommandImpl.class);
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public CommandAnswer execute(HttpServletRequest request) throws ControllerException {
        LOGGER.info("Start FindUserInformationCommandImpl.");
        CommandAnswer answer = new CommandAnswer();
        String path = null;
        try {
            Long idUser = getUserId(request);
            User user = userService.getUserById(idUser);
            request.setAttribute(USER, user);
            request.setAttribute(LAST_COMMAND, FIND_USER_INFORMATION);
            answer.setPath(pathToJsp(ConfigurationBundle.getProperty("path.page.work")));
            answer.setAnswerType(AnswerType.FORWARD);
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.error("Could not find user.");
            throw new ControllerException(e);
        }
        return answer;
    }
}
