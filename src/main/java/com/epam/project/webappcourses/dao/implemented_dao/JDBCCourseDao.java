package com.epam.project.webappcourses.dao.implemented_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.project.webappcourses.dao.ConnectionPool;
import com.epam.project.webappcourses.dao.SQLConstants;
import com.epam.project.webappcourses.dao.abstract_dao.CourseDao;
import com.epam.project.webappcourses.dao.mapper.CourseMapper;
import com.epam.project.webappcourses.entities.Course;
import com.epam.project.webappcourses.entities.CourseDescription;
import com.epam.project.webappcourses.exceptions.DBException;

public class JDBCCourseDao implements CourseDao {
    final static Logger logger = Logger.getLogger(JDBCCourseDao.class);
    static CourseMapper courseMapper = new CourseMapper();

    public static List<String> getPreviousTeachersByCourseId(int courseId) throws DBException {
        List <String> prevTeachers = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_PREVIOUS_TEACHERS)) {
            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                prevTeachers.add(rs.getString("teacher"));
            }
            return prevTeachers;
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entity", e);
        }
    }

    public static boolean changeTeacherForCourse (int teacherId, int courseId) throws DBException {
        boolean result = false;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.CHANGE_TEACHER_FOR_COURSE);) {
            pstmt.setInt(1, teacherId);
            pstmt.setInt(2, courseId);
            result = pstmt.executeUpdate() > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            logger.error("There was an error", e);
            throw new DBException("Couldn't change a teacher", e);
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Unexpected database error", e);
        }
        return result;
    }

    public static List<HashMap> getAllCoursesWithTeacher() throws DBException {
        List<HashMap> courses = new ArrayList<>();
        try(Connection connection = ConnectionPool.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQLConstants.GET_ALL_COURSES_WITH_TEACHERS)) {
            while (rs.next())   {
                HashMap<Integer, Integer> courseTeacher = new HashMap<>();
                courseTeacher.put(rs.getInt("course_id"), rs.getInt("teacher_id"));
                courses.add(courseTeacher);
            }
        } catch (SQLException e) {
            logger.error("There was an error", e);
            System.out.println(e.getMessage());
            throw new DBException("Failed to get courses", e);
        }
        return courses;
    }

    public static List<String> getAllTopics() throws DBException {
        List<String> topics = new ArrayList<>();
        try(Connection connection = ConnectionPool.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQLConstants.GET_ALL_TOPICS)) {
            while (rs.next())   {
                topics.add(rs.getString("topic_name"));
            }
        } catch (SQLException e) {
            logger.error("There was an error", e);
            System.out.println(e.getMessage());
            throw new DBException("Failed to get topics", e);
        }
        return topics;
    }

    public static int getTopicIdByName(String name) throws DBException {
        int number = 0;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_TOPIC_ID_BY_NAME)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                number = rs.getInt("topic_id");
            }
            return number;
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entity", e);
        }
    }


    public static boolean addNewTopic(String name) throws DBException {
        boolean result = false;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.ADD_NEW_TOPIC);) {
            pstmt.setString(1, name);
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

    public static boolean attachCourseToTeacher (int teacherId, int courseId) throws DBException {
        boolean result = false;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.ATTACH_COURSE_TO_TEACHER);) {
            pstmt.setInt(1, teacherId);
            pstmt.setInt(2, courseId);
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

    public static Course getByName(String name) throws DBException {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_COURSE_BY_NAME)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                courses.add(courseMapper.mapEntity(rs));
            }
            return courses.get(0);
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entity", e);
        }
    }

    public static String getDurationByCourseId (long id) throws DBException {
        String duration = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_COURSE_DURATION_IN_WEEKS)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
              duration = rs.getInt(1) + " weeks";
            }
            return duration;
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entity", e);
        }
    }

    public static List<Course> getFinishedCourses(long id) throws DBException {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_FINISHED_COURSES_BY_STUDENT_ID)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                JDBCCourseDao dao = new JDBCCourseDao();
                courses.add(dao.getById(rs.getLong("course_id")));
            }
            return courses;
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entity", e);
        }
    }

    public static List<Course> getCoursesInProgress(long id) throws DBException {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_COURSES_IN_PROGRESS_BY_STUDENT_ID)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                JDBCCourseDao dao = new JDBCCourseDao();
                courses.add(dao.getById(rs.getLong("course_id")));
            }
            return courses;
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entity", e);
        }
    }

    public static List<Course> getNotStartedCoursesByStudentId(long id) throws DBException {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_COURSES_TO_BE_STARTED_BY_STUDENT_ID)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                JDBCCourseDao dao = new JDBCCourseDao();
                courses.add(dao.getById(rs.getLong("course_id")));
            }
            return courses;
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entity", e);
        }
    }

    public static List<Long> getCoursesIdsByTeacherId(long id) throws DBException {
        List<Long> coursesIds = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_COURSES_BY_TEACHER_ID)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                coursesIds.add(rs.getLong(1));
            }
            return coursesIds;
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entity", e);
        }
    }

    public static CourseDescription getCourseDescription(long id, String locale) throws DBException {
        CourseDescription courseDescription = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_COURSE_DESCRIPTION)) {
            pstmt.setLong(1, id);
            pstmt.setString(2, locale);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                courseDescription = new CourseDescription();
                courseDescription.setName(rs.getString(1));
            }
            return courseDescription;
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entity", e);
        }
    }

        @Override
    public Course getById(long id) throws DBException {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_COURSE_BY_ID)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                courses.add(courseMapper.mapEntity(rs));
            }
            return courses.get(0);
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entity", e);
        }
    }

    public static int getMaxCourseDescriptionId() throws DBException {
        int maxCourseDescriptionId = 0;
        try(Connection connection = ConnectionPool.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQLConstants.GET_MAX_COURSE_DESCRIPTION_ID)) {
            while (rs.next())   {
                maxCourseDescriptionId = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("There was an error", e);
            System.out.println(e.getMessage());
            throw new DBException("Failed to get topics", e);
        }
        return maxCourseDescriptionId;
    }

    @Override
    public List<Course> getAll() throws DBException {
        List<Course> courses = new ArrayList<>();
        try(Connection connection = ConnectionPool.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQLConstants.GET_ALL_COURSES);) {
            while (rs.next())   {
                courses.add(courseMapper.mapEntity(rs));
            }
        } catch (SQLException e) {
            logger.error("There was an error", e);
            System.out.println(e.getMessage());
            throw new DBException("Failed to get courses", e);
        }
        return courses;
    }

    @Override
    public boolean add(Course course) throws DBException {
        boolean result = false;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.ADD_NEW_COURSE);) {
            pstmt.setObject(1, course.getEndDate());
            pstmt.setString(2, course.getName());
            pstmt.setObject(3, course.getStartDate());
            pstmt.setInt(4, course.getTopicId());
            pstmt.setInt(5, course.getCourseDescription().getCourseDescriptionId());
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
        return false;
    }

    @Override
    public boolean deleteById(long id) throws DBException {
        return false;
    }
}
