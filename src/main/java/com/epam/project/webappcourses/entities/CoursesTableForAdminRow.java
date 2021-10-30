package com.epam.project.webappcourses.entities;


import java.io.Serializable;
import java.time.LocalDate;

public class CoursesTableForAdminRow implements Serializable, Entity{
    private int id;
    private String name;
    private String topic;
    private LocalDate startDate;
    private LocalDate endDate;

    public CoursesTableForAdminRow() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
    this.id = id;
    }
}
