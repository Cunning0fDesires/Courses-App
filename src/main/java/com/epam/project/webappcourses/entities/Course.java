package com.epam.project.webappcourses.entities;

import java.time.LocalDate;

public class Course implements Entity {
    private int id;
    private CourseDescription courseDescription;
    private String name;
    private int topicId;
    private LocalDate startDate;
    private LocalDate endDate;
    public Course(int id, String name, CourseDescription courseDescription, int topicId, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.courseDescription = courseDescription;
        this.topicId = topicId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Course() {
    }

    public CourseDescription getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(CourseDescription courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
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
