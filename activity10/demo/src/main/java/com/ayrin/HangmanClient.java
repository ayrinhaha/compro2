package com.ayrin;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class HangmanClient {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 8000;

        try (
                Socket socket = new Socket(host, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner sc = new Scanner(System.in)) {

            String serverResponse;

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
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}