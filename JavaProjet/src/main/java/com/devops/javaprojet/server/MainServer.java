package com.devops.javaprojet.server;
import com.devops.javaprojet.common.Message;
import com.devops.javaprojet.server.api.Api;
import com.devops.javaprojet.server.database.Database;
import com.devops.javaprojet.server.database.DatabaseDAO;


import java.net.*;
import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MainServer {
    public static Api api;
    public static Map<String,String> flagMap;

    private static final int TIMER_DELAY = 30_000; // 30 secondes en millisecondes
    private static Timer timer;
    private static TimerTask timerTask;
    private static Server server;
    private static ArrayList<String> endTimerMessages;
    private static ArrayList<String> lastMessages;



    public static void gameTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                timerEnd();
            }
        };
        timer.schedule(timerTask, TIMER_DELAY); // Démarrer le timer
    }
    public static void resetTimer() {
        timerTask.cancel(); // Annuler la tâche courante
        timerTask = new TimerTask() {
            @Override
            public void run() {
                timerEnd();
            }
        };
        timer.schedule(timerTask, TIMER_DELAY); // Démarrer une nouvelle tâche
    }

    public static void timerEnd(){
        try {
            Message messageTimerEnd = new Message("TIMER", getRandomMessage(),201);
            server.broadcastMessage(messageTimerEnd, -1);
            Message messageTimerEndAnswer = new Message("La réponse était ", flagMap.get("country").substring(0, 1).toUpperCase() + flagMap.get("country").substring(1),201);
            server.broadcastMessage(messageTimerEndAnswer, -1);
            flagMap = api.GetInfoCountryRandom();
            Message messageSendFlag = new Message("server", flagMap.get("flag"),206);
            server.broadcastMessage(messageSendFlag, -1);
            System.out.println("Nouvelle réponse: " + flagMap.get("country"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("30 secondes se sont écoulées.");
        // Ajoutez ici le code que vous voulez exécuter après 30 secondes
        resetTimer(); // Réinitialiser le timer
    }

    public static void main(String[] args) throws IOException, SQLException, NoSuchAlgorithmException {
        /* Connection a la base de données */
        Database database = new Database("jdbc:mariadb://45.155.169.116:6006/javaprojet","javaprojet","devops");
        DatabaseDAO dataDAO = new DatabaseDAO(database.getMariadbConnection());
        api = new Api(dataDAO);
        
        ResultSet result = dataDAO.GetAllCountries();
        while (result.next()) {
            var country = result.getString("country");
            System.out.println(country);
        }

        try {
            flagMap = api.GetInfoCountryRandom();
            System.out.println("Nouvelle réponse: " + flagMap.get("country"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        /* */
        endTimerMessages = setEndTimerMessages();
        lastMessages = new ArrayList<String>();
        /* Déclenche le time de 30 secondes */
        gameTimer();

        Integer port = 1234;
        server = new Server(port);
        if (server != null) {
            System.out.println("Server connected.");
        }
    }

    public static ArrayList<String> setEndTimerMessages(){
        ArrayList<String> messagesList = new ArrayList<>();
        messagesList.add("Trop tard !");
        messagesList.add("Essayez encore.");
        messagesList.add("Encore !");
        messagesList.add("Persévérez !");
        messagesList.add("Next time !");
        messagesList.add("Loupé !");
        messagesList.add("Réessayer !");
        messagesList.add("Perdu !");
        messagesList.add("Suivant !");
        messagesList.add("Raté !");
        messagesList.add("Try again !");
        messagesList.add("Trop tard, essayez encore !");
        messagesList.add("Le temps est écoulé...");
        messagesList.add("Dommage, vous êtes si près de la réponse !");
        messagesList.add("You can do it !");
        messagesList.add("Encore un effort, vous y êtes presque !");
        messagesList.add("On dirait que vous avez besoin d'un peu plus de pratique !");
        messagesList.add("Vous avez essayé, mais le drapeau a gagné !");
        messagesList.add("Pas de chance, peut-être la prochaine fois !");
        messagesList.add("Temps écoulé, keep going !");
        messagesList.add("Le prochain drapeau vous attend, ne vous découragez pas !");
        messagesList.add("C'est presque ça, continuez !");
        messagesList.add("Ne perdez pas espoir, vous allez y arriver !");
        messagesList.add("Un autre essai, vous pouvez le faire !");
        messagesList.add("Oups, vous avez l'air perdu !");
        messagesList.add("Dommage, on dirait que vous ne voyagez pas beaucoup !");
        messagesList.add("Encore loupé, allez-vous abandonner ?");
        messagesList.add("Vous êtes chaud, mais les drapeaux sont encore plus chauds !");
        messagesList.add("Avez-vous déjà envisagé un métier en dehors de la géographie ?");
        messagesList.add("Je suis sûr que le prochain drapeau sera plus facile... ou pas !");
        return messagesList;
    }

    public static String getRandomMessage() {
        Random rand = new Random();
        int index = rand.nextInt(endTimerMessages.size());
        String message = endTimerMessages.get(index);

        // Vérifier si la nouvelle valeur est contenue dans la liste des 10 dernières valeurs
        while (lastMessages.size() > 0 && lastMessages.contains(message)) {
            index = rand.nextInt(endTimerMessages.size());
            message = endTimerMessages.get(index);
        }

        // Ajouter la nouvelle valeur à la liste des 10 dernières valeurs
        lastMessages.add(message);
        if (lastMessages.size() > 10) {
            lastMessages.remove(0);
        }

        return message;
    }
}
