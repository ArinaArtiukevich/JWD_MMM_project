package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.entity.CommandAnswer;
import com.jwd.controller.entity.enumType.AnswerType;
import com.jwd.controller.resources.ConfigurationBundle;

import javax.servlet.http.HttpServletRequest;

import static com.jwd.controller.command.ParameterAttributeType.ERROR_INTERNAL;
import static com.jwd.controller.util.Util.*;

public class DefaultCommandImpl implements Command {

    @Override
    public CommandAnswer execute(HttpServletRequest request) {
        CommandAnswer answer = new CommandAnswer();
        String path = pathToJspIndexPage(ConfigurationBundle.getProperty("path.page.index"));
        request.setAttribute(ERROR_INTERNAL, "Command wasn't found.");
        answer.setPath(path);
        answer.setAnswerType(AnswerType.FORWARD);
        return answer;
    }
}
