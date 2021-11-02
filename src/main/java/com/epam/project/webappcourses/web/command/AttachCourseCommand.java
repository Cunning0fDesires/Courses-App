package com.epam.project.webappcourses.web.command;

import com.epam.project.webappcourses.dao.implemented_dao.JDBCCourseDao;
import com.epam.project.webappcourses.dao.implemented_dao.JDBCJournalDao;
import com.epam.project.webappcourses.dao.implemented_dao.JDBCTeacherCourseTableRowDao;
import com.epam.project.webappcourses.entities.TeacherCourseTableRow;
import com.epam.project.webappcourses.exceptions.DBException;
import com.epam.project.webappcourses.web.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AttachCourseCommand extends Command {
    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int teacherId = Integer.parseInt(request.getParameter("teacher"));
        int courseId = Integer.parseInt(request.getParameter("course"));
        List<HashMap> existingCoursesWithTeacher = new ArrayList<>();
        boolean resultAttaching = false;
        boolean resultChanging = false;
        List<TeacherCourseTableRow> allTeachersWithCoursesList = new ArrayList<>();


        try {
            existingCoursesWithTeacher = JDBCCourseDao.getAllCoursesWithTeacher();
        } catch (DBException e) {
            e.printStackTrace();
        }
        HashMap<Integer, Integer> currentPair = new HashMap<>();
        currentPair.put(courseId, teacherId);

            try {
                resultAttaching = JDBCCourseDao.attachCourseToTeacher(teacherId, courseId);
                if (resultAttaching = true) {
                    allTeachersWithCoursesList = JDBCTeacherCourseTableRowDao.getAllTeachersWithCoursesList();
                    request.getSession().setAttribute("allTeachersWithCoursesList", allTeachersWithCoursesList);
                    return new CommandResponse(CommandResponse.DispatchType.FORWARD,  Path.ADMIN_ACCOUNT);
                }
            } catch (DBException e) {
                       try {
                           resultChanging = JDBCCourseDao.changeTeacherForCourse(teacherId, courseId);
                       } catch (DBException ex) {
                           ex.printStackTrace();
                       }
                       if (resultChanging = true) {
                           try {
                               allTeachersWithCoursesList = JDBCTeacherCourseTableRowDao.getAllTeachersWithCoursesList();
                           } catch (DBException ex) {
                               ex.printStackTrace();
                           }
                           request.getSession().setAttribute("allTeachersWithCoursesList", allTeachersWithCoursesList);
                        return new CommandResponse(CommandResponse.DispatchType.FORWARD,  Path.ADMIN_ACCOUNT);
                    }
                }
            return new CommandResponse(CommandResponse.DispatchType.FORWARD,  Path.ADMIN_ACCOUNT);
        }
    }

