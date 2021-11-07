package com.jwd.controller.command;

import com.jwd.controller.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest request) throws ControllerException;
}
