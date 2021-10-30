package com.epam.project.webappcourses.dao.implemented_dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.project.webappcourses.dao.ConnectionPool;
import com.epam.project.webappcourses.dao.SQLConstants;
import com.epam.project.webappcourses.dao.abstract_dao.JournalDao;
import com.epam.project.webappcourses.dao.mapper.JournalMapper;
import com.epam.project.webappcourses.entities.Journal;
import com.epam.project.webappcourses.exceptions.DBException;

public class JDBCJournalDao implements JournalDao {
    final static Logger logger = Logger.getLogger(JDBCJournalDao.class);
    static JournalMapper journalMapper = new JournalMapper();
    JDBCUserDao userDao = new JDBCUserDao();
    JDBCCourseDao courseDao = new JDBCCourseDao();

    public static int getCourseTeacherIdByCourseId (long id) throws DBException {
        int returnId = 0;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_JOURNAL_RECORD_ID_BY_COURSE_ID)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                returnId = rs.getInt("id");
            }
            return returnId;
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entity", e);
        }
    }


    public static String getTeacherNameByCourseId(long id) throws DBException {
        String name = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_TEACHER_BY_COURSE_ID)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                name = rs.getString("name") + " " + rs.getString("surname");
            }
            return name;
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entity", e);
        }
    }

    public static Object getGradeByStudentAndCourse (long studentId, long courseId) throws DBException {
        Object grade = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_GRADE_BY_STUDENT_AND_COURSE)) {
            pstmt.setLong(1, courseId);
            pstmt.setLong(2, studentId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                if (rs.getInt("grade") == 0) {
                    grade = " ";
                } else {
                grade = rs.getInt("grade");
                }
            }
            return grade;
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entity", e);
        }
    }

    public static List<Journal> getAllRecordsByStudentId(long id) throws DBException {
        List<Journal> journalRecords = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_RECORDS_BY_STUDENT_ID)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                journalRecords.add(journalMapper.mapEntity(rs));
            }
            return journalRecords;
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entity", e);
        }
    }

    public static List<Journal> getAllRecordsByTeacherIdAndCourseId(long teacherId, long courseId) throws DBException {
        List<Journal> journalRecords = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_RECORDS_BY_TEACHER_ID_AND_COURSE_ID)) {
            pstmt.setLong(1, teacherId);
            pstmt.setLong(2, courseId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                journalRecords.add(journalMapper.mapEntity(rs));
            }
            return journalRecords;
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entity", e);
        }
    }

    public static int getStudentsRegisteredNumberByCourseId (long id) throws DBException {
        int number = 0;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_STUDENTS_NUMBER_REGISTERED_TO_COURSE_BY_ID)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
               number = rs.getInt(1);
            }
            return number;
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entity", e);
        }
    }

        public LinkedList<Object> getGradesByStudentId(long id) throws DBException {
        LinkedList<Object> grades = new LinkedList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_GRADE_BY_STUDENT_ID)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                if (rs.getInt("grade") == 0) {
                    grades.add(" ");
                } else {
                    grades.add(rs.getInt("grade"));
                }
            }
            return grades;
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entity", e);
        }
    }


    @Override
    public Journal getById(long id) throws DBException {
        List<Journal> journalRecord = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_RECORDS_BY_STUDENT_ID)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                journalRecord.add(journalMapper.mapEntity(rs));
            }
            return journalRecord.get(0);
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entity", e);
        }
    }

    @Override
    public List<Journal> getAll() throws DBException {
        List<Journal> journalRecords = new ArrayList<>();
        try(Connection connection = ConnectionPool.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQLConstants.GET_ALL_JOURNAL_RECORDS);) {
            while (rs.next())   {
                journalRecords.add(journalMapper.mapEntity(rs));
            }

        } catch (SQLException e) {
            logger.error("There was an error", e);
            System.out.println(e.getMessage());
            throw new DBException("Failed to get journal records", e);
        }
        return journalRecords;
    }

    @Override
    public boolean add(Journal journal) throws DBException {
        boolean result = false;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.ADD_NEW_JOURNAL_RECORD);) {
            pstmt.setInt(1, journal.getStudentId());
            pstmt.setInt(2, journal.getCourse());
            result = pstmt.executeUpdate() > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            logger.error("There was an error", e);
            throw new DBException("Entity already exists", e);
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Unexpected database error", e);
        }
        return result;
    }

    @Override
    public boolean update(long id, String field, Object value) throws SQLException {
        boolean result = false;
        String sqlQuery = "UPDATE " + SQLConstants.TABLE_JOURNAL + " SET " + field + " = ? WHERE id_record = ?;";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlQuery);) {
            ResultSet rs = null;
            ps.setObject(1, value);
            ps.setLong(2, id);
            result = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean deleteById(long id) throws DBException {
        return false;
    }
}
