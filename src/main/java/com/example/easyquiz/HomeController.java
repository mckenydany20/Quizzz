package com.example.easyquiz;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeController {

    @FXML
    private Button playquizbtn;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Label errorLabel;

    @FXML
    private ImageView muteImageView;

    private Clip mediaPlayer;
    private boolean isMuted = false;

    private static boolean isMusicPlaying = false;

    public ExecutorService executorService = Executors.newSingleThreadExecutor();

    @FXML
    private void initialize() {
        roleComboBox.setItems(FXCollections.observableArrayList("Admin", "Utilisateur"));
        playquizbtn.setOnAction(event -> handlePlayQuiz(event));
        if(mediaPlayer == null){
            executorService.execute(this::PlayMusicBackground);
        }

    }

    public void PlayMusicBackground(){
        try {
            if (!isMusicPlaying) {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(getClass().getResourceAsStream("/com/example/easyquiz/sounds/background.wav"));
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
                mediaPlayer = AudioSystem.getClip();
                mediaPlayer.open(audioInputStream);
                mediaPlayer.loop(Clip.LOOP_CONTINUOUSLY);
                mediaPlayer.start();
                isMusicPlaying = true;
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        muteImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/easyquiz/images/sound.jpg"))));
    }

    @FXML
    private void handlePlayQuiz(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();
        errorLabel.setText("");

        if (username.isEmpty() || password.isEmpty() || role == null) {
            showError("Veuillez remplir tous les champs !");
            return;
        }

        if (UserdataController.validateUser(username, password, role)) {
            if ("Admin".equals(role)) {
                navigateToAdminUsers(event);
            } else {
                navigateToDifficultySelection(event, username);
            }
        } else {
            showError("Identifiants incorrects. Veuillez réessayer ou vous inscrire.");
        }
    }

    private void navigateToDifficultySelection(ActionEvent event, String username) {
        try {
            Stage thisStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            thisStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("level.fxml"));
            Scene scene = new Scene(loader.load());
            levelController levelController = loader.getController();
            levelController.setPlayerName(username);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image("logo.png"));
            stage.setTitle("Sélection de la difficulté du joueur : " + username);
            stage.initStyle(StageStyle.DECORATED);
            scene.setFill(Color.TRANSPARENT);
            stage.setOnCloseRequest(e -> stopMusic());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToAdminUsers(ActionEvent event) {
        try {

            Stage thisStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            thisStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_user.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image("logo.png"));
            stage.setTitle("Admin - User Management");
            stage.initStyle(StageStyle.DECORATED);
            scene.setFill(Color.TRANSPARENT);
            stage.setOnCloseRequest(e -> stopMusic());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToRegistration(ActionEvent event) {
        try {
            Stage thisStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            thisStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("registration.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image("logo.png"));
            stage.setTitle("Inscription");
            stage.initStyle(StageStyle.DECORATED);
            scene.setFill(Color.TRANSPARENT);
            stage.setOnCloseRequest(e -> stopMusic());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void toggleMute(ActionEvent event) {
        isMuted = !isMuted;
        mediaPlayer.setMicrosecondPosition(0);
        if (isMuted) {
            mediaPlayer.stop();
            muteImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/easyquiz/images/muted.jpg")));
        } else {
            mediaPlayer.start();
            muteImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/easyquiz/images/sound.jpg")));
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            isMusicPlaying = false;
        }
    }

    private void showError(String errorMessage) {
        errorLabel.setText(errorMessage);
    }
}
