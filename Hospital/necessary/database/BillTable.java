package Hospital.necessary.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Hospital.necessary.Connector;

public class BillTable {
    public static int CreateBill(int appointmentId) {
        Connection conn = Connector.connector();

        // Check for the connection
        if (conn == null) {
            System.out.println("Failed to get database connection.");
            return -1; // Indicate failure
        }

        String query = "INSERT INTO bills (appointmentId, paymentStatus) VALUES (?, 0)";

        try (PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, appointmentId);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Retrieve the generated billId
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Return the generated billId
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Indicate failure
    }

    public static boolean UpdateBillPaymentStatus(int billId) {
        Connection conn = Connector.connector();

        // Check for the connection
        if (conn == null) {
            System.out.println("Failed to get database connection.");
            return false; // Indicate failure
        }

        String query = "UPDATE bills SET paymentStatus = 1 WHERE billId = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, billId);

            int rowsAffected = stmt.executeUpdate();

            // Return true if the payment status was successfully updated
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Indicate failure
        }
    }

    public static ArrayList<String> BilledScheduledAppointments(int doctorId) {
        Connection conn = Connector.connector();

        if (conn == null) {
            System.out.println("Failed to get database connection.");
            return new ArrayList<String>();
        }

        String query = "SELECT ap.appointmentId, ap.problemDescription, ap.patientId FROM appointments AS ap INNER JOIN bills AS bi ON ap.appointmentId = bi.appointmentId WHERE ap.status='scheduled' AND bi.paymentStatus=1";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            // stmt.setInt(1, doctorId);

            ResultSet rs = stmt.executeQuery();
            ArrayList<String> resultArray = new ArrayList<String>();

            while (rs.next()) {
                String appointmentId = rs.getString("appointmentId");
                String problem = rs.getString("problemDescription");
                int patientId = rs.getInt("patientId");

                String resultString = appointmentId + " " + patientId + " " + problem;
                System.out.println("result : " + resultString);

                resultArray.add(resultString);
            }

            return resultArray;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<String>();
    }

    public static int GetBillIdByAppointmentId(int appointmentId) {
        Connection conn = Connector.connector();

        // Check for the connection
        if (conn == null) {
            System.out.println("Failed to get database connection.");
            return -1; // Indicate failure
        }

        String query = "SELECT billId FROM bills WHERE appointmentId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, appointmentId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("billId"); // Return the billId if found
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Return -1 if no bill is found or an error occurs
    }

}
