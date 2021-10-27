package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class FindWorkerResponseImpl implements Command {
    private static final Logger logger = LogManager.getLogger(FindUserOrdersImpl.class);

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("Start FindWorkerResponseImpl.");

        return null;
    }
}
