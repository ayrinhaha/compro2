package com.ayrinhaha;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Client-side application for the multiplayer game.
 * Handles connection, authentication, gameplay input, and result display.
 * <p>
 * Connects to a server at localhost:5000, performs login or registration,
 * plays 3 rounds of Rock-Paper-Scissors, and displays the final match result
 * along with the global leaderboard.
 * </p>
 * 
 * @author ayrinhaha
 */
public class Client {

    /**
     * Entry point for the client application.
     * 
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 5000);
                Scanner sc = new Scanner(System.in)) {

            // streams for communication with the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Connected. Waiting for START...");

            // wait for server to send START signal
            if (!"START".equals(in.readLine())) {
                System.out.println("Server error.");
                return;
            }

            System.out.println("Game starting!\n");

            String myName;

            // user authentication loop
            while (true) {

                System.out.println("[1] Login\n[2] Sign Up");
                System.out.print("Choose option: ");
                String choice = sc.nextLine().trim();

                if (!choice.equals("1") && !choice.equals("2")) {
                    System.out.println("Invalid choice.\n");
                    continue;
                }

                System.out.print("Username: ");
                String user = sc.nextLine().trim();

                System.out.print("Password: ");
                String pass = sc.nextLine().trim();

                String action = choice.equals("1") ? "LOGIN" : "REGISTER";

                out.println(action + "|" + user + "|" + pass);

                String authResponse = in.readLine();

                if (authResponse == null) {
                    System.out.println("Server disconnected.");
                    return;
                }

                if (authResponse.startsWith("SUCCESS|")) {
                    myName = authResponse.split("\\|")[1];
                    System.out.println("\nWelcome to Rock, Paper, Scissors, " + myName + "!\n");
                    break;
                } else {
                    System.out.println("Error: " + authResponse.split("\\|")[1]);
                }
            }

            // receive player names from server
            String names = in.readLine();
            String[] n = names.split("\\|");

            String p1Name = n[1];
            String p2Name = n[2];

            // main game loop for 3 rounds
            for (int i = 1; i <= 10; i++) {

                // wait for server signal (round start)
                in.readLine();

                int move;
                // player move input
                while (true) {
                    System.out.print(
                            "Round " + i +
                                    "\n[0] Rock\n[1] Paper\n[2] Scissors\nEnter move: ");

                    try {
                        move = Integer.parseInt(sc.nextLine().trim());
                        if (move >= 0 && move <= 2)
                            break;
                    } catch (Exception e) {
                        // Ignore parse errors
                    }

                    System.out.println("\nInvalid input.");
                }

                // send move to server
                out.println(move);

                // receive round result
                String response = in.readLine();

                if (response.startsWith("ROUND|")) {

                    String[] parts = response.split("\\|");

                    System.out.println("\n=====================");
                    System.out.println("Round " + parts[1]);
                    System.out.println(p1Name + ": " + parts[2]);
                    System.out.println(p2Name + ": " + parts[3]);
                    System.out.println("Winner: " + parts[4]);
                    System.out.println("Score: " + parts[5] + " - " + parts[6]);
                    System.out.println("=====================\n");
                }
            }

            // receive final game result and leaderboard
            String finalResponse = in.readLine();

            if (finalResponse != null && finalResponse.startsWith("FINAL|")) {
                handleFinal(finalResponse);
            }

        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }

    /**
     * Handles the final match result and displays the global leaderboard.
     * <p>
     * Parses a server message of format: FINAL|Winner|Leaderboard
     * Leaderboard entries are separated by ';'.
     * </p>
     * 
     * @param response the server response string containing final results
     */
    private static void handleFinal(String response) {

        String[] parts = response.split("\\|");

        System.out.println("\n=== FINAL RESULT ===");
        System.out.println("Match Winner: " + parts[1]);

        System.out.println("\nCurrent Leaderboard:");

        // Split leaderboard string into individual player entries
        String[] players = parts[2].split(";");

        for (String p : players) {
            if (!p.trim().isEmpty()) {
                System.out.println(p);
            }
        }
    }
}