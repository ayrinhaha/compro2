package com.ayrin;

import com.ayrin.model.User;
import com.ayrin.service.*;
import java.io.*;
import java.net.*;

public class HangmanServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8000);
        ScoreService scoreService = new ScoreService();
        System.out.println("Server Live on 8000...");

        while (true) {
            Socket client = server.accept();
            new Thread(() -> handleClient(client, scoreService)).start();
        }
    }

    private static void handleClient(Socket s, ScoreService ss) {
        try (PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {

            User currentUser = null;

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
            out.println("Welcome " + currentUser.username + "! Total Score: " + currentUser.totalScore);

            
            out.println("Difficulty (1-Easy, 2-Medium, 3-Hard):");
            out.println("REQUEST_INPUT");
            int diff = Integer.parseInt(in.readLine());
            HangmanService game = new HangmanService(diff);

            while (!game.isGameOver()) {

                out.println(game.getGameState());
                out.println("Your Guess:");
                out.println("REQUEST_INPUT");

                String guess = in.readLine();
                out.println(game.processGuess(guess));
            }

            //post game
            if (game.isWin()) {
                currentUser = ss.updateScore(currentUser, game.getScore());
                out.println("WINNER! You got " + game.getScore() + " points.");
            } else {
                out.println("GAME OVER! Word was: " + game.getWord());
            }

            //print to server
            ss.printLeaderboardToServer();
        } catch (Exception e) {
            System.out.println("Client left.");
        }
       
    }

}