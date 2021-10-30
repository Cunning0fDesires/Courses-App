package com.epam.project.webappcourses.dao.mapper;


import com.epam.project.webappcourses.entities.CoursesTableForAdminRow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CoursesTableForAdminRowMapper implements EntityMapper<CoursesTableForAdminRow>{
    @Override
    public CoursesTableForAdminRow mapEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("course_id");
        String name =  resultSet.getString("name");
        String topic =  resultSet.getString("topic_name");
        LocalDate startDate = resultSet.getObject("start_date", LocalDate.class);
        LocalDate endDate = resultSet.getObject("end_date", LocalDate.class);

        CoursesTableForAdminRow row = new CoursesTableForAdminRow();
        row.setId(id);
        row.setName(name);
        row.setTopic(topic);
        row.setStartDate(startDate);
        row.setEndDate(endDate);

        return row;
    }
}
