package com.epam.project.webappcourses.web.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.project.webappcourses.dao.implemented_dao.JDBCStudentsStatusTableRowDao;
import com.epam.project.webappcourses.dao.implemented_dao.JDBCUserDao;
import com.epam.project.webappcourses.entities.StudentStatusTableRow;
import com.epam.project.webappcourses.entities.User;
import com.epam.project.webappcourses.exceptions.DBException;
import com.epam.project.webappcourses.web.Path;

public class ChangeStatusCommand extends Command {
    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = new User();
        int statusId = 0;
        JDBCUserDao userDao = new JDBCUserDao();
        List<StudentStatusTableRow> allStudentsWithStatus = new ArrayList<>();

        try {
            user = userDao.getById(Long.parseLong(request.getParameter("id")));
            statusId = JDBCUserDao.getStatusIdByName(request.getParameter("status"));
        } catch (DBException e) {
            e.printStackTrace();
        }

        userDao.updateBlockedStatus(user, statusId);

        try {
            allStudentsWithStatus = JDBCStudentsStatusTableRowDao.getAll();
        } catch (DBException e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("allStudentsWithStatus", allStudentsWithStatus);

        return new CommandResponse(CommandResponse.DispatchType.FORWARD, Path.ADMIN_ACCOUNT);
    }
}
