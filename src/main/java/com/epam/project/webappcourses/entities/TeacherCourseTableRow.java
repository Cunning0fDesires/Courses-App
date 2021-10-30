package com.epam.project.webappcourses.entities;

import java.io.Serializable;

public class TeacherCourseTableRow implements Serializable, Entity {
    private int id;
    private String teacher;
    private String course;

    public TeacherCourseTableRow() {
    }


    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
    this.id = id;
    }
}
