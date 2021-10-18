package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import javax.servlet.http.HttpServletRequest;

import static com.jwd.controller.command.ParameterAttributeType.*;


public class ChangeLanguageImpl implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        page = request.getParameter("parent_page");
        String language = request.getParameter(CHANGE_LANGUAGE);
        request.setAttribute(CHANGE_LANGUAGE, language);
        request.getSession(true).setAttribute(LANGUAGE, language);
        return page;

    }
}
