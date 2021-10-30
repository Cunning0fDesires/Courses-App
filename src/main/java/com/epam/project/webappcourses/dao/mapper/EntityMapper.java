package com.epam.project.webappcourses.dao.mapper;

import com.epam.project.webappcourses.entities.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityMapper <T extends Entity>  {
    public T mapEntity(ResultSet resultSet) throws SQLException;
}
