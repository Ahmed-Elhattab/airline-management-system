package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides a utility method to establish a connection to the MySQL database.
 * Uses JDBC with predefined connection parameters.
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/airline_management";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Creates and returns a new database connection.
     *
     * @return a Connection object to the database
     * @throws SQLException if the connection fails
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}