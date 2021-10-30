package com.epam.project.webappcourses.entities;

import java.time.LocalDate;

public class CourseTableRow {
    private String name;
    private String teacher;
    private String duration;
    private int studentsRegistered;
    private Object grade;
    private LocalDate startDate;
    private LocalDate endDate;

    public CourseTableRow(String name, String teacher, String duration, int studentsRegistered, Object grade, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.teacher = teacher;
        this.duration = duration;
        this.studentsRegistered = studentsRegistered;
        this.grade = grade;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public CourseTableRow() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getStudentsRegistered() {
        return studentsRegistered;
    }

    public void setStudentsRegistered(int studentsRegistered) {
        this.studentsRegistered = studentsRegistered;
    }

    public Object getGrade() {
        return grade;
    }

    public void setGrade(Object grade) {
        this.grade = grade;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
