package com.ayrines;

import com.ayrines.services.NetworkService;

public class Main {
    public static void main(String[] args) {

        NetworkService ns = new NetworkService(null, 0);
        String host = "jsonplaceholder.typicode.com";
        int port = 80;
        String path = "/todos/1";

        String response = ns.fetchData(host, port, path);

        System.out.println(response);





    }
}