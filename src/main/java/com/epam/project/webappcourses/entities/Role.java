package com.epam.project.webappcourses.entities;

public enum Role {
    DEFAULT, TEACHER, STUDENT, ADMIN;

    public static Role getRole(User user) {
        int roleId = user.getRole();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
