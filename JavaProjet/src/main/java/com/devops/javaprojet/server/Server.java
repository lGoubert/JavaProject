package com.devops.javaprojet.server;

import com.devops.javaprojet.common.Message;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private int port;
    private List<ConnectedClient> clients;

    public int getNumClients() {
        return clients.size();
    }

    public int getPort() {
        return port;
    }

    public ConnectedClient getClientById(int id){ return clients.get(id); }

    public Server(int port) {
        this.port = port;
        this.clients = new ArrayList<ConnectedClient>();
        Thread threadConnection = new Thread(new Connection(this));
        threadConnection.start();
    }

    public void addClient(ConnectedClient newClient) {
        this.clients.add(newClient);
    }


    public void announceConnection(ConnectedClient client){
        broadcastMessage(new Message(client.getName(), " vient de se connecter",201),
                client.getId());
    }
    public void broadcastMessage(Message mess, int id) {
        for (ConnectedClient client : clients) {
            if (client.getId() != id) {
                client.sendMessage(mess);
            }
        }
    }

    public void sendMessageToClientId(Message mess, int id){
        for (ConnectedClient client : clients) {
            if (client.getId() == id) {
                client.sendMessage(mess);
            }
        }
    }
}

