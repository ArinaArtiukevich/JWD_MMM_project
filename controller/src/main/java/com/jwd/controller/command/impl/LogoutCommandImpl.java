package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.resources.ConfigurationBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.jwd.controller.util.Util.*;

public class LogoutCommandImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LogoutCommandImpl.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("Start LogoutCommandImpl.");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return pathToJspIndexPage(ConfigurationBundle.getProperty("path.page.index"));
    }


}
