package com.jwd.controller;

import com.jwd.controller.command.Command;
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
import java.io.IOException;

@WebServlet(name = "controller", urlPatterns = {"/controller"})
public class FrontController extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(FrontController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("Controller was started.");
        logger.debug(request.getParameter("command"));

        CommandFactory commandFactory = new CommandFactory();
        Command command = commandFactory.defineManager(request);
        String page = command.execute(request);
        logger.debug("using " + command);

        if(page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
        else {
            logger.error("Operation went wrong.");
            page = ConfigurationBundle.getProperty("path.page.index");;
            response.sendRedirect(request.getContextPath() + page);
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

}



