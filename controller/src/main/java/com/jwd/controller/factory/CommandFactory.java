package com.jwd.controller.factory;

import com.jwd.controller.command.Command;
import com.jwd.controller.command.impl.DefaultCommandImpl;
import com.jwd.controller.factory.enumType.CommandEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.jwd.controller.command.ParameterAttributeType.COMMAND;
import static java.util.Objects.isNull;

public class CommandFactory {
    private static final Logger LOGGER = LogManager.getLogger(CommandFactory.class);

    public Command defineManager(HttpServletRequest request) {
        LOGGER.info("Start defineManager(HttpServletRequest request).");

        Command command = new DefaultCommandImpl();
        String commandParameter = request.getParameter(COMMAND);

        if (!isNull(commandParameter) && !commandParameter.isEmpty()) {
            try {
                CommandEnum currentCommand = CommandEnum.valueOf(commandParameter.toUpperCase());
                command = currentCommand.getCommand();
                LOGGER.info("command = " + commandParameter);

            } catch (IllegalArgumentException e) {
                LOGGER.error("Illegal parameters");
            }
        } else {
            LOGGER.error("Invalid command parameter = " + commandParameter);
        }
        return command;
    }
}
