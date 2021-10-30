package com.epam.project.webappcourses.dao.abstract_dao;

import java.util.List;
import com.epam.project.webappcourses.entities.User;
import com.epam.project.webappcourses.exceptions.DBException;

public interface UserDao extends AbstractDao<User> {

    List<User> getUsersByRole(int role) throws DBException;

    boolean updateBlockedStatus(User user, int status) throws DBException;
}
