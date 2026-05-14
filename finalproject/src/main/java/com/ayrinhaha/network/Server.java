
package com.ayrinhaha.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void startServer() {
        try (ServerSocket server = new ServerSocket(5000)) {

            System.out.println("[Server started] Waiting for client...");

            Socket socket = server.accept();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            String message = in.readLine();
            System.out.println("[Received]: " + message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}