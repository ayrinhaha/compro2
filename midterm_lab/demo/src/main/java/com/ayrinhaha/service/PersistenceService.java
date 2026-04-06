package com.ayrinhaha.service;

import com.ayrinhaha.model.GameResult;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

/**
 * handles user storage, authentication, and global leaderboard.
 * stores users in a json file, manages login/registration, updates win/loss
 * stats,
 * and formats leaderboard data.
 * author: ayrinhaha
 */
public class PersistenceService {

    // path to the users json file
    private final String fileName = "data/users.json";

    // inner class representing a user entry
    private static class UserEntry {
        String username;
        String password;
        int wins;
        int losses;

        // calculates the win rate of the user
        double getWinRate() {
            int total = wins + losses;
            return total == 0 ? 0.0 : (double) wins / total;
        }
    }

    // loads all users from the json file
    private List<UserEntry> loadUsers() {
        File file = new File(fileName);
        if (!file.exists())
            return new ArrayList<>();

        try (FileReader reader = new FileReader(file)) {

            Gson gson = new Gson();
            Type type = new TypeToken<List<UserEntry>>() {
            }.getType();

            List<UserEntry> users = gson.fromJson(reader, type);
            return users != null ? users : new ArrayList<>();

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // saves the list of users back to the json file
    private void saveUsers(List<UserEntry> users) {
        try (FileWriter writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(users, writer);
        } catch (Exception e) {
        }
    }

    /**
     * authenticates a user for login or register.
     * if isRegister is true, adds a new user if username is available.
     * if false, verifies username and password match.
     * 
     * @param user       username input
     * @param pass       password input
     * @param isRegister true for registration, false for login
     * @return true if authentication succeeds, false otherwise
     */
    public boolean authenticate(String user, String pass, boolean isRegister) {

        user = user.trim();
        pass = pass.trim();

        List<UserEntry> users = loadUsers();

        if (isRegister) {
            for (UserEntry u : users) {
                if (u.username.equalsIgnoreCase(user))
                    return false;
            }

            UserEntry newUser = new UserEntry();
            newUser.username = user;
            newUser.password = pass;
            newUser.wins = 0;
            newUser.losses = 0;

            users.add(newUser);
            saveUsers(users);
            return true;
        }

        for (UserEntry u : users) {
            if (u.username.equalsIgnoreCase(user) && u.password.equals(pass)) {
                return true;
            }
        }

        return false;
    }

    /**
     * updates global stats using all rounds played in a session.
     * increments wins/losses for each player based on session results.
     * 
     * @param results list of game results
     */
    public void updateStats(List<GameResult> results) {

        if (results.isEmpty())
            return;

        List<UserEntry> users = loadUsers();

        String p1 = results.get(0).getP1Name();
        String p2 = results.get(0).getP2Name();

        int p1Wins = 0;
        int p2Wins = 0;

        for (GameResult r : results) {
            if (r.getWinnerName().equals(p1))
                p1Wins++;
            else if (r.getWinnerName().equals(p2))
                p2Wins++;
        }

        UserEntry u1 = find(users, p1);
        UserEntry u2 = find(users, p2);

        if (u1 == null || u2 == null)
            return;

        if (p1Wins > p2Wins) {
            u1.wins++;
            u2.losses++;
        } else if (p2Wins > p1Wins) {
            u2.wins++;
            u1.losses++;
        }

        saveUsers(users);
    }

    // finds a user by username from the list
    private UserEntry find(List<UserEntry> users, String name) {
        for (UserEntry u : users) {
            if (u.username.equals(name))
                return u;
        }
        return null;
    }

    /**
     * returns formatted leaderboard data.
     * sorts users by win rate descending and formats as "1. username - XW/XL
     * (YY%)".
     * 
     * @return string containing the leaderboard
     */
    public String getLeaderboardData() {

        List<UserEntry> list = loadUsers();
        list.sort((a, b) -> Double.compare(b.getWinRate(), a.getWinRate()));

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {

            UserEntry u = list.get(i);
            int winRate = (int) Math.round(u.getWinRate() * 100);

            sb.append(String.format("%d. %s - %dW/%dL (%d%%);",
                    i + 1, u.username, u.wins, u.losses, winRate));
        }

        return sb.length() == 0 ? "No data yet." : sb.toString();
    }
}