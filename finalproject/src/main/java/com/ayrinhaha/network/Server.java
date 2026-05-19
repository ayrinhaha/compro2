package com.ayrinhaha.network;

import java.io.*;
import java.net.*;

public class Server {

    private static final String LOG_FOLDER = "server_logs";

    public void startServer() {

        try (ServerSocket server = new ServerSocket(5000)) {

            System.out.println("[SUCCESS] Server started on port 5000.");

            while (true) {

                Socket socket = server.accept();

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                StringBuilder data = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    data.append(line);
                }

                String payload = data.toString();

                System.out.println("\n==================================================");
                System.out.println("              RECEIVED TRANSACTION");
                System.out.println("==================================================");
                System.out.println(payload);

                String username = extractUsername(payload);

                String file = LOG_FOLDER + "/" + username + "_logs.json";

                save(file, payload);

                socket.close();
            }

        } catch (Exception e) {
            System.out.println("[ERROR] Server error.");
        }
    }

    /**
     * Saves received transaction data into
     * the user's dedicated server log file.
     *
     * @param file destination file
     * @param data transaction JSON
     */
    /**
     * Saves received transaction data into
     * the user's dedicated server log file.
     *
     * Stored as a JSON array.
     *
     * @param file destination file
     * @param data transaction JSON
     */
    private void save(String file, String data) {

        try {

            File folder = new File(LOG_FOLDER);

            if (!folder.exists()) {

                folder.mkdirs();
            }

            File logFile = new File(file);

            if (!logFile.exists()
                    || logFile.length() == 0) {

                BufferedWriter bw = new BufferedWriter(
                        new FileWriter(logFile));

                bw.write("[\n");
                bw.write(data);
                bw.write("\n]");

                bw.close();

                System.out.println("[SUCCESS] Transaction saved to server log.");

                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(logFile));
            StringBuilder sb = new StringBuilder();

            String line;

            while ((line = br.readLine()) != null) {

                sb.append(line).append("\n");
            }

            br.close();

            String content = sb.toString().trim();

            content = content.substring(0, content.length() - 1);

            BufferedWriter bw = new BufferedWriter(new FileWriter(logFile));
            bw.write(content);

            if (!content.endsWith("[")) {

                bw.write(",");
            }

            bw.write("\n");
            bw.write(data);
            bw.write("\n]");

            bw.close();

            System.out.println("[SUCCESS] Transaction saved to server log.");

        } catch (Exception e) {
            System.out.println("[ERROR] Failed to save transaction log.");
            e.printStackTrace();
        }
    }

    /**
     * Extracts username from incoming JSON payload.
     *
     * @param json incoming transaction JSON
     * @return extracted username
     */
    private String extractUsername(String json) {

        try {
            int start = json.indexOf("\"username\":\"") + 12;
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        } catch (Exception e) {
            return "unknown_user";
        }
    }

    public static void main(String[] args) {
        new Server().startServer();
    }
}