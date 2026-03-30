package com.ayrin;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Client class for the Hangman game.
 * Connects to the HangmanServer via socket communication and handles
 * user interaction through the console. It listens for server messages
 * and responds when input is requested.
 *
 * @author ayrinhaha
 */
public class HangmanClient {

    /**
     * Entry point of the Hangman client application.
     * Establishes a connection to the server and continuously listens
     * for server responses. When the server requests input, it collects
     * user input from the console and sends it back.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8000;

        try (
                Socket socket = new Socket(host, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner sc = new Scanner(System.in)) {

            String serverResponse;

            /**
             * Main loop that processes server messages.
             * 
             * If "REQUEST_INPUT" is received, prompts the user for input
             * If "DISCONNECT" is received, terminates the session
             * Otherwise, prints the server message
             * 
             */
            while ((serverResponse = in.readLine()) != null) {

                serverResponse = serverResponse.trim();

                if (serverResponse.equals("REQUEST_INPUT")) {

                    System.out.print("Input: ");
                    String userInput = sc.nextLine();
                    out.println(userInput);

                } else if (serverResponse.equals("DISCONNECT")) {
                    System.out.println("\n--- Thank you for playing! ---");
                    break;
                } else {
                    System.out.println(serverResponse);
                }
            }

        } catch (Exception e) {
            /**
             * Handles connection-related errors such as server unavailability
             * or network issues.
             */
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}