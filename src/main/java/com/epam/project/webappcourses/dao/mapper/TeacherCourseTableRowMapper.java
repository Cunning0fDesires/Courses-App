package com.epam.project.webappcourses.dao.mapper;


import com.epam.project.webappcourses.entities.TeacherCourseTableRow;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherCourseTableRowMapper implements EntityMapper<TeacherCourseTableRow>{
    @Override
    public TeacherCourseTableRow mapEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("user_id");
        String teacher = resultSet.getString("teacher");
        String course = resultSet.getString("course");

        TeacherCourseTableRow row = new TeacherCourseTableRow();
        row.setId(id);
        row.setTeacher(teacher);
        row.setCourse(course);
        return row;
    }
}
