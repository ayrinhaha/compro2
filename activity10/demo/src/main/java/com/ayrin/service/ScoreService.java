package com.ayrin.service;

import com.ayrin.model.User;
import java.io.*;
import java.util.ArrayList;

/**
 * Service class responsible for managing users and their scores.
 *
 * This class handles user authentication (sign in and sign up),
 * score updates, file storage, and leaderboard retrieval.
 *
 * User data is stored in a text file defined by FILE_NAME.
 *
 * @author ayrinhaha
 */
public class ScoreService {
    private static final String FILE_NAME = "users.txt";

    /**
     * Authenticates a user using a username and password.
     *
     * @param user the username
     * @param pass the password
     * @return the matching User if credentials are correct,
     *         otherwise null
     */
    public User signIn(String user, String pass) {
        ArrayList<User> users = loadUsers();
        for (User u : users) {
            if (u.username.equalsIgnoreCase(user) && u.password.equals(pass))
                return u;
        }
        return null;
    }

    /**
     * Registers a new user if the username is not already taken.
     *
     * @param user the desired username
     * @param pass the desired password
     * @return the created User if successful,
     *         otherwise null if username already exists
     */
    public User signUp(String user, String pass) {
        ArrayList<User> users = loadUsers();
        for (User u : users) {
            if (u.username.equalsIgnoreCase(user))
                return null;
        }
        User newUser = new User(user, pass, 0);
        users.add(newUser);
        saveUsers(users);
        return newUser;
    }

    /**
     * Updates the total score of a user by adding points.
     *
     * @param user   the user to update
     * @param points the points to add
     * @return the updated User with the new total score
     */
    public User updateScore(User user, int points) {
        ArrayList<User> users = loadUsers();

        for (User u : users) {
            if (u.username.equalsIgnoreCase(user.username)) {
                u.totalScore += points;
                user.totalScore = u.totalScore;
                break;
            }
        }

        saveUsers(users);
        return user;
    }

    /**
     * Loads all users from the file.
     *
     * @return a list of users; returns an empty list if
     *         the file does not exist or an error occurs
     */
    private ArrayList<User> loadUsers() {
        ArrayList<User> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                list.add(new User(p[0], p[1], Integer.parseInt(p[2])));
            }
        } catch (IOException e) {
            // File may not exist yet
        }
        return list;
    }

    /**
     * Saves all users to the file.
     *
     * @param users the list of users to store
     */
    private void saveUsers(ArrayList<User> users) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (User u : users)
                pw.println(u.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves users sorted by highest score first.
     *
     * @return a list of users sorted in descending order
     *         based on total score
     */
    public ArrayList<User> getLeaderboard() {
        ArrayList<User> users = loadUsers();

        users.sort((a, b) -> Integer.compare(b.totalScore, a.totalScore));

        return users;
    }

    /**
     * Prints the leaderboard to the server console.
     */
    public void printLeaderboardToServer() {
        ArrayList<User> users = getLeaderboard();

        System.out.println("\n===== ALL-TIME LEADERBOARD =====");

        for (User u : users) {
            System.out.println(u.username + " - " + u.totalScore);
        }

        System.out.println("================================\n");
    }
}