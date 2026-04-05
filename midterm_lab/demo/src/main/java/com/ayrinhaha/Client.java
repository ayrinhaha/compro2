package com.ayrinhaha;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Client-side application for the multiplayer game.
 * Handles connection, authentication, gameplay input, and result display.
 *
 * @author ayrinhaha
 */
public class Client {

    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 5000);
             Scanner sc = new Scanner(System.in)) {

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Connected. Waiting for START...");

            if (!"START".equals(in.readLine())) {
                System.out.println("Server error.");
                return;
            }

            System.out.println("Game starting!\n");

            String myName;

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
                    System.out.println("\nWelcome, " + myName + "!\n");
                    break;
                } else {
                    System.out.println("Error: " + authResponse.split("\\|")[1]);
                }
            }

            String names = in.readLine();
            String[] n = names.split("\\|");

            String p1Name = n[1];
            String p2Name = n[2];

            for (int i = 1; i <= 10; i++) {

                in.readLine();

                int move;
                while (true) {
                    System.out.print(
                            "Round " + i +
                                    "\n[0] Rock\n[1] Paper\n[2] Scissors\nEnter move: ");

                    try {
                        move = Integer.parseInt(sc.nextLine().trim());
                        if (move >= 0 && move <= 2) break;
                    } catch (Exception e) {}

                    System.out.println("\nInvalid input.");
                }

                out.println(move);

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

            String finalResponse = in.readLine();

            if (finalResponse != null && finalResponse.startsWith("FINAL|")) {
                handleFinal(finalResponse);
            }

        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }

    /**
     * Handles final game result and leaderboard display.
     */
    private static void handleFinal(String response) {

        String[] parts = response.split("\\|");

        System.out.println("\n=== FINAL RESULT ===");
        System.out.println("Match Winner: " + parts[1]);

        System.out.println("\nCurrent Leaderboard:");
        System.out.println(parts[2].replace("<br>", "\n"));
    }
}