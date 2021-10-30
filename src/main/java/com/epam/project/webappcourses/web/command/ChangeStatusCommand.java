package com.epam.project.webappcourses.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.project.webappcourses.dao.implemented_dao.JDBCUserDao;
import com.epam.project.webappcourses.entities.User;
import com.epam.project.webappcourses.exceptions.DBException;
import com.epam.project.webappcourses.web.Path;

public class ChangeStatusCommand extends Command {
    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = new User();
        int statusId = 0;
        JDBCUserDao userDao = new JDBCUserDao();
        try {
            user = userDao.getById(Long.parseLong(request.getParameter("id")));
            statusId = JDBCUserDao.getStatusIdByName(request.getParameter("status"));
        } catch (DBException e) {
            e.printStackTrace();
        }
        if (user.getStatus() == statusId) {
            return new CommandResponse(CommandResponse.DispatchType.FORWARD, Path.PAGE__ERROR_PAGE);
        }
        boolean result = false;

        result = userDao.updateBlockedStatus(user, statusId);
        if (result == true){
        return new CommandResponse(CommandResponse.DispatchType.FORWARD, Path.PAGE__SUCCESS);
        }
        return new CommandResponse(CommandResponse.DispatchType.FORWARD, Path.PAGE__ERROR_PAGE);
    }
}
