
package com.ayrinhaha.network;

import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public void send(String data) {
        try (Socket socket = new Socket("localhost", 8000)) {

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.println(data);

            System.out.println("[Sent]: " + data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}