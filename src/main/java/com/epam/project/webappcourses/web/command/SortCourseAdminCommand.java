package com.epam.project.webappcourses.web.command;

import com.epam.project.webappcourses.dao.implemented_dao.JDBCCoursesTableForAdminRowDao;
import com.epam.project.webappcourses.entities.CoursesTableForAdminRow;
import com.epam.project.webappcourses.exceptions.DBException;
import com.epam.project.webappcourses.web.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SortCourseAdminCommand extends Command{
    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LocalDate start_date = LocalDate.parse(request.getParameter( "start_date_filter"));
        LocalDate end_date = LocalDate.parse(request.getParameter("end_date_filter"));
        List<CoursesTableForAdminRow> sortedCourses = new ArrayList<>();
        try {
            for (CoursesTableForAdminRow row : JDBCCoursesTableForAdminRowDao.getSortedCourses(start_date, end_date)) {
                sortedCourses.add(row);
            }
        } catch (DBException e) {
            e.printStackTrace();
        }
        request.getSession().setAttribute("sortedCourses", sortedCourses);
        return new CommandResponse(CommandResponse.DispatchType.FORWARD, Path.ADMIN_ACCOUNT);
    }
}
