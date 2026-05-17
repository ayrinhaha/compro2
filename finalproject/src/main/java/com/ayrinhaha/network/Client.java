package com.ayrinhaha.network;

import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public void send(String data) {

        try (Socket socket = new Socket("localhost", 5000);

                PrintWriter out = new PrintWriter(
                        socket.getOutputStream(),
                        true)) {

            // SEND WHOLE JSON STRING
            out.println(data);

            System.out.println(
                    "\n[DATA SENT TO SERVER]");

        } catch (Exception e) {

            System.out.println(
                    "Client connection error.");
        }
    }
}