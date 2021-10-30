package com.epam.project.webappcourses.web.command;

import com.epam.project.webappcourses.dao.implemented_dao.JDBCCourseDao;
import com.epam.project.webappcourses.dao.implemented_dao.JDBCJournalDao;
import com.epam.project.webappcourses.entities.Course;
import com.epam.project.webappcourses.entities.Journal;
import com.epam.project.webappcourses.entities.User;
import com.epam.project.webappcourses.exceptions.DBException;
import com.epam.project.webappcourses.web.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CourseRegisterCommand extends Command {
    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user.getStatus() == 2) {
            return new CommandResponse(CommandResponse.DispatchType.FORWARD, Path.PAGE__STATUS_BLOCKED);
        }
        int userId = user.getId();
        JDBCCourseDao courseDao = new JDBCCourseDao();
        List<Integer> idsOfCourseRecords = new ArrayList<>();
        List<Journal> allRecordsOfStudent = new ArrayList<>();
        int courseTeacherId = 0;
        try {
            List<Course> courses = courseDao.getAll();
            Course requestCourse = null;
            for (Course course : courses) {
                if (course.getName().equals(request.getParameter("course"))) {
                    requestCourse = course;
                }
            }
            allRecordsOfStudent = JDBCJournalDao.getAllRecordsByStudentId(userId);
            courseTeacherId = JDBCJournalDao.getCourseTeacherIdByCourseId(requestCourse.getId());
            for (Journal record : allRecordsOfStudent) {
                idsOfCourseRecords.add(record.getCourse());
            }
        } catch (DBException e) {
            e.printStackTrace();
        }

        if (idsOfCourseRecords.contains(courseTeacherId)) {
            return new CommandResponse(CommandResponse.DispatchType.FORWARD, Path.PAGE__ERROR_REGISTERING_TO_COURSE);
        } else {
            JDBCJournalDao journalDao = new JDBCJournalDao();
            Journal newRecord = new Journal();
            newRecord.setStudentId(user.getId());
            newRecord.setCourse(courseTeacherId);
            try {
                journalDao.add(newRecord);
            } catch (DBException e) {
                e.printStackTrace();
            }
            return new CommandResponse(CommandResponse.DispatchType.FORWARD, Path.PAGE__SUCCESS);
        }
    }
}
