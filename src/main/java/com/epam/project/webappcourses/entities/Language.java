package com.epam.project.webappcourses.entities;

public class Language implements Entity {
    private int languageId;
    private String shortName;
    private String fullName;

    public Language(int languageId, String shortName, String fullName) {
        this.languageId = languageId;
        this.shortName = shortName;
        this.fullName = fullName;
    }

    public Language() {
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public int getId() {
        return languageId;
    }

    @Override
    public void setId(int id) {
        languageId = id;
    }
}
