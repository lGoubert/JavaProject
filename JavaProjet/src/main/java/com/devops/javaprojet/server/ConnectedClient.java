package com.devops.javaprojet.server;
import com.devops.javaprojet.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class ConnectedClient implements Runnable {
    private static int idCounter;
    private int id;
    private Server server;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int numClient) {
        id = numClient;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

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
            //e.printStackTrace();
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
                        ConnectedClient client = server.getClientById(id);
                        switch (mess.getType()){
                            case 101: //Message public
                                mess.setSender(client.getName());
                                mess.setType(201);
                                mess.setContent(mess.getContent());
                                server.broadcastMessage(mess, id);
                                System.out.println(mess.getContent());
                                break;
                            case 102: //Login
                                System.out.println(mess.getContent());
                                String[] messArrayLogin = mess.getContent().split(Pattern.quote("|"), 2);
                                String usernameLogin = messArrayLogin[0];
                                String passwordLogin = messArrayLogin[1];
                                try{
                                    Message message = MainServer.api.Login(usernameLogin, passwordLogin);
                                    server.sendMessageToClientId(message, id);
                                    if(message.getType() == 203){
                                        client.setName(usernameLogin);
                                        server.announceConnection(client);
                                    }
                                }catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 103: //Register
                                System.out.println(mess.getContent());
                                String[] messArrayRegister = mess.getContent().split(Pattern.quote("|"), 2);
                                String usernameRegister = messArrayRegister[0];
                                String passwordRegister = messArrayRegister[1];
                                try{
                                    Message message = MainServer.api.Register(usernameRegister, passwordRegister);
                                    server.sendMessageToClientId(message, id);
                                    if(message.getType() == 205){
                                        client.setName(usernameRegister);
                                        server.announceConnection(client);
                                    }
                                }catch (SQLException e) {
                                    throw new RuntimeException(e);
                                } catch (NoSuchAlgorithmException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 104: //Response
                                /*To DO
                                    Recupere le message content et le comparer avec le nom du pays actuel
                                    revoyer une image en 206 si la réponse est trouver (broadcast)
                                 */
                                System.out.println(mess.getContent());
                                break;

                            default:
                                break;
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
                //e.printStackTrace();
                isActive = false;
                System.out.println("Un client viens de se déconnecter");
            }

        }
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

