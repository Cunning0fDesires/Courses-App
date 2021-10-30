package com.epam.project.webappcourses.dao.abstract_dao;

import com.epam.project.webappcourses.exceptions.DBException;

import java.sql.SQLException;
import java.util.List;

public interface AbstractDao<T> {
    T getById (long id) throws DBException;

    List<T> getAll() throws DBException;

    boolean add(T t) throws DBException;

    boolean update(long id, String field, Object value) throws SQLException;

    boolean deleteById(long id) throws DBException;
}
