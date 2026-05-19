package com.ayrinhaha.network;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * Handles outgoing network connections to the finance server.
 *
 * @author ayrinhaha
 */
public class Client {

    /**
     * Sends formatted data strings over socket to the local server port 5000.
     *
     * @param data The JSON formatted string payload.
     */
    public void send(String data) {
        try (Socket socket = new Socket("localhost", 5000);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println(data);
            System.out.println("\n[SUCCESS] Data sent to server.");

        } catch (Exception e) {
            System.out.println("[ERROR] Unable to connect to server.");
        }
    }
}