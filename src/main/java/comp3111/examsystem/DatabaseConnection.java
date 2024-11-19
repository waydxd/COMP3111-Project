package comp3111.examsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static String url = "jdbc:sqlite:./sqlite.db";

    /**
     * @param newUrl the new url to set for the database connection
     *               <p>
     *               This function is mainly for testing service and DAO methods
     *               </p>
     */
    public static void setUrl(String newUrl) {
        url = newUrl;
    }
    /**
     * @return the connection to the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
