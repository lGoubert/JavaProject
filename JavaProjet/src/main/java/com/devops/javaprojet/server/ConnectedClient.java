package com.devops.javaprojet.server;
import com.devops.javaprojet.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectedClient implements Runnable {
    private static int idCounter;
    private int id;
    private Server server;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public int getId() {
        return id;
    }

    public void setId(int numClient) {
        id = numClient;
    }

    public ConnectedClient(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        id = idCounter;
        idCounter++;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Nouvelle connexion, id = " + id);
    }

    public void sendMessage(Message mess) {
        try {
            this.out.writeObject(mess);
            this.out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        boolean isActive = true;
        while (isActive) {
            Message mess;
            try {
                if (in != null) {
                    if ((mess = (Message) in.readObject()) != null) {
                        if (mess.getType() == 1){
                            mess.setSender(String.valueOf(id));
                            server.broadcastMessage(mess, id);
                            System.out.println(mess.getContent());
                        } else if (mess.getType() == 2) {
                            //request ou réponse
                        }
                    } else {
                        isActive = false;
                    }
                } else {
                    isActive = false;
                }
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                isActive = false;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                isActive = false;
            }

        }
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

