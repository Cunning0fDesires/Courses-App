package com.epam.project.webappcourses.web.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.project.webappcourses.dao.implemented_dao.JDBCCourseDao;
import com.epam.project.webappcourses.dao.implemented_dao.JDBCTeacherCourseTableRowDao;
import com.epam.project.webappcourses.dao.implemented_dao.JDBCUserDao;
import com.epam.project.webappcourses.entities.Course;
import com.epam.project.webappcourses.entities.TeacherCourseTableRow;
import com.epam.project.webappcourses.entities.User;
import com.epam.project.webappcourses.exceptions.DBException;
import com.epam.project.webappcourses.web.Path;

public class AddTeacherCommand extends Command {
    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        List<TeacherCourseTableRow> allTeachersWithCoursesList = new ArrayList<>();


        User teacher = new User();
        teacher.setName(name);
        teacher.setSurname(surname);
        teacher.setLogin(login);
        teacher.setPassword(password);
        teacher.setEmail(email);

        boolean addingTeacher = false;

        List<String> existingLogins = new ArrayList<>();

        try {
            for (User user : JDBCUserDao.getAllTeachers()) {
                existingLogins.add(user.getLogin());
            }
            allTeachersWithCoursesList = JDBCTeacherCourseTableRowDao.getAllTeachersWithCoursesList();

        } catch (DBException e) {
            e.printStackTrace();
        }
        if (existingLogins.contains(login)) {
            return new CommandResponse(CommandResponse.DispatchType.FORWARD, Path.PAGE__ERROR_PAGE);
        }
        try {
            addingTeacher = JDBCUserDao.addNewTeacher(teacher);
        } catch (DBException e) {
            e.printStackTrace();
        }
        HttpSession session = request.getSession();
        session.setAttribute("allTeachersWithCoursesList", allTeachersWithCoursesList);

        if (addingTeacher == true) {
            return new CommandResponse(CommandResponse.DispatchType.FORWARD, Path.ADMIN_ACCOUNT);
        }
       return new CommandResponse(CommandResponse.DispatchType.FORWARD, Path.ADD_TEACHER);
    }
}
