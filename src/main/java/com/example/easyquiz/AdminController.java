package com.example.easyquiz;

import javafx.collections.FXCollections;
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

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminController {

    @FXML
    private TableView<Userdata> usersTable;
    @FXML
    private TableColumn<Userdata, String> usernameColumn;
    @FXML
    private TableColumn<Userdata, String> roleColumn;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private void initialize() {
        roleComboBox.setItems(FXCollections.observableArrayList("Admin", "Utilisateur"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        loadUsers();
    }

    private void loadUsers() {
        List<Userdata> users = loadUsersFromDatabase();
        usersTable.getItems().setAll(users);
    }

    @FXML
    private void handleAddUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();
        addUserToDatabase(new Userdata(username, password, role));
        loadUsers();
    }

    @FXML
    private void handleUpdateUser() {
        Userdata selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            selectedUser.setUsername(usernameField.getText());
            selectedUser.setPassword(passwordField.getText());
            selectedUser.setRole(roleComboBox.getValue());
            updateUserInDatabase(selectedUser);
            loadUsers();
        }
    }

    @FXML
    private void handleDeleteUser() {
        Userdata selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            deleteUserFromDatabase(selectedUser);
            loadUsers();
        }
    }

    private List<Userdata> loadUsersFromDatabase() {
        List<Userdata> users = new ArrayList<>();
        try (Connection connection = db_connection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new Userdata(rs.getString("username"), rs.getString("password"), rs.getString("role")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private void addUserToDatabase(Userdata user) {
        try (Connection connection = db_connection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO users (username, password, role) VALUES (?, ?, ?)"
            );
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateUserInDatabase(Userdata user) {
        try (Connection connection = db_connection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE users SET username = ?, password = ?, role = ? WHERE username = ?"
            );
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteUserFromDatabase(Userdata user) {
        try (Connection connection = db_connection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM users WHERE username = ?"
            );
            stmt.setString(1, user.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToQuestionManagement(ActionEvent event) {
        try {
            Stage thisStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            thisStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image("logo.png"));
            stage.setTitle("Gestion des Questions");
            stage.initStyle(StageStyle.DECORATED);
            scene.setFill(Color.TRANSPARENT);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
