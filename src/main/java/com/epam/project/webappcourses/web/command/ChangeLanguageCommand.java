package com.epam.project.webappcourses.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeLanguageCommand extends Command {

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        final String language = request.getParameter("lang");
        final HttpSession session = request.getSession();
        session.setAttribute("lang", language);
        return new CommandResponse(CommandResponse.DispatchType.SEND_REDIRECT, request.getContextPath() + "/controller?command=login");
    }
}
