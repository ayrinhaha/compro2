package com.ayrinhaha.service;

import com.ayrinhaha.model.UserAccount;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles account authentication
 * and persistent storage using Gson.
 *
 * @author ayrinhaha
 */
public class AccountService {

    private static final String FILE = "accounts.json";

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private List<UserAccount> accounts = new ArrayList<>();

    /**
     * Loads accounts during startup.
     */
    public AccountService() {
        load();
    }

    /**
     * Authenticates a user account.
     *
     * @param username entered username
     * @param password entered password
     * @return matching account or null
     */
    public UserAccount login(
            String username,
            String password) {

        for (UserAccount acc : accounts) {

            if (acc.getUsername().equals(username)
                    && acc.validatePassword(password)) {

                return acc;
            }
        }

        return null;
    }

    /**
     * Registers a new user account.
     *
     * @param username new username
     * @param password new password
     * @return created account
     */
    public UserAccount register(
            String username,
            String password) {

        UserAccount acc = new UserAccount(username, password);

        accounts.add(acc);

        saveAll();

        return acc;
    }

    /**
     * Saves all accounts into accounts.json.
     */
    public void saveAll() {

        try (Writer writer = new FileWriter(FILE)) {

            gson.toJson(accounts, writer);

        } catch (Exception e) {

            System.out.println(
                    "[ERROR] Account save error.");
        }
    }

    /**
     * Loads all accounts from accounts.json.
     */
    public void load() {

        try (Reader reader = new FileReader(FILE)) {

            Type type = new TypeToken<List<UserAccount>>() {
            }.getType();

            accounts = gson.fromJson(reader, type);

            if (accounts == null) {
                accounts = new ArrayList<>();
            }

        } catch (Exception e) {

            accounts = new ArrayList<>();

            System.out.println("[INFO] No existing accounts found. Creating new storage.");
        }
    }

    /**
     * Saves latest account changes.
     *
     * @param acc updated account
     */
    public void sync(UserAccount acc) {

        saveAll();
    }
}