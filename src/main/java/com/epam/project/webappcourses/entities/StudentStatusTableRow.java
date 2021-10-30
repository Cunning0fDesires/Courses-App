package com.epam.project.webappcourses.entities;

import java.io.Serializable;

public class StudentStatusTableRow implements Serializable, Entity{
    private String student;
    private int id;
    private String status;

    public StudentStatusTableRow() {
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
