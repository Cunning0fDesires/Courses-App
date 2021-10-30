package com.epam.project.webappcourses.web.command;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.epam.project.webappcourses.dao.implemented_dao.JDBCCourseDao;
import com.epam.project.webappcourses.dao.implemented_dao.JDBCCoursesTableForAdminRowDao;
import com.epam.project.webappcourses.dao.implemented_dao.JDBCJournalDao;
import com.epam.project.webappcourses.dao.implemented_dao.JDBCStudentsStatusTableRowDao;
import com.epam.project.webappcourses.dao.implemented_dao.JDBCTeacherCourseTableRowDao;
import com.epam.project.webappcourses.dao.implemented_dao.JDBCUserDao;
import com.epam.project.webappcourses.entities.Course;
import com.epam.project.webappcourses.entities.CourseTableRow;
import com.epam.project.webappcourses.entities.CoursesTableForAdminRow;
import com.epam.project.webappcourses.entities.Journal;
import com.epam.project.webappcourses.entities.JournalTableRow;
import com.epam.project.webappcourses.entities.Role;
import com.epam.project.webappcourses.entities.StudentStatusTableRow;
import com.epam.project.webappcourses.entities.TeacherCourseTableRow;
import com.epam.project.webappcourses.entities.User;
import com.epam.project.webappcourses.exceptions.DBException;
import com.epam.project.webappcourses.web.Path;

public class LoginCommand extends Command {
    private static final long serialVersionUID = -3071536593627692473L;
    private static final Logger log = Logger.getLogger(LoginCommand.class);


    @Override
    public CommandResponse execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");
        String forward = Path.PAGE__ERROR_PAGE;
        HttpSession session = request.getSession();
        Object rawUserRole = session.getAttribute("userRole");
        if (rawUserRole != null) {
            User user = (User) session.getAttribute("user");
            if (rawUserRole == Role.STUDENT)
                forward = processStudent(user, session);
            else if (rawUserRole == Role.TEACHER)
                forward = processTeacher(user, session);
            log.debug("Command finished");
            return new CommandResponse(CommandResponse.DispatchType.FORWARD, forward);
        }

        String login = request.getParameter("login");
        session.setAttribute("userLogin", login);
        log.trace("Request parameter: loging --> " + login);

        String password = request.getParameter("password");

        String errorMessage = null;

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return new CommandResponse(CommandResponse.DispatchType.FORWARD, forward);
        }
        User user = null;
        try {
            user = new JDBCUserDao().getUserByLogin(login);
        } catch (DBException e) {
            e.printStackTrace();
        }
        log.trace("Found in DB: user --> " + user);
        if (user == null || !password.equals(user.getPassword())) {
            errorMessage = "Cannot find user with such login/password";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return new CommandResponse(CommandResponse.DispatchType.FORWARD, forward);
        }

        Role userRole = Role.getRole(user);
        log.trace("userRole --> " + userRole);

        session.setAttribute("user", user);
        log.trace("Set the session attribute: user --> " + user);
        session.setAttribute("userRole", userRole);
        log.trace("Set the session attribute: userRole --> " + userRole);
        log.info("User " + user + " logged as " + userRole.toString().toLowerCase());

        if (userRole == Role.STUDENT)
            forward = processStudent(user, session);
        else if (userRole == Role.TEACHER)
            forward = processTeacher(user, session);
        else if (userRole == Role.ADMIN)
            forward = processAdmin(user, session);

