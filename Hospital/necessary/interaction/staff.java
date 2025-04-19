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

            System.out.println("logged in : " + loggedIn);

            if (loggedIn == 1) {
                // doctor interaction
                break;
            } else if (loggedIn == 2) {
                // recieptionist interaction
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
            
            if(ch == 1) {
                patients = AppointmentTable.GetPatientAppointmentsQuery();
            } else if(ch == 2) {
                int doctorId, appointmentId;
                System.out.println("Enter the Doctor Id ");
                doctorId = sc.nextInt();

                System.out.print("Enter the Appointment Id ");
                appointmentId = sc.nextInt();

                // Appointment status update
                
            
            }

            

        } while(true);
    }

    public static void DoctorInteraction() {
    }

}
