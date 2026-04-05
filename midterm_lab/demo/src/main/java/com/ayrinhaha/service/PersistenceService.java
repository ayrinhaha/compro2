package com.ayrinhaha.service;

import com.ayrinhaha.model.GameResult;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Handles user storage, authentication, and global leaderboard.
 *
 * @author ayrinhaha
 */
public class PersistenceService {

    private final String fileName = "users.json";

    private static class UserEntry {
        String username;
        String password;
        int wins;
        int losses;

        double getWinRate() {
            int total = wins + losses;
            return total == 0 ? 0.0 : (double) wins / total;
        }
    }

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

    private void saveUsers(List<UserEntry> users) {
        try (FileWriter writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(users, writer);
        } catch (Exception e) {
        }
    }

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
     * Updates global stats using all rounds played in a session.
     */
    public void updateStats(List<GameResult> results) {

        List<UserEntry> users = loadUsers();

        for (GameResult r : results) {

            String winner = r.getWinnerName();
            String p1 = r.getP1Name();
            String p2 = r.getP2Name();

            UserEntry u1 = find(users, p1);
            UserEntry u2 = find(users, p2);

            if (u1 == null || u2 == null)
                continue;

            if (winner.equals(p1)) {
                u1.wins++;
                u2.losses++;
            } else if (winner.equals(p2)) {
                u2.wins++;
                u1.losses++;
            }
        }

        saveUsers(users);
    }

    private UserEntry find(List<UserEntry> users, String name) {
        for (UserEntry u : users) {
            if (u.username.equals(name))
                return u;
        }
        return null;
    }

    public String getLeaderboardData() {

        List<UserEntry> list = loadUsers();
        list.sort((a, b) -> Double.compare(b.getWinRate(), a.getWinRate()));

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {

            UserEntry u = list.get(i);
            int winRate = (int) Math.round(u.getWinRate() * 100);

            sb.append(String.format("%d. %s %d%%<br>",
                    i + 1, u.username, winRate));
        }

        return sb.length() == 0 ? "No data yet." : sb.toString();
    }
}