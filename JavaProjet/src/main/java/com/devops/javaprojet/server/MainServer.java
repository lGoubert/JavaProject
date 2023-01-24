package com.devops.javaprojet.server;

import java.net.*;
import java.io.*;

public class MainServer {
    public static void main(String[] args) throws IOException {
        Integer port = 1234;
        Server server = new Server(port);
        if (server != null) {
            System.out.println("Server connected.");
        }
    }
}
