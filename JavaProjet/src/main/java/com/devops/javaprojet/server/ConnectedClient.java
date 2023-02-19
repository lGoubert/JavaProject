package com.devops.javaprojet.server;
import com.devops.javaprojet.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.MalformedInputException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
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
                                System.out.println(mess.getSender() + ": " + mess.getContent());
                                MainServer.api.LogMessage(mess.getSender(), mess.getContent());
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

                                Message messageSendActualFlag = new Message("server", MainServer.flagMap.get("flag"), 206);
                                server.broadcastMessage(messageSendActualFlag, -1);
                                System.out.println("Envoi du drapeau actuel.");
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
                                String clientAnswer = Normalizer.normalize(mess.getContent(), Normalizer.Form.NFD)
                                    .replaceAll("\\p{M}", "")
                                    .replaceAll("[^\\p{ASCII}]", "");

                                System.out.println(client.getName() + " a proposé " + clientAnswer + "(corrigé en : " + clientAnswer + ")");
                                Message messageProposition = new Message("PROPOSITION",client.getName() + " a proposé " +  mess.getContent(),201);
                                server.broadcastMessage(messageProposition, -1);

                                if (clientAnswer.toLowerCase().equals(MainServer.flagMap.get("country").toLowerCase())){
                                    int score = Integer.parseInt(MainServer.flagMap.get("difficulty"));

                                    Message messageGoodAnswser = new Message("BONNE REPONSE",client.getName() + " a trouvé la bonne réponse et marque " +  score + " point(s).",201);
                                    server.broadcastMessage(messageGoodAnswser, -1);
                                    System.out.println(mess.getContent());

                                    try {
                                        MainServer.api.AddScore(MainServer.api.getIdUser(client.getName()), score);
                                        MainServer.flagMap = MainServer.api.GetInfoCountryRandom();
                                        System.out.println("Nouvelle réponse: " + MainServer.flagMap.get("country"));
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }

                                    Message messageSendFlag = new Message("server",MainServer.flagMap.get("flag"),206);
                                    server.broadcastMessage(messageSendFlag, -1);
                                    System.out.println("Envoie du nouveau drapeau.");
                                }
                                break;
                            case 105: //Scoreboard
                                ArrayList<String> scores = MainServer.api.GetScoreboard();
                                for (String score: scores) {
                                    System.out.println(score);
                                    Message message = new Message("", score, 207);
                                    server.sendMessageToClientId(message, id);
                                }
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
                System.out.println("Un client vient de se déconnecter");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

