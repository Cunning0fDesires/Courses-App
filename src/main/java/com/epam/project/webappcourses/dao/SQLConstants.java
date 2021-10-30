package com.epam.project.webappcourses.dao;

public class SQLConstants {
    public static final String TABLE_USER = "public.user";
    public static final String TABLE_COURSE = "public.course";
    public static final String TABLE_JOURNAL = "public.journal";
    public static final String TABLE_LANGUAGE = "public.language";
    public static final String TABLE_TEACHER_COURSE = "public.teacher_course";
    public static final String TABLE_TOPIC = "public.topic";
    public static final String TABLE_STATUS = "public.status";


    public static final String GET_ALL_USERS = "SELECT * FROM " + TABLE_USER;
    public static final String GET_USER_BY_ID = "SELECT * FROM " + TABLE_USER + " WHERE user_id = ?;";
    public static final String GET_USER_BY_LOGIN = "SELECT * FROM " + TABLE_USER + " WHERE login = ?;";
    public static final String GET_USER_BY_ROLE = "SELECT * FROM " + TABLE_USER + " WHERE role_id = ?;";
    public static final String DELETE_USER_BY_ID = "DELETE FROM " + TABLE_USER + " WHERE user_id = ?;";
    public static final String ADD_NEW_USER = "INSERT INTO " + TABLE_USER + " (role_id, login, name, surname, email, password) VALUES (?, ?, ?, ?, ?, crypt(?, gen_salt('bf')))";
    public static final String GET_USER_BY_LOGIN_PASSWORD = "SELECT * FROM " + TABLE_USER + " WHERE login = ? AND password = ?;";
    public static final String GET_ALL_COURSES = "SELECT * FROM " + TABLE_COURSE;
    public static final String GET_COURSE_BY_ID = "SELECT * FROM " + TABLE_COURSE + " WHERE course_id = ?;";
    public static final String GET_COURSES_TO_BE_STARTED_BY_STUDENT_ID = "SELECT c.course_id, c.name FROM public.journal j RIGHT JOIN public.teacher_course tc ON j.course_teacher = tc.id RIGHT JOIN course c ON tc.course_id = c.course_id WHERE j.student_id = ? AND start_date > 'today';";
    public static final String GET_FINISHED_COURSES_BY_STUDENT_ID = "SELECT c.course_id, c.name FROM public.journal j RIGHT JOIN public.teacher_course tc ON j.course_teacher = tc.id RIGHT JOIN course c ON tc.course_id = c.course_id WHERE j.student_id = ? AND end_date < 'today';";
    public static final String GET_COURSES_IN_PROGRESS_BY_STUDENT_ID = "SELECT c.course_id, c.name FROM public.journal j RIGHT JOIN public.teacher_course tc ON j.course_teacher = tc.id RIGHT JOIN course c ON tc.course_id = c.course_id WHERE j.student_id = ? AND start_date <= 'today' AND 'today' < end_date;";
    public static final String GET_COURSE_DURATION_IN_WEEKS = "SELECT ((end_date - start_date) / 7)::int FROM " + TABLE_COURSE + " WHERE course_id = ?;";
    public static final String GET_RECORDS_BY_STUDENT_ID = "SELECT * FROM " + TABLE_JOURNAL + " WHERE student_id = ?;";
    public static final String GET_ALL_JOURNAL_RECORDS = "SELECT * FROM " + TABLE_JOURNAL;
    public static final String GET_COURSE_TEACHER_BY_ID = "SELECT * FROM " + TABLE_TEACHER_COURSE + " WHERE id = ?;";
    public static final String GET_ALL_COURSES_BY_TEACHER = "SELECT * FROM " + TABLE_TEACHER_COURSE;
    public static final String GET_COURSE_TEACHER_BY_COURSE_ID = "SELECT * FROM " + TABLE_TEACHER_COURSE + " WHERE course_id = ?;";
    public static final String GET_STUDENTS_NUMBER_REGISTERED_TO_COURSE_BY_ID = "SELECT count(*) FROM " + TABLE_JOURNAL + " WHERE course_teacher = ?;";
    public static final String GET_GRADE_BY_STUDENT_ID = "SELECT grade FROM " + TABLE_JOURNAL + " WHERE student_id = ?;";
    public static final String GET_COURSE_ID_BY_STUDENT_ID = "SELECT course_teacher FROM " + TABLE_JOURNAL + " WHERE student_id = ?;";
    public static final String GET_COURSES_BY_TEACHER_ID = "SELECT c.course_id FROM public.teacher_course tc JOIN public.user u ON tc.teacher_id = u.user_id JOIN public.course c ON tc.course_id = c.course_id WHERE u.user_id = ?;";
    public static final String GET_RECORDS_BY_TEACHER_ID_AND_COURSE_ID = "SELECT j.id_record, j.student_id, j.course_teacher, j.grade FROM public.journal j JOIN public.teacher_course tc ON j.course_teacher = tc.id WHERE tc.teacher_id = ? AND tc.course_id = ?;";
    public static final String GET_TEACHER_BY_COURSE_ID = "SELECT u.user_id, u.name, u.surname FROM public.course c RIGHT JOIN public.teacher_course tc ON  c.course_id = tc.course_id RIGHT JOIN public.user u ON tc.teacher_id = u.user_id WHERE tc.course_id = ?;";
    public static final String GET_GRADE_BY_STUDENT_AND_COURSE = "SELECT j.grade FROM public.course c RIGHT JOIN public.teacher_course tc ON  c.course_id = tc.course_id RIGHT JOIN public.journal j ON tc.id = j.course_teacher WHERE tc.course_id = ? AND j.student_id = ?;";
    public static final String GET_COURSES_SORT_BY_NAME = "SELECT DISTINCT cd.name, t.topic_name AS topic, u.name || ' '|| u.surname AS teacher, ((c.end_date - c.start_date) / 7)::int AS duration, count(j.id_record) OVER (PARTITION BY c.name) AS students_registered FROM public.course c RIGHT JOIN public.course_description cd ON c.course_description_id = cd.course_description_id RIGHT JOIN public.language l ON cd.language_id = l.language_id RIGHT JOIN public.topic t ON  c.topic_id = t.topic_id RIGHT JOIN public.teacher_course tc ON  c.course_id = tc.course_id RIGHT JOIN public.journal j ON  tc.id = j.course_teacher RIGHT JOIN public.user u ON tc.teacher_id = u.user_id WHERE u.role_id = 1 AND l.short_name = ?;";
    public static final String GET_COURSES_SORT_BY_DURATION = "SELECT DISTINCT cd.name, t.topic_name AS topic, u.name || ' '|| u.surname AS teacher, ((c.end_date - c.start_date) / 7)::int AS duration, count(j.id_record) OVER (PARTITION BY cd.name) AS students_registered FROM public.course c RIGHT JOIN public.course_description cd ON c.course_description_id = cd.course_description_id RIGHT JOIN public.language l ON cd.language_id = l.language_id RIGHT JOIN public.topic t ON  c.topic_id = t.topic_id RIGHT JOIN public.teacher_course tc ON  c.course_id = tc.course_id RIGHT JOIN public.journal j ON  tc.id = j.course_teacher RIGHT JOIN public.user u ON tc.teacher_id = u.user_id WHERE u.role_id = 1 AND l.short_name = ? ORDER BY duration;";
    public static final String GET_COURSES_SORT_BY_STUDENTS_NUMBER = "SELECT DISTINCT cd.name, t.topic_name AS topic, u.name || ' '|| u.surname AS teacher, ((c.end_date - c.start_date) / 7)::int AS duration, count(j.id_record) OVER (PARTITION BY c.name) AS students_registered FROM public.course c RIGHT JOIN public.course_description cd ON c.course_description_id = cd.course_description_id RIGHT JOIN public.language l ON cd.language_id = l.language_id RIGHT JOIN public.topic t ON  c.topic_id = t.topic_id RIGHT JOIN public.teacher_course tc ON  c.course_id = tc.course_id RIGHT JOIN public.journal j ON  tc.id = j.course_teacher RIGHT JOIN public.user u ON tc.teacher_id = u.user_id WHERE u.role_id = 1 AND l.short_name = ? ORDER BY students_registered;";
    public static final String GET_ALL_TEACHERS_WITH_COURSES = "SELECT u.user_id, u.name || ' ' || u.surname as teacher, c.name AS course FROM public.user u RIGHT JOIN public.teacher_course tc ON  u.user_id = tc.teacher_id RIGHT JOIN public.course c ON  tc.course_id = c.course_id WHERE u.role_id = 1;";
    public static final String GET_COURSES_INFO_FOR_ADMIN = "SELECT c.course_id, c.name, t.topic_name, c.start_date, c.end_date FROM public.course c RIGHT JOIN public.topic t ON  c.topic_id = t.topic_id;";
    public static final String GET_STUDENTS_STATUS_LIST = "SELECT u.user_id, u.name || ' ' || u.surname AS student, s.status_name AS status FROM public.user u RIGHT JOIN public.status s ON u.status = s.status_id WHERE role_id = 2;";
    public static final String GET_TEACHER_COURSE_ID_BY_COURSE_ID = "SELECT id FROM public.teacher_course tc RIGHT JOIN public.course c ON  tc.course_id = c.course_id WHERE c.course_id = ?;";
    public static final String ADD_NEW_JOURNAL_RECORD = "INSERT INTO " + TABLE_JOURNAL + " (student_id, course_teacher) VALUES (?, ?);";
    public static final String GET_ALL_TEACHERS = "SELECT * FROM " + TABLE_USER + " WHERE role_id = 1;";
    public static final String ADD_NEW_TEACHER = "INSERT INTO " + TABLE_USER + " (role_id, login, name, surname, email, password) VALUES (1, ?, ?, ?, ?, crypt(?, gen_salt('bf')))";
    public static final String GET_COURSE_BY_NAME = "SELECT * FROM " + TABLE_COURSE + " WHERE name = ?;";
    public static final String ATTACH_COURSE_TO_TEACHER = "INSERT INTO " + TABLE_TEACHER_COURSE + " (teacher_id, course_id) VALUES (?, ?);";
    public static final String GET_TOPIC_ID_BY_NAME = "SELECT topic_id FROM " + TABLE_TOPIC + " WHERE topic_name = ?;";
    public static final String ADD_NEW_TOPIC = "INSERT INTO " + TABLE_TOPIC + " (topic_name) VALUES (?);";
    public static final String GET_ALL_TOPICS = "SELECT topic_name FROM " + TABLE_TOPIC;
    public static final String ADD_NEW_COURSE = "INSERT INTO " + TABLE_COURSE + " (end_date, name, start_date, topic_id, course_description_id) VALUES (?, ?, ?, ?, ?);";
    public static final String GET_STATUS_ID_BY_NAME = "SELECT status_id FROM " + TABLE_STATUS + " WHERE status_name = ?;";
    public static final String GET_COURSE_DESCRIPTION = "SELECT cd.name FROM public.course_description cd JOIN public.course c ON c.course_description_id = cd.course_description_id JOIN public.language l ON l.language_id = cd.language_id WHERE c.course_id = ? AND l.short_name =?;";
    public static final String GET_ALL_LANGUAGES = "SELECT * FROM " + TABLE_LANGUAGE;
    public static final String GET_MAX_COURSE_DESCRIPTION_ID = "SELECT MAX(course_description_id) FROM public.course_description;";
    public static final String GET_ALL_COURSES_WITH_TEACHERS = "SELECT * FROM " + TABLE_TEACHER_COURSE;
    public static final String GET_JOURNAL_RECORD_ID_BY_COURSE_ID = "SELECT tc.id FROM public.course c RIGHT JOIN public.teacher_course tc ON  c.course_id = tc.course_id RIGHT JOIN public.user u ON tc.teacher_id = u.user_id WHERE c.course_id = ?;";


















}
