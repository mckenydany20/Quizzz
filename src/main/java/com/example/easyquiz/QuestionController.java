package com.example.easyquiz;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionController {

    @FXML
    private TableView<Question> questionsTable;
    @FXML
    private TableColumn<Question, String> questionColumn;
    @FXML
    private TableColumn<Question, String> difficultyColumn;
    @FXML
    private TextArea questionField;
    @FXML
    private ComboBox<String> difficultyComboBox;

    private static final String EASY_FILE = "easy.txt";
    private static final String NORMAL_FILE = "normal.txt";
    private static final String HARD_FILE = "hard.txt";

    private String currentDifficultyFile;

    @FXML
    private void initialize() {
        difficultyComboBox.setItems(FXCollections.observableArrayList("Facile", "Normal", "Difficile"));
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));

        difficultyComboBox.setOnAction(event -> loadQuestions());
    }

    @FXML
    private void loadQuestions() {
        String difficulty = difficultyComboBox.getValue();
        switch (difficulty) {
            case "Facile":
                currentDifficultyFile = EASY_FILE;
                break;
            case "Normal":
                currentDifficultyFile = NORMAL_FILE;
                break;
            case "Difficile":
                currentDifficultyFile = HARD_FILE;
                break;
        }
        List<Question> questions = loadQuestionsFromFile();
        questionsTable.getItems().setAll(questions);
    }

    @FXML
    private void handleAdd() {
        String questionText = questionField.getText();
        String difficulty = difficultyComboBox.getValue();
        Question newQuestion = new Question(questionText, difficulty);
        addQuestionToFile(newQuestion);
        loadQuestions();
    }

    @FXML
    private void handleUpdate() {
        Question selectedQuestion = questionsTable.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            selectedQuestion.setQuestion(questionField.getText());
            updateQuestionInFile(selectedQuestion);
            loadQuestions();
        }
    }

    @FXML
    private void handleDelete() {
        Question selectedQuestion = questionsTable.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            deleteQuestionFromFile(selectedQuestion);
            loadQuestions();
        }
    }

    private List<Question> loadQuestionsFromFile() {
        List<Question> questions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/questions/" + currentDifficultyFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                questions.add(new Question(line, difficultyComboBox.getValue()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }

    private void addQuestionToFile(Question question) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentDifficultyFile, true))) {
            writer.write(question.getQuestion());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateQuestionInFile(Question question) {
        List<Question> questions = loadQuestionsFromFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentDifficultyFile))) {
            for (Question q : questions) {
                if (q.equals(question)) {
                    writer.write(question.getQuestion());
                } else {
                    writer.write(q.getQuestion());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteQuestionFromFile(Question question) {
        List<Question> questions = loadQuestionsFromFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentDifficultyFile))) {
            for (Question q : questions) {
                if (!q.equals(question)) {
                    writer.write(q.getQuestion());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToUserManagement(ActionEvent event) {
        try {
            Stage thisStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            thisStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_user.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image("logo.png"));
            stage.setTitle("Gestion des Utilisateurs");
            stage.initStyle(StageStyle.DECORATED);
            scene.setFill(Color.TRANSPARENT);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
