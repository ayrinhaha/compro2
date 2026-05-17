package com.ayrinhaha.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final String EXPENSE_FILE =
            "server_data.json";

    private static final String TUITION_FILE =
            "tuition_server.json";

    public void startServer() {

        try (ServerSocket server =
                     new ServerSocket(5000)) {

            System.out.println("[SERVER STARTED]");

            while (true) {

                Socket socket =
                        server.accept();

                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()));

                StringBuilder data =
                        new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {
                    data.append(line).append("\n");
                }

                String payload =
                        data.toString();

                System.out.println("\n[RECEIVED]");
                System.out.println(payload);

                // =========================
                // ROUTING LOGIC
                // =========================

                if (payload.contains("\"type\":\"TUITION\"")) {

                    saveToFile(TUITION_FILE, payload);

                } else {

                    saveToFile(EXPENSE_FILE, payload);
                }

                socket.close();
            }

        } catch (Exception e) {
            System.out.println("Server error.");
        }
    }

    private void saveToFile(String file,
                            String data) {

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(file, true))) {

            writer.write(data);
            writer.newLine();

            System.out.println(
                    "[SAVED TO " + file + "]");

        } catch (IOException e) {

            System.out.println("File error.");
        }
    }

    public static void main(String[] args) {

        new Server().startServer();
    }
}