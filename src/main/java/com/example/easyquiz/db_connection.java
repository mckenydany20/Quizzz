package com.example.easyquiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class db_connection {

    private static final String URL = "jdbc:mysql://localhost:3306/ict308";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection to the database established successfully.");
            return connection;
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database.");
            throw e;
        }
    }

    /*public static void main(String[] args) {
        try {
            DataBaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}
