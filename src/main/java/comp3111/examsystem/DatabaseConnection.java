package comp3111.examsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static String url = "jdbc:sqlite:./sqlite.db";

    public static void setUrl(String newUrl) {
        url = newUrl;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
