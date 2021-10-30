package com.epam.project.webappcourses.entities;

public class JournalTableRow {
    private int id;
    private String studentName;
    private String studentSurname;
    private int grade;

    public JournalTableRow(int id, String studentName, String studentSurname, int grade) {
        this.id = id;
        this.studentName = studentName;
        this.grade = grade;
    }

    public JournalTableRow() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentSurname() {
        return studentSurname;
    }

    public void setStudentSurname(String studentSurname) {
        this.studentSurname = studentSurname;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
