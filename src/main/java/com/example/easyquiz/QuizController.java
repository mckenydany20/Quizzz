package com.example.easyquiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class QuizController {

    static String playerName;

    private String correctAnswerText;


    @FXML
    public Label question;

    @FXML
    public Button opt1, opt2, opt3, opt4;
    private static String difficulty;

    static int counter = 0;

    static int totalQuestions;
    static int correct = 0;


    // Déclarez une liste pour stocker les questions
    private List<String> questions = new ArrayList<>();

    @FXML
    private void initialize() {

        loadQuestions();
    }

    private void loadQuestions() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/questions/" + difficulty + ".txt"));
            String line;
            questions.clear();
            while ((line = reader.readLine()) != null) {
                questions.add(line);
            }

            totalQuestions = questions.size();
            Collections.shuffle(questions);
            if (counter < totalQuestions) {
                loadQuestion(counter);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadQuestion(int questionIndex) {
        if (questionIndex >= totalQuestions) return;
        String line = questions.get(questionIndex);
        String[] parts = line.split("/");
        String questionNumber = (questionIndex + 1) + ". ";
        question.setText(questionNumber + parts[0]);

        List<String> answers = new ArrayList<>();
        answers.add(parts[1]);
        answers.add(parts[2]);
        answers.add(parts[3]);
        answers.add(parts[4]);

        opt1.setText(answers.get(0));
        opt2.setText(answers.get(1));
        opt3.setText(answers.get(2));
        opt4.setText(answers.get(3));

        correctAnswerText = parts[5];
    }

    @FXML
    public void optionClicked(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String selectedAnswer = clickedButton.getText();
        boolean isCorrect = selectedAnswer.equals(correctAnswerText);
        if (isCorrect) {
            correct++;
            counter++;
            if (counter < totalQuestions) {
                loadQuestion(counter);
            } else {
                showResult();
            }
        } else {
            showResult();
        }
    }

    private void showResult() {
        try {
            Stage thisStage = (Stage) question.getScene().getWindow();
            thisStage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("result.fxml"));
            Scene scene = new Scene(loader.load());
            ResultController resultController = loader.getController();
            resultController.setPlayerName(playerName);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image("logo.png"));
            stage.setTitle("Quizz IT / joueur : " + playerName);
            stage.initStyle(StageStyle.DECORATED);
            scene.setFill(Color.TRANSPARENT);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public static void setDifficulty(String difficulty) {
        QuizController.difficulty = difficulty;
    }

}