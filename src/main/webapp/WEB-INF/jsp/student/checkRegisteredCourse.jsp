<%@ page import="com.epam.project.webappcourses.entities.User" %>
<%@ page import="com.epam.project.webappcourses.dao.implemented_dao.JDBCCourseDao" %>
<%@ page import="com.epam.project.webappcourses.entities.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epam.project.webappcourses.entities.Journal" %>
<%@ page import="com.epam.project.webappcourses.dao.implemented_dao.JDBCJournalDao" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.epam.project.webappcourses.exceptions.DBException" %>
<%@ page import="com.epam.project.webappcourses.web.Controller" %>

<%
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        JDBCCourseDao courseDao = new JDBCCourseDao();
        try {
            List<Course> courses = courseDao.getAll();
            Course requestCourse = null;
            for (Course course : courses) {
                if (course.getName() == request.getParameter("course")) {
                    requestCourse = course;
                }
            }
            List<Journal> allRecordsOfStudent = JDBCJournalDao.getAllRecordsByStudentId(userId);
            int courseTeacherId = JDBCJournalDao.getCourseTeacherIdByCourseId(requestCourse.getId());
            List<Integer> idsOfCourseRecords = new ArrayList<>();
            for (Journal record : allRecordsOfStudent) {
                idsOfCourseRecords.add(record.getCourse());
            }
            if (idsOfCourseRecords.contains(courseTeacherId)) {
                System.out.println("false");
            } else {
                System.out.println("true");
            }
        } catch (DBException e) {
            e.printStackTrace();
        }
    %>
