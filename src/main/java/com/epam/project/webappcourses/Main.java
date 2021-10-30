package com.epam.project.webappcourses;

import com.epam.project.webappcourses.dao.implemented_dao.*;
import com.epam.project.webappcourses.entities.Journal;
import com.epam.project.webappcourses.exceptions.DBException;
import com.epam.project.webappcourses.web.Path;
import com.epam.project.webappcourses.web.command.CommandResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarOutputStream;


public class Main {
    public static void main(String[] args) {
        int courseTeacherId = 0;
        List<Journal> allRecordsOfStudent = new ArrayList<>();
        List<Integer> idsOfCourseRecords = new ArrayList<>();
        try {
            courseTeacherId = JDBCJournalDao.getCourseTeacherIdByCourseId(8);
            allRecordsOfStudent = JDBCJournalDao.getAllRecordsByStudentId(24);
            for (Journal record : allRecordsOfStudent) {
                idsOfCourseRecords.add(record.getCourse());
            }
        } catch (DBException e) {
            e.printStackTrace();
        }
        System.out.println(idsOfCourseRecords);

        if (idsOfCourseRecords.contains(courseTeacherId)) {
            System.out.println("error registering");
        } else {
            JDBCJournalDao journalDao = new JDBCJournalDao();
            Journal newRecord = new Journal();
            newRecord.setStudentId(24);
            newRecord.setCourse(courseTeacherId);
            try {
                journalDao.add(newRecord);
            } catch (DBException e) {
                e.printStackTrace();
            }
            System.out.println("success");
        }
    }
}
