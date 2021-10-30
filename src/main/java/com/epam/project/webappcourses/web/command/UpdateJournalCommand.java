package com.epam.project.webappcourses.web.command;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.epam.project.webappcourses.dao.implemented_dao.JDBCCourseDao;
import com.epam.project.webappcourses.dao.implemented_dao.JDBCJournalDao;
import com.epam.project.webappcourses.dao.implemented_dao.JDBCUserDao;
import com.epam.project.webappcourses.entities.Course;
import com.epam.project.webappcourses.entities.CourseTableRow;
import com.epam.project.webappcourses.entities.Journal;
import com.epam.project.webappcourses.entities.JournalTableRow;
import com.epam.project.webappcourses.entities.User;
import com.epam.project.webappcourses.exceptions.DBException;
import com.epam.project.webappcourses.web.Path;

public class UpdateJournalCommand extends Command {
    private static final Logger log = Logger.getLogger(UpdateJournalCommand.class);

    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int tablesAmount = Integer.parseInt(request.getParameter("tablesAmount"));

        JDBCJournalDao journalDao = new JDBCJournalDao();
        for (int tableIndex = 0; tableIndex < tablesAmount; tableIndex++) {
            int rowsAmount = Integer.parseInt(request.getParameter("tableRowsAmount_" + tableIndex));
            for (int rowIndex = 0; rowIndex < rowsAmount; rowIndex++) {
                int id_record = Integer.parseInt(request.getParameter("id_record_" + tableIndex + "_" + rowIndex));
                int grade = Integer.parseInt(request.getParameter("grade_" + tableIndex + "_" + rowIndex));
                try {
                    journalDao.update(id_record, "grade", grade);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String lang = (String) session.getAttribute("lang");

        try {
            List<Long> coursesIds = JDBCCourseDao.getCoursesIdsByTeacherId(user.getId());
            Map<Course, List<Journal>> editableCoursesJournal = new HashMap<>();
            Map<Course, List<Journal>> readonlyCoursesJournal = new HashMap<>();

            JDBCCourseDao courseDao = new JDBCCourseDao();
            for (Long courseId : coursesIds) {
                Course course = courseDao.getById(courseId);
                course.setCourseDescription(JDBCCourseDao.getCourseDescription(courseId, lang));
                List<Journal> journalRecords = JDBCJournalDao.getAllRecordsByTeacherIdAndCourseId(user.getId(), courseId);
                if (course.getEndDate().isBefore(LocalDate.now()))
                    readonlyCoursesJournal.put(course, journalRecords);
                else
                    editableCoursesJournal.put(course, journalRecords);
            }

            Map<CourseTableRow, List<JournalTableRow>> editableCoursesJournalRows = getCoursesJournalRowsFor(editableCoursesJournal);
            Map<CourseTableRow, List<JournalTableRow>> readonlyCoursesJournalRows = getCoursesJournalRowsFor(readonlyCoursesJournal);
            session.setAttribute("editableCoursesJournalRows", editableCoursesJournalRows);
            session.setAttribute("readonlyCoursesJournalRows", readonlyCoursesJournalRows);
        } catch (DBException e) {
            e.printStackTrace();
        }

        return new CommandResponse(CommandResponse.DispatchType.FORWARD, Path.TEACHER_ACCOUNT);
    }

        private Map<CourseTableRow, List<JournalTableRow>> getCoursesJournalRowsFor(Map<Course, List<Journal>> coursesJournal) throws DBException {
        JDBCUserDao userDao = new JDBCUserDao();
        Map<CourseTableRow, List<JournalTableRow>> coursesJournalRows = new HashMap<>();
        for (Map.Entry<Course, List<Journal>> entry : coursesJournal.entrySet()) {
            CourseTableRow courseRow = new CourseTableRow();
            Course courseEntity = entry.getKey();
            courseRow.setName(courseEntity.getCourseDescription().getName());
            courseRow.setStartDate(courseEntity.getStartDate());
            courseRow.setEndDate(courseEntity.getEndDate());

            List<Journal> journals = entry.getValue();
            List<JournalTableRow> journalRows = new ArrayList<>();
            for (Journal journal : journals) {
                JournalTableRow row = new JournalTableRow();
                row.setId(journal.getId());
                User student = userDao.getById(journal.getStudentId());
                row.setStudentName(student.getName());
                row.setStudentSurname(student.getSurname());
                row.setGrade(journal.getGrade());
                journalRows.add(row);
            }
            coursesJournalRows.put(courseRow, journalRows);
        }
        return coursesJournalRows;
    }
}
