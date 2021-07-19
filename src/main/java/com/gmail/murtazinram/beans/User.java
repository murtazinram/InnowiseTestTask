package com.gmail.murtazinram.beans;

import java.util.List;

public class User {
    private String name;
    private String surname;
    private String email;
    private List<String> phonesList;
    private List<Role> roleList;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getPhonesList() {
        return phonesList;
    }

    public void setPhonesList(List<String> phonesList) {
        this.phonesList = phonesList;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phonesList=" + phonesList +
                ", roleList=" + roleList +
                '}';
    }
}
