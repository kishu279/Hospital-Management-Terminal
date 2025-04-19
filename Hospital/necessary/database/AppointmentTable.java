package Hospital.necessary.database;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Hospital.necessary.Connector;
import Hospital.necessary.interaction.patient;

public class AppointmentTable {
    public static int InsertQuery(int patientId, String problem) {

        Connection conn = Connector.connector();

        // Check for the connection
        if (conn == null) {
            System.out.println("Failed to get database connection.");
            return -1; // Indicate failure
        }

        String query = "INSERT INTO appointments (patientId, problemDescription) VALUES(?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, patientId);
            stmt.setString(2, problem);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                var rs = stmt.getGeneratedKeys();

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static String GetAppointmentStatusQuery(int appointmentId) {

        Connection conn = Connector.connector();

        // Check for the connection
        if (conn == null) {
            System.out.println("Failed to get database connection.");
            return "pending"; // Indicate failure
        }

        String query = "SELECT status from appointments WHERE appointmentId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, appointmentId);

            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("status");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "pending";

    }

    public static ArrayList<String> GetPatientAppointmentsQuery() {

        Connection conn = Connector.connector();

        // Check for the connection
        if (conn == null) {
            System.out.println("Failed to get database connection.");
        }

        String query = "SELECT * FROM appointments where status=pending";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            ArrayList<String> result = new ArrayList<String>();
            
            while(rs.next()) {
                String appointmentId = rs.getString("appointmentId");
                String patientId = rs.getString("patientId");
                String status = rs.getString("status");
                String problem = rs.getString("problemDescription");

                String resultString = appointmentId + patientId + problem + status;
                
                result.add(resultString);
            }


            return result;
        } catch(SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static Boolean AppointmentStatusUpdate(int doctorId, int appointmentId) {
        return false;
    }
}
