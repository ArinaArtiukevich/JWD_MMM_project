package com.jwd.controller.factory;

import com.jwd.controller.command.Command;
import com.jwd.controller.command.impl.DefaultCommandImpl;
import com.jwd.controller.factory.enums.CommandEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {

    private static final Logger logger = LogManager.getLogger(CommandFactory.class);

    public Command defineManager(HttpServletRequest request) {
        logger.info("Start defineManager(HttpServletRequest request).");

        Command command = new DefaultCommandImpl();
        String commandParameter = request.getParameter("command").toUpperCase();

        if(commandParameter != null && !commandParameter.isEmpty()) {
            try {
                CommandEnum currentCommand = CommandEnum.valueOf(commandParameter);
                command = currentCommand.getCommand();
                logger.info("command = " + commandParameter);

            } catch(IllegalArgumentException e) {
                logger.info("Illegal parameters");
            }
        } else {
            logger.info("Invalid command parameter = " + commandParameter);
        }
        return command;
    }
}
