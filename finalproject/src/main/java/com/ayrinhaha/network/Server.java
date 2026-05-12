
package com.ayrinhaha.network;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void startServer() {

        try {

            // TODO:
            // Create server socket

            ServerSocket serverSocket = new ServerSocket(5000);

            // TODO:
            // Wait for client connection

            Socket socket = serverSocket.accept();

            // TODO:
            // Receive client data

        } catch (Exception e) {

            // TODO:
            // Handle server errors

        }
    }
}
