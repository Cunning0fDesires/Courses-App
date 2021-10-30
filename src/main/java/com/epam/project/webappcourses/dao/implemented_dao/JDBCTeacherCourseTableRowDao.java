package com.epam.project.webappcourses.dao.implemented_dao;

import com.epam.project.webappcourses.dao.ConnectionPool;
import com.epam.project.webappcourses.dao.SQLConstants;
import com.epam.project.webappcourses.dao.mapper.TeacherCourseTableRowMapper;
import com.epam.project.webappcourses.entities.TeacherCourseTableRow;
import com.epam.project.webappcourses.exceptions.DBException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCTeacherCourseTableRowDao {
    static TeacherCourseTableRowMapper mapper = new TeacherCourseTableRowMapper();
    final static Logger logger = Logger.getLogger(JDBCTeacherCourseTableRowDao.class);

    public static List<TeacherCourseTableRow> getAllTeachersWithCoursesList() throws DBException {
        List<TeacherCourseTableRow> journalRecords = new ArrayList<>();
        try(Connection connection = ConnectionPool.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQLConstants.GET_ALL_TEACHERS_WITH_COURSES);) {
            while (rs.next())   {
                journalRecords.add(mapper.mapEntity(rs));
            }

        } catch (SQLException e) {
            logger.error("There was an error", e);
            System.out.println(e.getMessage());
            throw new DBException("Failed to get entities", e);
        }
        return journalRecords;
    }
}
