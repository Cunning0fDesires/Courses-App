package com.epam.project.webappcourses.dao.abstract_dao;

import com.epam.project.webappcourses.dao.abstract_dao.UserDao;

public interface DaoFactory {

    UserDao createUserDao();

    CourseDao createCourseDao();

    TopicDao createThemeDao();

}
