package com.epam.project.webappcourses.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.epam.project.webappcourses.entities.Course;

public class CourseMapper implements EntityMapper<Course>{
    @Override
    public Course mapEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("course_id");
        String name= resultSet.getString("name");
        int topicId = resultSet.getInt("topic_id");
        LocalDate startDate = resultSet.getObject("start_date", LocalDate.class);
        LocalDate endDate = resultSet.getObject("end_date", LocalDate.class);

        Course course = new Course();
        course.setName(name);
        course.setId(id);
        course.setTopicId(topicId);
        course.setStartDate(startDate);
        course.setEndDate(endDate);

        return course;
    }
}
