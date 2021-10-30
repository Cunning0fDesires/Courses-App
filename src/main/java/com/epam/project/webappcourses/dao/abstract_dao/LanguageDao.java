package com.epam.project.webappcourses.dao.abstract_dao;

import java.util.List;

import com.epam.project.webappcourses.entities.Language;
import com.epam.project.webappcourses.exceptions.DBException;

public interface LanguageDao {

    List<Language> findAllLanguages() throws DBException;
}
