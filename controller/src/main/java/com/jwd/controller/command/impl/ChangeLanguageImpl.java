package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.resources.ConfigurationBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.jwd.controller.command.ParameterAttributeType.*;
import static com.jwd.controller.util.Util.pathToJsp;
import static com.jwd.controller.util.Util.pathToJspCheckIsIndexPage;


public class ChangeLanguageImpl implements Command {
    private static final Logger logger = LogManager.getLogger(ChangeLanguageImpl.class);

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("Start ChangeLanguageImpl.");
        String page = null;
        page = pathToJspCheckIsIndexPage(request.getParameter("parent_page"));
        String language = request.getParameter(CHANGE_LANGUAGE);
        if (language != null) {
            request.setAttribute(CHANGE_LANGUAGE, language);
            request.getSession(true).setAttribute(LANGUAGE, language);
        } else {
            logger.error("Can not find locale.");
            page = pathToJsp(ConfigurationBundle.getProperty("path.page.error"));
        }
        return page;

    }
}
