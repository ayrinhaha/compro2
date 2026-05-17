package com.ayrinhaha.model;

/**
 * Represents a registered user in the system.
 * Used for login authentication and data isolation.
 *
 * @author ayrinhaha
 */
public class User {

    private String username;
    private String password;

    /**
     * Constructs a user with login credentials.
     *
     * @param username The user's account name.
     * @param password The user's account password.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

 public String getPassword() {
    return password;
}

    /**
     * Validates if the provided input matches the account password.
     *
     * @param input The password attempt.
     * @return True if passwords match, false otherwise.
     */
    public boolean validatePassword(String input) {
        return this.password.equals(input);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}