package com.example.easyquiz;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserdataController {
    private static String loggedInUsername;

    public static boolean validateUser(String username, String password, String role) {
        try (Connection connection = db_connection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM user WHERE username = ? AND password = ? AND role = ?"
            );
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                loggedInUsername = username;
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean registerUser(String username, String password, String role) {
        try (Connection connection = db_connection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO users (username, password, role) VALUES (?, ?, ?)"
            );
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getLoggedInUsername() {
        return loggedInUsername;
    }
}
