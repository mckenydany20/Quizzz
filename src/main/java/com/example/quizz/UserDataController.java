package com.example.quizz;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class UserDataController {

    @FXML
    private TextField username;

    @FXML
    private TextField email;

    @FXML
    private TextField password; // Assurez-vous que cette variable est annotée avec @FXML

    @FXML
    private Button playquizbtn;

    @FXML
    public void initialize() {
        playquizbtn.setOnAction(event -> {
            String user = username.getText();
            String mail = email.getText();
            String pass = password.getText();

            db_connect userData = new db_connect();
            userData.insertUser(user, mail, pass);
        });
    }
}
