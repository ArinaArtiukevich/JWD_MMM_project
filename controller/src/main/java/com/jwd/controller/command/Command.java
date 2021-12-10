package com.jwd.controller.command;

import com.jwd.controller.entity.CommandAnswer;
import com.jwd.controller.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface for all commands.
 */
public interface Command {
    CommandAnswer execute(HttpServletRequest request) throws ControllerException;
}
