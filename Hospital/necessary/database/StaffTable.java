package Hospital.necessary.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import Hospital.necessary.Connector;

public class StaffTable {
    public static int EmailPasswordVerifyQuery(String email, String password) {

        Connection conn = Connector.connector();

        if (conn == null) {
            System.out.println("Failed to get database connection.");
            return -1; // Indicate failure
        }

        // Safety measures
        String Vemail = email.toLowerCase().trim();
        String Vpassword = password.toLowerCase().trim();

        String query = "SELECT staffId, name, email, role, `field` AS staffField FROM staff WHERE email=? AND password=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, Vemail);
            stmt.setString(2, Vpassword);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int staffId = rs.getInt("staffId");
                    String staffName = rs.getString("name");
                    String field = rs.getString("staffField");
                    String role = rs.getString("role");

                    System.out.println("Logged in as " + staffName + " with id: " + staffId + ", workding as : " + field);

                    if(role.equals("doctor")) {
                        return 1;
                    } else if(role.equals("recieptionist")) {
                        return 2;
                    }

                    return 0;
                } else {
                    System.out.println("Invalid email or password.");
                    return 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -2;
        }

    }

}
