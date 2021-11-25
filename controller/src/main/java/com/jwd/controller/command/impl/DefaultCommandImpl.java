package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.resources.ConfigurationBundle;

import javax.servlet.http.HttpServletRequest;

import static com.jwd.controller.command.ParameterAttributeType.ERROR_INTERNAL;
import static com.jwd.controller.util.Util.*;

public class DefaultCommandImpl implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = pathToJspIndexPage(ConfigurationBundle.getProperty("path.page.index"));
        request.setAttribute(ERROR_INTERNAL, "Command wasn't found.");
        return page;
    }
}
