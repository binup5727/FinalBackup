package com.example.final_poc;

import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String email;
    private String favorites;

    //constructor
    public User(int id, String name, String email, String favorites) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.favorites = favorites;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFavorites() {
        return favorites;
    }

    public void setFavorites(String favorites) {
        this.favorites = favorites;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", favorites=" + favorites +
                '}';
    }
}
