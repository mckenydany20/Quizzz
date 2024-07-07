package com.example.easyquiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class RegistrationController {

    @FXML
    private Label errorLabel;
    @FXML
    private Button registerbtn;
    @FXML
    private TextField usernameFieldRegister;

    @FXML
    private PasswordField passwordFieldRegister;

    @FXML
    private PasswordField confirmPasswordFieldRegister;

    @FXML
    private void initialize() {
        registerbtn.setOnAction(event -> handleRegister(event));
    }
    @FXML
    public void handleRegister(ActionEvent event) {
        String username = usernameFieldRegister.getText();
        String password = passwordFieldRegister.getText();
        String confirmPassword = confirmPasswordFieldRegister.getText();
        errorLabel.setText("");

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("Veuillez remplir tous les champs !");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Les mots de passe ne correspondent pas.");
            return;
        }

        if (UserdataController.registerUser(username, password, "Admin")) {
            System.out.println("Inscription réussie !");
            // Fermer la fenêtre d'inscription
            Stage registrationStage = (Stage) usernameFieldRegister.getScene().getWindow();
            registrationStage.close();

            // Rediriger vers la page de connexion (ou commencer le quiz directement)
            openLoginPage();
        } else {
            showError("Échec de l'inscription.");
        }
    }

    private void openLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("level.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            homeStage.setTitle(" Quizzz ");
            homeStage.initStyle(StageStyle.DECORATED);

            homeStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showError(String errorMessage) {
        errorLabel.setText(errorMessage);
    }
}
