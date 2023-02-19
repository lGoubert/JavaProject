package com.devops.javaprojet.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScoreboardController {

    @FXML
    Button backButton;

    @FXML
    TableView tableScore = new TableView();

    @FXML
    TableColumn<Score, String> scoreName =  new TableColumn<>("Nom");

    @FXML
    TableColumn<Score, String> scoreValue = new TableColumn<>("Score");

    @FXML
    public void initialize() {
            scoreName.setCellValueFactory(new PropertyValueFactory<>("name"));
            scoreValue.setCellValueFactory(new PropertyValueFactory<>("value"));
    }

    @FXML
    public void backButtonHandle() throws IOException {
        GameApplication.LoadMainScene();
    }

    public void addScores(ArrayList<Score> scores){
        for (Score score: scores) {
            tableScore.getItems().add(new Score(score.getName(), score.getValue()));
        }
    }

    public void addScore(Score score){
        tableScore.getItems().add(score);
    }
}
