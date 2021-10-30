package com.epam.project.webappcourses.entities;

public class Journal implements Entity {
    private int recordId;
    private int studentId;
    private int grade;
    private int course;

    public Journal(int id, int studentId, int grade, int course) {
        this.recordId = id;
        this.studentId = studentId;
        this.grade = grade;
        this.course = course;
    }

    public Journal() {
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    @Override
    public int getId() {
        return recordId;
    }

    @Override
    public void setId(int id) {
     this.recordId = id;
    }

}
