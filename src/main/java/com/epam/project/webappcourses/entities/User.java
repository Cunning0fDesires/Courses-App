package com.epam.project.webappcourses.entities;


import java.io.Serializable;

public class User implements Serializable, Entity {
    private int id;
    private int role;
    private String login;
    private String name;
    private String surname;
    private String patronymic_name;
    private String email;
    private String password;
    private int status;

    public User(int id, int role, String login, String name, String surname, String patronymic_name, String email, String password, int status) {
        this.id = id;
        this.role = role;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.patronymic_name = patronymic_name;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public User() {
    }


    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic_name() {
        return patronymic_name;
    }

    public void setPatronymic_name(String patronymic_name) {
        this.patronymic_name = patronymic_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
