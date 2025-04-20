package Hospital.necessary.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Hospital.necessary.Connector;
import Hospital.necessary.interaction.staff;

public class StaffTable {
    public static void EmailPasswordVerifyQuery(String email, String password) {

        Connection conn = Connector.connector();

        if (conn == null) {
            System.out.println("Failed to get database connection.");
            return; // Indicate failure
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

                    System.out
                            .println("Logged in as " + staffName + " with id: " + staffId + ", workding as : " + field);

                    if (role.equals("doctor")) {
                        int doctorId = rs.getInt("staffId");
                        staff.DoctorInteraction(doctorId);

                    } else if (role.equals("receptionist")) {
                        staff.RecieptionistInteraction();
                    }

                    return;
                } else {
                    System.out.println("Invalid email or password.");
                    return;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

    }

    public static ArrayList<String> getDoctorList() {

        Connection conn = Connector.connector();

        if (conn == null) {
            System.out.println("Failed to get database connection.");

            return new ArrayList<String>();
        }

        String query = "SELECT * FROM staff WHERE role='doctor'";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            ArrayList<String> resultArray = new ArrayList<String>();

            while (rs.next()) {

                String doctorName = rs.getString("name");
                String staffId = rs.getString("staffId");
                String field = rs.getString("field");

                String resultString = staffId + " " + doctorName + " " + field;

                resultArray.add(resultString);
            }

            return resultArray;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

}
