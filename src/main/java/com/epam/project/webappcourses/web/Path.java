package com.epam.project.webappcourses.web;

public class Path {

    // pages
    public static final String STUDENT_ACCOUNT = "/WEB-INF/jsp/student/loginsuccess.jsp";
    public static final String TEACHER_ACCOUNT = "/WEB-INF/jsp/teacher/logincussess.jsp";
    public static final String ADMIN_ACCOUNT = "/WEB-INF/jsp/admin/admin_account.jsp";

    public static final String STUDENT_SEARCH = "/WEB-INF/jsp/student/search.jsp";
    public static final String PAGE__LOGIN = "/index.jsp";
    public static final String ADD_COURSE = "/WEB-INF/jsp/admin/addcourse.jsp";
    public static final String ADD_TEACHER = "/WEB-INF/jsp/admin/addteacher.jsp";



    public static final String PAGE__ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
    public static final String PAGE__SUCCESS = "/WEB-INF/jsp/success_page.jsp";
    public static final String PAGE__ERROR_ATTACHING = "/WEB-INF/jsp/error_attaching_course.jsp";
    public static final String PAGE__ERROR_REGISTERING_TO_COURSE = "/WEB-INF/jsp/error_register_course.jsp";
    public static final String PAGE__STATUS_BLOCKED = "/WEB-INF/jsp/error_status.jsp";




    // commands
    public static final String COMMAND__LIST_ORDERS = "/controller?command=listOrders";
    public static final String COMMAND__LIST_MENU = "/controller?command=listMenu";

}
