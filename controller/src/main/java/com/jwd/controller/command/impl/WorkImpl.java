package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.entity.CommandAnswer;
import com.jwd.controller.entity.enumType.AnswerType;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.resources.ConfigurationBundle;
import com.jwd.controller.validator.ControllerValidator;

import javax.servlet.http.HttpServletRequest;

import static com.jwd.controller.command.ParameterAttributeType.*;
import static com.jwd.controller.util.Util.pathToJsp;

public class WorkImpl implements Command {
    private final ControllerValidator validator = new ControllerValidator();

    @Override
    public CommandAnswer execute(HttpServletRequest request) throws ControllerException {
        CommandAnswer answer = new CommandAnswer();
        String path = null;
        String action = request.getParameter(WORK_ACTION);
        validator.isValid(action);
        if (ADD_SERVICE_ORDER.equals(action)) {
            path = pathToJsp(ConfigurationBundle.getProperty("path.page.add.service.order"));
        }
        if (SHOW_USER_ORDER.equals(action)) {
            path = pathToJsp(ConfigurationBundle.getProperty("path.page.show.user.order"));
        }
        if (FIND_CLIENT_RESPONSE.equals(action)) {
            path = pathToJsp(ConfigurationBundle.getProperty("path.page.order.client.order.responses"));
        }
        if (FIND_WORKER_RESPONSE.equals(action)) {
            path = pathToJsp(ConfigurationBundle.getProperty("path.page.order.worker.responses"));
        }
        answer.setAnswerType(AnswerType.FORWARD);
        answer.setPath(path);
        return answer;
    }
}
