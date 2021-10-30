package com.epam.project.webappcourses.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.epam.project.webappcourses.web.Path;

public class LogoutCommand extends Command {
    private static final long serialVersionUID = -2785976616686657267L;
    private static final Logger log = Logger.getLogger(LogoutCommand.class);

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");

        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();

        log.debug("Command finished");
        return new CommandResponse(CommandResponse.DispatchType.FORWARD, Path.PAGE__LOGIN);
    }
}
