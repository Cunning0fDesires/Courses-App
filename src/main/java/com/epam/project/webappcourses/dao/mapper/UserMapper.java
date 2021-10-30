package com.epam.project.webappcourses.dao.mapper;

import com.epam.project.webappcourses.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements EntityMapper<User>{

    @Override
    public User mapEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("user_id");
        String firstName = resultSet.getString("name");
        String lastName = resultSet.getString("surname");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        int role = resultSet.getInt("role_id");
        int status = resultSet.getInt("status");

        User user = new User();
        user.setId(id);
        user.setName(firstName);
        user.setSurname(lastName);
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(role);
        user.setStatus(status);
        return user;
    }


    public User extractStudentData(ResultSet resultSet) throws SQLException {
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        int studId = resultSet.getInt("stud_id");
        User user = new User();
        user.setName(firstName);
        user.setSurname(lastName);
        user.setId(studId);
        return user;
    }
}
