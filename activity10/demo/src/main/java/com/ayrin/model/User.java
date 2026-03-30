package com.ayrin.model;

/**
 * Model class representing a user in the Hangman game.
 *
 * Stores the username, password, and total accumulated score.
 * This class is used for authentication and leaderboard tracking.
 *
 * @author ayrinhaha
 */
public class User {
    public String username;
    public String password;
    public int totalScore;

    /**
     * Constructs a User with the given details.
     *
     * @param username   the user's username
     * @param password   the user's password
     * @param totalScore the user's total accumulated score
     */
    public User(String username, String password, int totalScore) {
        this.username = username;
        this.password = password;
        this.totalScore = totalScore;
    }

    /**
     * Converts the user object into a string format for file storage.
     *
     * Format: username,password,totalScore
     *
     * @return a string representation of the user
     */
    @Override
    public String toString() {
        return username + "," + password + "," + totalScore;
    }
}