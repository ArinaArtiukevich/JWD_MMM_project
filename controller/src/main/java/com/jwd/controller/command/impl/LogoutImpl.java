package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.resources.ConfigurationBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.jwd.controller.util.Util.*;

public class LogoutImpl implements Command {
    private static final Logger logger = LogManager.getLogger(LogoutImpl.class);

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("Start LogoutImpl.");
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return pathToJspIndexPage(ConfigurationBundle.getProperty("path.page.index"));
    }


}
