package com.epam.project.webappcourses.dao.implemented_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.project.webappcourses.dao.ConnectionPool;
import com.epam.project.webappcourses.dao.SQLConstants;
import com.epam.project.webappcourses.dao.abstract_dao.UserDao;
import com.epam.project.webappcourses.dao.mapper.UserMapper;
import com.epam.project.webappcourses.entities.User;
import com.epam.project.webappcourses.exceptions.DBException;


public class JDBCUserDao implements UserDao {
    final static Logger logger = Logger.getLogger(JDBCUserDao.class);
    static UserMapper mapper = new UserMapper();

    public static int getStatusIdByName(String name) throws DBException {
        int number = 0;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_STATUS_ID_BY_NAME)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                number = rs.getInt("status_id");
            }
            return number;
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entity", e);
        }
    }

    public static boolean addNewTeacher(User user) throws DBException {
        boolean result = false;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.ADD_NEW_TEACHER);) {
            pstmt.setString(1, user.getLogin());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getSurname());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPassword());
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

    public static List<User> getAllTeachers() throws DBException {
        List<User> users = new ArrayList<>();
        try(Connection connection = ConnectionPool.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQLConstants.GET_ALL_TEACHERS);) {
            while (rs.next())   {
                users.add(mapper.mapEntity(rs));
            }

        } catch (SQLException e) {
            logger.error("There was an error", e);
            System.out.println(e.getMessage());
            throw new DBException("Failed to get users", e);
        }
        return users;
    }

    public static User getUserByLogin(String login) throws DBException {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_USER_BY_LOGIN)) {
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                users.add(mapper.mapEntity(rs));
            }
            return users.get(0);
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find user", e);
        }
    }

    private void close(AutoCloseable object) {
        if (object != null) {
            try {
                object.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public List<User> getUsersByRole(int role) throws DBException {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_USER_BY_ROLE)) {
            pstmt.setInt(1, role);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                users.add(mapper.mapEntity(rs));
            }
            return users;
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entities", e);
        }
    }

    @Override
    public boolean updateBlockedStatus(User user, int status) {
        String statusField = "status";
        boolean result = false;
        result = update(user.getId(), statusField, status);
        return result;
    }

    @Override
    public User getById(long id) throws DBException {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_USER_BY_ID)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())   {
                users.add(mapper.mapEntity(rs));
            }
            return users.get(0);
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entity", e);
        }
    }

    @Override
    public List<User> getAll() throws DBException {
        List<User> users = new ArrayList<>();
        try(Connection connection = ConnectionPool.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQLConstants.GET_ALL_USERS);) {
            while (rs.next())   {
                users.add(mapper.mapEntity(rs));
            }

        } catch (SQLException e) {
            logger.error("There was an error", e);
            System.out.println(e.getMessage());
            throw new DBException("Failed to get users", e);
        }
        return users;
    }

    @Override
    public boolean add(User user) throws DBException {
        boolean result = false;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLConstants.ADD_NEW_USER);) {
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getSurname());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(2, user.getLogin());
            pstmt.setString(6, user.getPassword());
            pstmt.setInt(1, user.getRole());
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
    public boolean update(long id, String field, Object value)  {
        boolean result = false;
        String sqlQuery = "UPDATE " + SQLConstants.TABLE_USER + " SET " + field + " = ? WHERE user_id = ?;";
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
        boolean result = false;
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = ConnectionPool.getConnection();
            pstmt = connection.prepareStatement(SQLConstants.DELETE_USER_BY_ID);
            int index = 1;
            pstmt.setLong(index, id);
            result = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("There was an error", e);
            e.printStackTrace();
            throw new DBException("Failed to delete a user with id = " + id, e);
        } finally {
            close(connection);
        }
        return result;
    }
}
