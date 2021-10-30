package com.epam.project.webappcourses.entities;

public class CourseDescription {
    private int courseDescriptionId;
    private int languageId;
    private String name;

    public CourseDescription(int courseDescriptionId, int languageId, String name) {
        this.courseDescriptionId = courseDescriptionId;
        this.languageId = languageId;
        this.name = name;
    }

    public CourseDescription() {
    }

    public int getCourseDescriptionId() {
        return courseDescriptionId;
    }

    public void setCourseDescriptionId(int courseDescriptionId) {
        this.courseDescriptionId = courseDescriptionId;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
