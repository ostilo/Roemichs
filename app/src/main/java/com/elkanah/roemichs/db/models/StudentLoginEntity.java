package com.elkanah.roemichs.db.models;

public class StudentLoginEntity {
    private String username;
    private String userpassword;

    public StudentLoginEntity(String username, String userpassword) {
        this.username = username;
        this.userpassword = userpassword;
    }

    public String getUsername() {
        return username;
    }


    public String getUserpassword() {
        return userpassword;
    }

}
