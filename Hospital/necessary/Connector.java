package Hospital.necessary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    // Static instance of the connection
    private static Connection connection;

    // Private constructor to prevent instantiation
    private Connector() {
    }

    // Public method to get the single instance of the connection
    public static Connection connector() {
        if (connection == null) {
            try {
                // Load the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish the connection
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");

                if (connection != null) {
                    System.out.println("Connected to the database");
                } else {
                    System.out.println("Failed to connect to the database");
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    // Method to close the connection (optional, for cleanup)
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
