package org.example.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionData {
    private static Connection connection;
    private static final String url = "jdbc:postgresql://localhost:5432/Questionarium";
    private static final String user = "postgres";
    private static final String password = "qwerty12";

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, password);
            }
            System.out.println("Connected");
            return connection;
        } catch (SQLException e){
            throw new RuntimeException("Can't connection", e);
        }

    }
}
