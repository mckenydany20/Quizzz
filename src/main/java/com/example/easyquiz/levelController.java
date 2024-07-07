package com.example.easyquiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class levelController {

    private String playerName;

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @FXML
    private void selectEasy(ActionEvent event) {
        QuizController.setDifficulty("easy");
        startQuiz(event,playerName, "easy");
    }

    @FXML
    private void selectNormal(ActionEvent event) {
        QuizController.setDifficulty("normal");
        startQuiz(event, playerName, "normal");
    }

    @FXML
    private void selectHard(ActionEvent event) {
        QuizController.setDifficulty("hard");
        startQuiz(event, playerName, "hard");
    }

    @FXML
    private void goToHome(ActionEvent event) {
        try {
            HomeController homeController = new HomeController();
            homeController.stopMusic();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.setFill(Color.TRANSPARENT);

            Stage homeStage = new Stage();
            homeStage.setScene(scene);
            homeStage.setResizable(false);
            homeStage.getIcons().add(new Image("logo.png"));
            homeStage.setTitle("Easy Quiz IT");
            homeStage.initStyle(StageStyle.DECORATED);

            homeStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startQuiz(ActionEvent event, String username, String difficulty) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("quiz.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.setFill(Color.TRANSPARENT);
            Stage Diffstage = new Stage();
            Diffstage.setScene(scene);
            Diffstage.setResizable(false);
            Diffstage.getIcons().add(new Image("logo.png"));
            Diffstage.setTitle("Quizz IT / joueur : "+username+" | difficulté : "+difficulty);
            QuizController quizController = fxmlLoader.getController();
            quizController.setPlayerName(username);
            Diffstage.initStyle(StageStyle.DECORATED);
            Diffstage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
