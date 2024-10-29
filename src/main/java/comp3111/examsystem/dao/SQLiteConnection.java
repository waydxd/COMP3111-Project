package comp3111.examsystem.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
    private static final String DB_URL = "jdbc:sqlite:database.db";
    private static Connection connection;

    // Private constructor to prevent instantiation
    private SQLiteConnection() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Register JDBC driver
                Class.forName("org.sqlite.JDBC");
                // Create connection
                connection = DriverManager.getConnection(DB_URL);
                // Set foreign keys support
                connection.createStatement().execute("PRAGMA foreign_keys = ON");
            }
            return connection;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQLite JDBC Driver not found", e);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error closing the connection", e);
        }
    }

    // Method to check if connection is valid
    public static boolean isConnectionValid() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    // Method to reset connection (useful for testing)
    public static void resetConnection() {
        closeConnection();
        connection = null;
    }
}