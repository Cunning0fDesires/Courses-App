package com.epam.project.webappcourses.dao.mapper;

import com.epam.project.webappcourses.entities.Journal;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JournalMapper implements EntityMapper<Journal> {

    @Override
    public Journal mapEntity(ResultSet resultSet) throws SQLException {
        int recordId = resultSet.getInt("id_record");
        int studentId = resultSet.getInt("student_id");
        int course = resultSet.getInt("course_teacher");
        int grade = resultSet.getInt("grade");

        Journal journal = new Journal();
        journal.setId(recordId);
        journal.setStudentId(studentId);
        journal.setCourse(course);
        journal.setGrade(grade);

        return journal;
    }
}
