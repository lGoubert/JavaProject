package com.devops.javaprojet.server;

import com.devops.javaprojet.common.Message;
import com.devops.javaprojet.server.api.Api;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private int port;

    public Api getApi() {
        return api;
    }

    private Api api;
    private List<ConnectedClient> clients;

    public int getNumClients() {
        return clients.size();
    }

    public int getPort() {
        return port;
    }

    public Server(int port, Api api) {
        this.api = api;
        this.port = port;
        this.clients = new ArrayList<ConnectedClient>();
        Thread threadConnection = new Thread(new Connection(this));
        threadConnection.start();
    }

    public void addClient(ConnectedClient newClient) {
        this.clients.add(newClient);
        broadcastMessage(new Message(String.valueOf(newClient.getId()), newClient.getId() + " vient de se connecter",1),
                newClient.getId());
    }

    public void broadcastMessage(Message mess, int id) {
        for (ConnectedClient client : clients) {
            if (client.getId() != id) {
                client.sendMessage(mess);
            }
        }
    }
}