//             work with i18n
//            String userLocaleName = user.getLocaleName();
//            log.trace("userLocalName --> " + userLocaleName);
//
//            if (userLocaleName != null && !userLocaleName.isEmpty()) {
//                Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", userLocaleName);
//
//                session.setAttribute("defaultLocale", userLocaleName);
//                log.trace("Set the session attribute: defaultLocaleName --> " + userLocaleName);
//
//                log.info("Locale for user: defaultLocale --> " + userLocaleName);
//            }
        log.debug("Command finished");
        return new CommandResponse(CommandResponse.DispatchType.FORWARD, forward);
    }

    private String processStudent(User user, HttpSession session) {
        List<CourseTableRow> coursesToBeStartedTable = new LinkedList<>();
        List<CourseTableRow> coursesInProgressTable = new LinkedList<>();
        List<CourseTableRow> finishedCoursesTable = new LinkedList<>();

        String lang = (String) session.getAttribute("lang");

        try {
            List<Course> notStartedCourses = JDBCCourseDao.getNotStartedCoursesByStudentId(user.getId());

            for (Course course : notStartedCourses) {
                CourseTableRow row = new CourseTableRow();
                row.setName(JDBCCourseDao.getCourseDescription(course.getId(), lang).getName());
                row.setGrade(JDBCJournalDao.getGradeByStudentAndCourse(user.getId(), course.getId()));
                row.setDuration(JDBCCourseDao.getDurationByCourseId(course.getId()));
                row.setStudentsRegistered(JDBCJournalDao.getStudentsRegisteredNumberByCourseId(course.getId()));
                row.setTeacher(JDBCJournalDao.getTeacherNameByCourseId(course.getId()));
                coursesToBeStartedTable.add(row);
            }
        } catch (DBException e) {
            e.printStackTrace();
        }
        session.setAttribute("coursesToBeStartedTable", coursesToBeStartedTable);

        try {
            List<Course> onGoingCourses = JDBCCourseDao.getCoursesInProgress(user.getId());

            for (Course course : onGoingCourses) {
                CourseTableRow row = new CourseTableRow();
                row.setName(JDBCCourseDao.getCourseDescription(course.getId(), lang).getName());
                row.setGrade(JDBCJournalDao.getGradeByStudentAndCourse(user.getId(), course.getId()));
                row.setDuration(JDBCCourseDao.getDurationByCourseId(course.getId()));
                row.setStudentsRegistered(JDBCJournalDao.getStudentsRegisteredNumberByCourseId(course.getId()));
                row.setTeacher(JDBCJournalDao.getTeacherNameByCourseId(course.getId()));
                coursesInProgressTable.add(row);
            }
        } catch (DBException e) {
            e.printStackTrace();
        }
        session.setAttribute("coursesInProgressTable", coursesInProgressTable);

        try {
            List<Course> finishedCourses = JDBCCourseDao.getFinishedCourses(user.getId());

            for (Course course : finishedCourses) {
                CourseTableRow row = new CourseTableRow();
                row.setName(JDBCCourseDao.getCourseDescription(course.getId(), lang).getName());
                row.setGrade(JDBCJournalDao.getGradeByStudentAndCourse(user.getId(), course.getId()));
                row.setDuration(JDBCCourseDao.getDurationByCourseId(course.getId()));
                row.setStudentsRegistered(JDBCJournalDao.getStudentsRegisteredNumberByCourseId(course.getId()));
                row.setTeacher(JDBCJournalDao.getTeacherNameByCourseId(course.getId()));
                finishedCoursesTable.add(row);
            }
        } catch (DBException e) {
            e.printStackTrace();
        }
        session.setAttribute("finishedCoursesTable", finishedCoursesTable);

        return Path.STUDENT_ACCOUNT;
    }

    private String processTeacher(User user, HttpSession session) {
        try {
            List<Long> coursesIds = JDBCCourseDao.getCoursesIdsByTeacherId(user.getId());
            Map<Course, List<Journal>> editableCoursesJournal = new HashMap<>();
            Map<Course, List<Journal>> readonlyCoursesJournal = new HashMap<>();

            String lang = (String) session.getAttribute("lang");

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
        return Path.TEACHER_ACCOUNT;
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

    private String processAdmin(User user, HttpSession session) {
        List<TeacherCourseTableRow> allTeachersWithCoursesList = new ArrayList<>();
        List<CoursesTableForAdminRow> allCourses = new ArrayList<>();
        List<StudentStatusTableRow> allStudentsWithStatus = new ArrayList<>();
        List<User> allTeachers = new ArrayList<>();

        try {
            allTeachersWithCoursesList = JDBCTeacherCourseTableRowDao.getAllTeachersWithCoursesList();
            allCourses = JDBCCoursesTableForAdminRowDao.getAll();
            allStudentsWithStatus = JDBCStudentsStatusTableRowDao.getAll();
            for (User teacher : JDBCUserDao.getAllTeachers()) {
                allTeachers.add(teacher);
            }

        } catch (DBException e) {
            e.printStackTrace();
        }
        session.setAttribute("allTeachersWithCoursesList", allTeachersWithCoursesList);
        session.setAttribute("allCourses", allCourses);
        session.setAttribute("allStudentsWithStatus", allStudentsWithStatus);
        session.setAttribute("allTeachers", allTeachers);

        return Path.ADMIN_ACCOUNT;
    }
}
