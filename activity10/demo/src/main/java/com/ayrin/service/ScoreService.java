package com.ayrin.service;

import com.ayrin.model.User;
import java.io.*;
import java.util.ArrayList;

public class ScoreService {
    private static final String FILE_NAME = "users.txt";

    public User signIn(String user, String pass) {
        ArrayList<User> users = loadUsers();
        for (User u : users) {
            if (u.username.equalsIgnoreCase(user) && u.password.equals(pass))
                return u;
        }
        return null;
    }

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

    private ArrayList<User> loadUsers() {
        ArrayList<User> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                list.add(new User(p[0], p[1], Integer.parseInt(p[2])));
            }
        } catch (IOException e) {
            }
        return list;
    }

    private void saveUsers(ArrayList<User> users) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (User u : users)
                pw.println(u.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getLeaderboard() {
        ArrayList<User> users = loadUsers();

        users.sort((a, b) -> Integer.compare(b.totalScore, a.totalScore));

        return users;
    }

    public void printLeaderboardToServer() {
        ArrayList<User> users = getLeaderboard();

        System.out.println("\n===== ALL-TIME LEADERBOARD =====");

        for (User u : users) {
            System.out.println(u.username + " - " + u.totalScore);
        }

        System.out.println("================================\n");
    }
}