package com.ayrin;

import com.ayrin.model.User;
import com.ayrin.service.*;
import java.io.*;
import java.net.*;

/**
 * Main server class for the Hangman game.
 * This server listens on port 8000 and handles multiple clients concurrently
 * using threads. Each client can sign in or sign up, then play a game of
 * Hangman.
 * Scores are managed via the ScoreService.
 *
 * @author ayrinhaha
 */
public class HangmanServer {

    /**
     * Entry point of the Hangman server application.
     * Initializes the server socket and continuously listens for incoming
     * client connections. Each client is handled in a separate thread.
     *
     * @param args command-line arguments (not used)
     * @throws IOException if an I/O error occurs when opening the socket
     */
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8000);
        ScoreService scoreService = new ScoreService();
        System.out.println("Server Live on 8000...");

        while (true) {
            Socket client = server.accept();
            new Thread(() -> handleClient(client, scoreService)).start();
        }
    }

    /**
     * This method manages:
     * User authentication (sign in / sign up)
     * Game initialization based on selected difficulty
     * Processing guesses during the Hangman game<
     * Updating and displaying scores
     * 
     * @param s  the client socket
     * @param ss the score service used for authentication and score management
     */
    private static void handleClient(Socket s, ScoreService ss) {
        try (PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {

            User currentUser = null;

            // authentication loop (sign in / sign up)
            while (currentUser == null) {

                out.println("1. Sign In");
                out.println("2. Sign Up");
                out.println("REQUEST_INPUT");

                String choice = in.readLine();

                if (choice == null)
                    continue;

                out.println("Username");
                out.println("REQUEST_INPUT");
                String user = in.readLine();

                out.println("Password");
                out.println("REQUEST_INPUT");
                String pass = in.readLine();

                if ("1".equals(choice)) {
                    currentUser = ss.signIn(user, pass);
                } else if ("2".equals(choice)) {
                    currentUser = ss.signUp(user, pass);
                }

                if (currentUser == null) {
                    out.println("Access Denied. Try again.");
                }
            }

            // welcome message and user score
            out.println("Welcome " + currentUser.username + "! Total Score: " + currentUser.totalScore);

            // difficulty selection
            out.println("Difficulty (1-Easy, 2-Medium, 3-Hard):");
            out.println("REQUEST_INPUT");
            int diff = Integer.parseInt(in.readLine());
            HangmanService game = new HangmanService(diff);

            // main game loop
            while (!game.isGameOver()) {

                out.println(game.getGameState());
                out.println("Your Guess:");
                out.println("REQUEST_INPUT");

                String guess = in.readLine();
                out.println(game.processGuess(guess));
            }

            // post-game result handling
            if (game.isWin()) {
                currentUser = ss.updateScore(currentUser, game.getScore());
                out.println("WINNER! You got " + game.getScore() + " points.");
            } else {
                out.println("GAME OVER! Word was: " + game.getWord());
            }

            // print leaderboard to server console
            ss.printLeaderboardToServer();

        } catch (Exception e) {
            System.out.println("Client left.");
        }
    }
}