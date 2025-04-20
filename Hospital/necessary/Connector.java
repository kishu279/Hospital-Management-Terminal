package Hospital.necessary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    // Static instance of the connection
    // private static Connection connection;

    // Private constructor to prevent instantiation
    // private Connector() {
    // }

    // Public method to get the single instance of the connection
    public static Connection connector() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
            connection.setAutoCommit(true); // Ensure auto-commit is enabled
            System.out.println("Connected to the database");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
