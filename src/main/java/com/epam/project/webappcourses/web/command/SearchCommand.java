package com.epam.project.webappcourses.web.command;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.epam.project.webappcourses.dao.implemented_dao.JDBCSearchTableRowDao;
import com.epam.project.webappcourses.entities.SearchTableRow;
import com.epam.project.webappcourses.exceptions.DBException;
import com.epam.project.webappcourses.web.Path;

public class SearchCommand extends Command {
    private static final long serialVersionUID = -3071536593627692473L;
    private static final Logger log = Logger.getLogger(SearchCommand.class);

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");
        HttpSession session= request.getSession();
        LinkedList<SearchTableRow> sortedByName = new LinkedList<>();
        LinkedList<SearchTableRow> sortedByDuration = new LinkedList<>();
        LinkedList<SearchTableRow> sortedByStudents = new LinkedList<>();

        String lang = (String) session.getAttribute("lang");

        try {
            sortedByName = JDBCSearchTableRowDao.getSortedCoursesByName(lang);
            sortedByDuration = JDBCSearchTableRowDao.getSortedCoursesByDuration(lang);
            sortedByStudents = JDBCSearchTableRowDao.getSortedCoursesByStudentsNumber(lang);
        } catch (DBException e) {
            e.printStackTrace();
        }

        session.setAttribute("sortedByName", sortedByName);
        session.setAttribute("sortedByDuration", sortedByDuration);
        session.setAttribute("sortedByStudents", sortedByStudents);


        String forward = Path.STUDENT_SEARCH;
        return new CommandResponse(CommandResponse.DispatchType.FORWARD, forward);
    }
}
