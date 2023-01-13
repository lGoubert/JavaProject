package com.devops.javaprojet.server;

import java.net.*;
import java.io.*;

public class MainServer {
    public static void main(String[] args) throws IOException {
        Integer port = 5000;
        Server server = new Server(port);
        if (server != null) {
            System.out.println("Server connected.");
        }
    }

    private static void printUsage() {
        System.out.println("java server.Server <port>");
        System.out.println("\t<port>: server's port");
    }
}
