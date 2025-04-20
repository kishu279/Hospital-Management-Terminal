package Hospital.main;

import java.sql.Connection;
import java.util.Scanner;

import Hospital.necessary.Connector;
import Hospital.necessary.interaction.patient;
import Hospital.necessary.interaction.staff;

public class Main {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;

        try {

            conn = Connector.connector();

            System.out.println("At the main function");

            // main program starts

            int ch = 0;

            while (ch < 1 || ch > 2) {
                System.out.println("Get Checked Up : 1");
                System.out.println("Sign in as Staff : 2");

                ch = sc.nextInt();

                if (ch == 1) {
                    // Patient Interaction
                    patient.patientInteraction();

                } else if (ch == 2) {
                    // Login Page
                    staff.LoginPage();
                } else {
                    System.out.println("Wrong Choice, Enter again : ");
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
