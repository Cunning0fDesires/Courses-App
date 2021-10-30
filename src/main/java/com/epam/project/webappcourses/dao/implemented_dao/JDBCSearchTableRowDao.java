package com.epam.project.webappcourses.dao.implemented_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.project.webappcourses.dao.ConnectionPool;
import com.epam.project.webappcourses.dao.SQLConstants;
import com.epam.project.webappcourses.dao.abstract_dao.SearchTableRowDao;
import com.epam.project.webappcourses.dao.mapper.SearchTableRowMapper;
import com.epam.project.webappcourses.entities.SearchTableRow;
import com.epam.project.webappcourses.exceptions.DBException;

public class JDBCSearchTableRowDao implements SearchTableRowDao {
    final static Logger logger = Logger.getLogger(JDBCUserDao.class);
    static SearchTableRowMapper mapper = new SearchTableRowMapper();

    public static LinkedList<SearchTableRow> getSortedCoursesByStudentsNumber (String lang) throws DBException {
        LinkedList<SearchTableRow> rows = new LinkedList<>();
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_COURSES_SORT_BY_STUDENTS_NUMBER)) {
            pstmt.setString(1, lang);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                rows.add(mapper.mapEntity(rs));
            }

        } catch (SQLException e) {
            logger.error("There was an error", e);
            System.out.println(e.getMessage());
            throw new DBException("Failed to get rows", e);
        }
        return rows;
    }

    public static LinkedList<SearchTableRow> getSortedCoursesByDuration (String lang) throws DBException {
        LinkedList<SearchTableRow> rows = new LinkedList<>();
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_COURSES_SORT_BY_DURATION)) {
            pstmt.setString(1, lang);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                rows.add(mapper.mapEntity(rs));
            }

        } catch (SQLException e) {
            logger.error("There was an error", e);
            System.out.println(e.getMessage());
            throw new DBException("Failed to get rows", e);
        }
        return rows;
    }

    public static LinkedList<SearchTableRow> getSortedCoursesByName (String lang) throws DBException {
        LinkedList<SearchTableRow> rows = new LinkedList<>();
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_COURSES_SORT_BY_NAME)) {
            pstmt.setString(1, lang);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                rows.add(mapper.mapEntity(rs));
            }
        } catch (SQLException e) {
            logger.error("There was an error", e);
            System.out.println(e.getMessage());
            throw new DBException("Failed to get rows", e);
        }
        return rows;
    }

    @Override
    public SearchTableRow getById(long id) throws DBException {
        return null;
    }

    @Override
    public List<SearchTableRow> getAll() throws DBException {
        List<SearchTableRow> rows = new ArrayList<>();
        try(Connection connection = ConnectionPool.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQLConstants.GET_ALL_USERS);) {
            while (rs.next())   {
                rows.add(mapper.mapEntity(rs));
            }

        } catch (SQLException e) {
            logger.error("There was an error", e);
            System.out.println(e.getMessage());
            throw new DBException("Failed to get rows", e);
        }
        return rows;
    }

    @Override
    public boolean add(SearchTableRow searchTableRow) throws DBException {
        return false;
    }

    @Override
    public boolean update(long id, String field, Object value) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteById(long id) throws DBException {
        return false;
    }
}
