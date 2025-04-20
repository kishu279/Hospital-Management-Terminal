package Hospital.necessary.interaction;

import java.util.ArrayList;
import java.util.Scanner;

import Hospital.necessary.database.AppointmentTable;
import Hospital.necessary.database.StaffTable;

public class staff {
    public static void LoginPage() {
        Scanner sc = new Scanner(System.in);

        // Ask fot the useremail and password
        String userEmail, password;
        int loggedIn = 0;

        while (true) {
            System.out.println("Enter the email ");
            userEmail = sc.nextLine();
            System.out.println("Enter password ");
            password = sc.nextLine();

            loggedIn = StaffTable.EmailPasswordVerifyQuery(userEmail, password);

            if (loggedIn == 1) {
                // doctor interaction
                staff.DoctorInteraction();
                break;
            } else if (loggedIn == 2) {
                // recieptionist interaction
                staff.RecieptionistInteraction();
                break;
            } else {
                System.out.println("Failed to login");
            }
        }
    }

    public static void RecieptionistInteraction() {
        Scanner sc = new Scanner(System.in);

        int ch = 0;
        ArrayList<String> patients = new ArrayList<String>();

        do {
            System.out.println("Get Pending Appointment Status : 1");
            System.out.println("Update the Appointments : 2");
            // ...

            ch = sc.nextInt();

            if (ch == 1) {
                patients = AppointmentTable.GetPatientAppointmentsQuery();

                for (int i = 0; i < patients.size(); i++) {
                    System.out.println(patients.get(i));
                }

            } else if (ch == 2) {
                int doctorId, appointmentId;
                System.out.println("Enter the Doctor Id ");
                doctorId = sc.nextInt();

                System.out.print("Enter the Appointment Id ");
                appointmentId = sc.nextInt();

                System.out.println("Enter the date and time for scheduling (format: YYYY-MM-DD HH:MM:SS)");
                sc.nextLine(); // Consume the newline character
                String scheduledTime = sc.nextLine();

                // Appointment status update
                if (AppointmentTable.AppointmentSchedulerUpdate(doctorId, appointmentId, "scheduled", scheduledTime)) {
                    System.out.println("Status updated");
                } else {
                    System.out.println("Failed to update the status");
                }
            }

        } while (true);
    }

    public static void DoctorInteraction() {
    }

}
