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

        try (
                Socket socket = new Socket("localhost", 5000);
                PrintWriter writer = new PrintWriter(
                        socket.getOutputStream(),
                        true);) {

            writer.println(data);

            writer.flush();

            socket.shutdownOutput();

            System.out.println(
                    "[SUCCESS] Data sent to server.");

        } catch (Exception e) {

            System.out.println(
                    "[ERROR] Failed to send data.");

            e.printStackTrace();
        }
    }
}