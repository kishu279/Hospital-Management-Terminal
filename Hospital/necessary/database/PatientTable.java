package Hospital.necessary.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Hospital.necessary.Connector;

public class PatientTable {    
    //  return the user id upon creation 
    public static int InsertQuery(String name, String idProof, int age) {
        // get the connection instance
        Connection conn = Connector.connector();

        // Check for the connection
        if (conn == null) {
            System.out.println("Failed to get database connection.");
            return -1; // Indicate failure
        }

        // Query for inserting
        String query = "INSERT INTO patient (name, idProof, age) VALUES (?, ?, ?)";
        try(PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, idProof);
            stmt.setInt(3, age);

            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0) {
                var rs = stmt.getGeneratedKeys();
                if(rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


}
