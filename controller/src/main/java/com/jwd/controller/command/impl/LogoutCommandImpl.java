package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.entity.CommandAnswer;
import com.jwd.controller.entity.enumType.AnswerType;
import com.jwd.controller.resources.ConfigurationBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.jwd.controller.util.Util.*;

public class LogoutCommandImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LogoutCommandImpl.class);

    @Override
    public CommandAnswer execute(HttpServletRequest request) {
        LOGGER.info("Start LogoutCommandImpl.");
        CommandAnswer answer = new CommandAnswer();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        answer.setPath(pathToJspIndexPage(ConfigurationBundle.getProperty("path.page.index")));
        answer.setAnswerType(AnswerType.FORWARD);
        return answer;
    }


}
