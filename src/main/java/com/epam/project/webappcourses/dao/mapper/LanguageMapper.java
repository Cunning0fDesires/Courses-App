package com.epam.project.webappcourses.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.project.webappcourses.entities.Language;

public class LanguageMapper implements EntityMapper<Language> {
    @Override
    public Language mapEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("language_id");
        String shortName = resultSet.getString("short_name");
        String fullName = resultSet.getString("full_name");

        final Language language = new Language();
        language.setId(id);
        language.setShortName(shortName);
        language.setFullName(fullName);
        return language;
    }
}
