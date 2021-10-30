package com.epam.project.webappcourses.dao.mapper;

import com.epam.project.webappcourses.entities.SearchTableRow;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchTableRowMapper implements EntityMapper<SearchTableRow>{
    @Override
    public SearchTableRow mapEntity(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        String topic = resultSet.getString("topic");
        String teacher = resultSet.getString("teacher");
        int duration = resultSet.getInt("duration");
        int studentsRegistered = resultSet.getInt("students_registered");

        SearchTableRow row = new SearchTableRow();
        row.setName(name);
        row.setTopic(topic);
        row.setTeacher(teacher);
        row.setDuration(duration);
        row.setStudentsRegistered(studentsRegistered);

        return row;
    }
}
