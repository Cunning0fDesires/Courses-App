package com.epam.project.webappcourses.dao.mapper;


import com.epam.project.webappcourses.entities.StudentStatusTableRow;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentStatusTableRowMapper  implements EntityMapper<StudentStatusTableRow>{
    @Override
    public StudentStatusTableRow mapEntity(ResultSet resultSet) throws SQLException {
        String student =  resultSet.getString("student");
        String status =  resultSet.getString("status");
        int id = resultSet.getInt("user_id");

        StudentStatusTableRow row = new StudentStatusTableRow();
        row.setStudent(student);
        row.setStatus(status);
        row.setId(id);

        return row;
    }
}
