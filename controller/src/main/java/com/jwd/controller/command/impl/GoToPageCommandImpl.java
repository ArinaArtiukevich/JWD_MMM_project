package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.validator.ControllerValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


import static com.jwd.controller.command.ParameterAttributeType.PATH;
import static com.jwd.controller.util.Util.pathToJspCheckIsIndexPage;

public class GoToPageCommandImpl implements Command {
    private static final Logger logger = LogManager.getLogger(GoToPageCommandImpl.class);
    private final ControllerValidator validator = new ControllerValidator();

    @Override
    public String execute(HttpServletRequest request) throws ControllerException {
        logger.info("Start GoToPageCommandImpl.");
        String page = null;
        String page_path = request.getParameter(PATH);
        validator.isValid(page_path);
        page = pathToJspCheckIsIndexPage(page_path);
        // todo !!! if catch from frontController stackoverflow
        return page;
    }
}
