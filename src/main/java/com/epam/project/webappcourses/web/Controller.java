package com.epam.project.webappcourses.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epam.project.webappcourses.web.command.Command;
import com.epam.project.webappcourses.web.command.CommandContainer;
import com.epam.project.webappcourses.web.command.CommandResponse;

public class Controller extends HttpServlet {
    private static final long serialVersionUID = 2423353715955164816L;

    private static final Logger log = Logger.getLogger(Controller.class);

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
        response.setCharacterEncoding("UTF-8");
    }

    /**
     * Main method of this controller.
     */
    private void process(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        log.debug("Controller starts");

        // extract command name from the request
        String commandName = request.getParameter("command");
        log.trace("Request parameter: command --> " + commandName);

        // obtain command object by its name
        Command command = CommandContainer.get(commandName);
        log.trace("Obtained command --> " + command);

        // execute command and get forward address
        CommandResponse commandResponse = command.execute(request, response);
        log.trace("Forward address --> " + commandResponse.getUrl());

        log.debug("Controller finished, now go to forward address --> " + commandResponse.getUrl());

        // if the forward address is not null go to the address
        switch (commandResponse.getDispatchType()) {
            case FORWARD: {
                RequestDispatcher disp = request.getRequestDispatcher(commandResponse.getUrl());
                disp.forward(request, response);
                break;
            }
            case SEND_REDIRECT: {
                response.sendRedirect(commandResponse.getUrl());
            }
        }
    }
}
