package com.example.admin.firebase;

/**
 * Created by Admin on 2/17/2017.
 */

public class UserModel {
    String name;
    String email;

    public UserModel() {
    }

    public UserModel(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
