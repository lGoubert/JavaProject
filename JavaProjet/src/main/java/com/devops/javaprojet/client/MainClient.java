package com.devops.javaprojet.client;

import java.net.*;
import java.io.*;

public class MainClient {
    public static void main(String[] args) throws IOException {
        String address = "localhost";
        Integer port = 5000;
        Client c = new Client(address, port);
    }
}
