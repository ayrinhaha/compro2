package com.ayrinhaha.model;

public class User {
    private String username;
    private String password;

public User(String username, String password) {
this .username = username;


setPassword ( password) ;
}



    public String getUsername() {
        return username;

    }

    public boolean validatePassword(String input) {
        return this.password.equals(input);
    }

    public void setPassword(String password) {
        // TODO: Add password validation
        this.password = password;

    }
}
