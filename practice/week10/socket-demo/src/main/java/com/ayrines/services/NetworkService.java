package com.ayrines.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetworkService {

    private String host;
    private int port;

    public NetworkService(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String fetchData(String host, int port, String path) {
        StringBuilder response = new StringBuilder();

        try (Socket socket = new Socket(host, port);
                PrintWriter requestWriter = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader responseReader = new BufferedReader(new InputStreamReader((socket.getInputStream())))) {
            System.out.print("Connected to server...");

            // send an HTTP request: Method, Patch, Protocol version, Host header, blank
            // line

            requestWriter.print("GET" + path + "HTTP/1.1");
            requestWriter.print("Host: " + host);
            requestWriter.print("User-Agent: Java/SocketDemo");
            requestWriter.print("Accept: Application/json");
            requestWriter.print("Connection: close");
            requestWriter.print("\r\n"); // end req header

            System.out.println("\n --- HTTP Response Headers ---");

            String line;
            boolean isBody = false;

            while ((line = responseReader.readLine()) != null) {
                if (line.isEmpty() && !isBody) {
                    System.out.println(" [Header] " + line);
                    isBody = true;
                    continue;
                }

                if (isBody)
                    response.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }

    public void sendData(String path, String content) {

    }
}
