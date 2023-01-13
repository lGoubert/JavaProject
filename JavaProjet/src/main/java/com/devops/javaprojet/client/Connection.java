package com.devops.javaprojet.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection implements Runnable {
    private Client client;
    private Socket socket;
    private BufferedReader in;

    public PrintWriter getOut() {
        return out;
    }

    private PrintWriter out;
    private BufferedReader stdIn;

    public Connection(Client client, Socket socket) {
        this.client = client;
        this.socket = socket;
    }

    public void run() {
        try {
            System.out.println("Connexion établie avec " + socket.getRemoteSocketAddress());

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            String userInput;

            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("Réponse du serveur : " + in.readLine());
                if (userInput.equals("Bye."))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
