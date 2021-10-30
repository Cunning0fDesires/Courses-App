package com.epam.project.webappcourses.entities;

public enum Status {
    DEFAULT, ACTIVE, BLOCKED;

    public static Status getRole(User user) {
        int statusId = user.getStatus();
        return Status.values()[statusId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
