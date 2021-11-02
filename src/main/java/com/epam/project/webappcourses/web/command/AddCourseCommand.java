package com.epam.project.webappcourses.web.command;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.project.webappcourses.dao.implemented_dao.JDBCCourseDao;
import com.epam.project.webappcourses.entities.Course;
import com.epam.project.webappcourses.entities.CourseDescription;
import com.epam.project.webappcourses.exceptions.DBException;
import com.epam.project.webappcourses.web.Path;

public class AddCourseCommand extends Command {
    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("course_name");
        String topic = request.getParameter("topic");
        int topicId = 0;

        LocalDate startDate = LocalDate.parse(request.getParameter("start_date"));
        LocalDate endDate = LocalDate.parse(request.getParameter("end_date"));

        Course courseToBeAdded = new Course();
        boolean addition = false;

        List<String> allCoursesNames = new ArrayList<>();
        JDBCCourseDao courseDao = new JDBCCourseDao();

        try {
            for (Course course : courseDao.getAll()) {
                allCoursesNames.add(course.getName());
            }
        } catch (DBException e) {
            e.printStackTrace();
        }
        if (allCoursesNames.contains(name)) {
            return new CommandResponse(CommandResponse.DispatchType.FORWARD, Path.PAGE__ERROR_PAGE);
        }
        List<String> allTopics = new ArrayList<>();
        try {
            for (String entity : JDBCCourseDao.getAllTopics()) {
                allTopics.add(entity);
            }
        } catch (DBException e) {
            e.printStackTrace();
        }
        if (allTopics.contains(topic)){
            try {
                topicId = JDBCCourseDao.getTopicIdByName(request.getParameter("topic"));
            } catch (DBException e) {
                e.printStackTrace();
            }
        } else {
            try {
                JDBCCourseDao.addNewTopic(request.getParameter("topic"));
                topicId = JDBCCourseDao.getTopicIdByName(request.getParameter("topic"));

            } catch (DBException e) {
                e.printStackTrace();
            }
        }

        try {
            courseToBeAdded.setCourseDescription(new CourseDescription());
            courseToBeAdded.getCourseDescription().setCourseDescriptionId(JDBCCourseDao.getMaxCourseDescriptionId() + 1);
        } catch (DBException e) {
            e.printStackTrace();
        }
        courseToBeAdded.setName(name);
        courseToBeAdded.setTopicId(topicId);
        courseToBeAdded.setStartDate(startDate);
        courseToBeAdded.setEndDate(endDate);

        try {
            addition = courseDao.add(courseToBeAdded);
        } catch (DBException e) {
            e.printStackTrace();
        }

        if (addition == true ) {
            return new CommandResponse(CommandResponse.DispatchType.FORWARD, Path.PAGE__SUCCESS);
        }
        return new CommandResponse(CommandResponse.DispatchType.FORWARD, Path.PAGE__ERROR_PAGE);
    }
}
