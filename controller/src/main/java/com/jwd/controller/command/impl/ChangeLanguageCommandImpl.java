package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.entity.CommandAnswer;
import com.jwd.controller.entity.enumType.AnswerType;
import com.jwd.controller.resources.ConfigurationBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.jwd.controller.command.ParameterAttributeType.*;
import static com.jwd.controller.util.Util.pathToJsp;
import static com.jwd.controller.util.Util.pathToJspCheckIsIndexPage;


public class ChangeLanguageCommandImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ChangeLanguageCommandImpl.class);

    @Override
    public CommandAnswer execute(HttpServletRequest request) {
        LOGGER.info("Start ChangeLanguageCommandImpl.");
        CommandAnswer answer = new CommandAnswer();
        String path = null;
        path = pathToJspCheckIsIndexPage(request.getParameter(PARENT_PAGE));
        String language = request.getParameter(CHANGE_LANGUAGE);
        if (language != null) {
            request.setAttribute(CHANGE_LANGUAGE, language);
            request.getSession(true).setAttribute(LANGUAGE, language);
        } else {
            LOGGER.error("Can not find locale.");
            path = pathToJsp(ConfigurationBundle.getProperty("path.page.error"));
        }
        answer.setPath(path);
        answer.setAnswerType(AnswerType.FORWARD);
        return answer;
    }
}
