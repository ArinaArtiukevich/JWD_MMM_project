package com.jwd.controller;

import com.jwd.controller.command.Command;
import com.jwd.controller.command.ParameterAttributeType;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.factory.CommandFactory;
import com.jwd.controller.resources.ConfigurationBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.ConnectException;

import static com.jwd.controller.command.ParameterAttributeType.COMMAND;
import static com.jwd.controller.command.ParameterAttributeType.ERROR;
import static com.jwd.controller.util.Util.pathToJsp;
import static com.jwd.controller.util.Util.pathToJspIndexPage;
import static java.util.Objects.nonNull;

@WebServlet(name = "controller", urlPatterns = {"/controller"})
public class FrontController extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(FrontController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("Controller was started.");
        logger.debug(request.getParameter(COMMAND));

        String page = null;
        try {
            CommandFactory commandFactory = new CommandFactory();
            Command command = commandFactory.defineManager(request);
            page = command.execute(request);
            logger.debug("using " + command);
            if (page != null) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                dispatcher.forward(request, response);
            } else {
                logger.error("Operation went wrong.");
                response.sendRedirect("/controller?command=go_to_page&path=index");
            }
        } catch (ControllerException e) {
            logger.error("Operation went wrong.");
            Throwable cause = getCause(e);
            HttpSession session = request.getSession();
            session.setAttribute(ERROR, "Exception: " + cause.getMessage());
            response.sendRedirect("/controller?command=go_to_page&path=error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private Throwable getCause(Throwable cause) {
        if (nonNull(cause.getCause())) {
            cause = getCause(cause.getCause());
        }
        return cause;
    }

}



