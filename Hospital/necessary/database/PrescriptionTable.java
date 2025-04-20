package Hospital.necessary.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Hospital.necessary.Connector;

public class PrescriptionTable {
    public static boolean CreatePrescription(int appointmentId, String content) {
        Connection conn = Connector.connector();

        if (conn == null) {
            System.out.println("Failed to get database connection.");
            return false;

        }

        String query = "INSERT INTO prescription (appointmentId, content) VALUE(?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, appointmentId);
            stmt.setString(2, content);

            int rowsCount = stmt.executeUpdate();

            if (rowsCount > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String GetPrescriptionByAppointmentId(int appointmentId) {
        Connection conn = Connector.connector();

        if (conn == null) {
            System.out.println("Failed to get database connection.");
            return null; // Indicate failure
        }

        String query = "SELECT content FROM prescription WHERE appointmentId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, appointmentId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("content"); // Return the prescription content
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if no prescription is found or an error occurs
    }

    
}
