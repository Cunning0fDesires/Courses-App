package com.epam.project.webappcourses.dao.implemented_dao;

import com.epam.project.webappcourses.dao.ConnectionPool;
import com.epam.project.webappcourses.dao.SQLConstants;
import com.epam.project.webappcourses.dao.mapper.CoursesTableForAdminRowMapper;
import com.epam.project.webappcourses.dao.mapper.UserMapper;
import com.epam.project.webappcourses.entities.CoursesTableForAdminRow;
import com.epam.project.webappcourses.entities.User;
import com.epam.project.webappcourses.exceptions.DBException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCCoursesTableForAdminRowDao {
    final static Logger logger = Logger.getLogger(JDBCUserDao.class);
    static CoursesTableForAdminRowMapper mapper = new CoursesTableForAdminRowMapper();

    public static List<CoursesTableForAdminRow> getSortedCourses(LocalDate start, LocalDate end) throws DBException {
        List<CoursesTableForAdminRow> courses = new ArrayList<>();
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(SQLConstants.GET_COURSES_SORTED_FOR_ADMIN);
            ) {
            stmt.setDate(1, Date.valueOf(start));
            stmt.setDate(2, Date.valueOf(end));
            ResultSet rs = stmt.executeQuery();
            while (rs.next())   {
                courses.add(mapper.mapEntity(rs));
            }

        } catch (SQLException e) {
            logger.error("There was an error", e);
            System.out.println(e.getMessage());
            throw new DBException("Failed to get courses", e);
        }
        return courses;
    }

    public static List<CoursesTableForAdminRow> getAll() throws DBException {
        List<CoursesTableForAdminRow> courses = new ArrayList<>();
        try(Connection connection = ConnectionPool.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQLConstants.GET_COURSES_INFO_FOR_ADMIN);) {
            while (rs.next())   {
                courses.add(mapper.mapEntity(rs));
            }

        } catch (SQLException e) {
            logger.error("There was an error", e);
            System.out.println(e.getMessage());
            throw new DBException("Failed to get courses", e);
        }
        return courses;
    }
}
