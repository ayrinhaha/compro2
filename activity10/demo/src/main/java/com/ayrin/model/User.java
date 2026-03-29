package com.ayrin.model;

public class User {
    public String username;
    public String password;
    public int totalScore;

    public User(String username, String password, int totalScore) {
        this.username = username;
        this.password = password;
        this.totalScore = totalScore;
    }

    @Override
    public String toString() {
        return username + "," + password + "," + totalScore;
    }
}