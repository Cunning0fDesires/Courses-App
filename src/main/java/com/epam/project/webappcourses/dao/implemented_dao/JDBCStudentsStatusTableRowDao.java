package com.epam.project.webappcourses.dao.implemented_dao;

import com.epam.project.webappcourses.dao.ConnectionPool;
import com.epam.project.webappcourses.dao.SQLConstants;
import com.epam.project.webappcourses.dao.mapper.CoursesTableForAdminRowMapper;
import com.epam.project.webappcourses.dao.mapper.StudentStatusTableRowMapper;
import com.epam.project.webappcourses.entities.CoursesTableForAdminRow;
import com.epam.project.webappcourses.entities.StudentStatusTableRow;
import com.epam.project.webappcourses.exceptions.DBException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCStudentsStatusTableRowDao {
    final static Logger logger = Logger.getLogger(JDBCUserDao.class);
    static StudentStatusTableRowMapper mapper = new StudentStatusTableRowMapper();

    public static List<StudentStatusTableRow> getAll() throws DBException {
        List<StudentStatusTableRow> students = new ArrayList<>();
        try(Connection connection = ConnectionPool.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQLConstants.GET_STUDENTS_STATUS_LIST);) {
            while (rs.next())   {
                students.add(mapper.mapEntity(rs));
            }

        } catch (SQLException e) {
            logger.error("There was an error", e);
            System.out.println(e.getMessage());
            throw new DBException("Failed to get students", e);
        }
        return students;
    }
}
