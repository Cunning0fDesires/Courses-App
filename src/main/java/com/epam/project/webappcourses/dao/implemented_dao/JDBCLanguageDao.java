package com.epam.project.webappcourses.dao.implemented_dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.project.webappcourses.dao.ConnectionPool;
import com.epam.project.webappcourses.dao.SQLConstants;
import com.epam.project.webappcourses.dao.abstract_dao.LanguageDao;
import com.epam.project.webappcourses.dao.mapper.LanguageMapper;
import com.epam.project.webappcourses.entities.Language;
import com.epam.project.webappcourses.exceptions.DBException;

public class JDBCLanguageDao implements LanguageDao {
    final static Logger logger = Logger.getLogger(JDBCLanguageDao.class);
    LanguageMapper languageMapper = new LanguageMapper();

    @Override
    public List<Language> findAllLanguages() throws DBException {
        List<Language> languages = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SQLConstants.GET_ALL_LANGUAGES)) {
            while (rs.next())   {
                languages.add(languageMapper.mapEntity(rs));
            }
            return languages;
        } catch (SQLException e) {
            logger.error("There was an error", e);
            throw new DBException("Failed to find entity", e);
        }
    }
}
