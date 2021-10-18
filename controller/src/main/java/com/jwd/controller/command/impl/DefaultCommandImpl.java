package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.resources.ConfigurationBundle;

import javax.servlet.http.HttpServletRequest;

public class DefaultCommandImpl implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationBundle.getProperty("path.page.index");
        request.setAttribute("internalError", "Command wasn't found.");
        return page;
    }
}
