package Hospital.necessary.interaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import Hospital.necessary.database.AppointmentTable;
import Hospital.necessary.database.BillTable;
import Hospital.necessary.database.PrescriptionTable;
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

            StaffTable.EmailPasswordVerifyQuery(userEmail, password);
        }
    }

    public static void RecieptionistInteraction() {
        Scanner sc = new Scanner(System.in);

        int ch = 0;
        ArrayList<String> patients = new ArrayList<String>();

        do {
            System.out.println("Get Pending Appointment Status : 1");
            System.out.println("Update the Appointments : 2");
            System.out.println("Create a Bill : 3"); // New option for creating a bill
            ch = sc.nextInt();

            if (ch == 1) {
                System.out.println("Patients: ");
                System.out.println("");
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

            } else if (ch == 3) { // New case for creating a bill
                System.out.print("Enter the Appointment Id for billing: ");
                int appointmentId = sc.nextInt();

                int billId = BillTable.CreateBill(appointmentId); // Call CreateBill method

                if (billId != -1) {
                    System.out.println("Bill created successfully. Bill ID: " + billId);
                } else {
                    System.out.println("Failed to create the bill. Please check the appointment ID.");
                }
            }

        } while (true);
    }

    public static void DoctorInteraction(int doctorId) {

        Scanner sc = new Scanner(System.in);

        ArrayList<String> patients = new ArrayList<String>();

        do {
            int ch = 0;

            System.out.println("Check for the appointments scheduled : 1");
            System.out.println("Updte the appointment : 2");

            ch = sc.nextInt();

            switch (ch) {
                case 1:

                    System.out.println("Patients details:");
                    patients = BillTable.BilledScheduledAppointments(doctorId);

                    for (int i = 0; i < patients.size(); i++) {
                        System.out.println(patients.get(i));
                    }

                    break;
                case 2:
                    // create the prescription
                    System.out.println("Enter the Appointment Id ");
                    int appointmentId = sc.nextInt();

                    System.out.println("Enter the content ");
                    sc.nextLine();
                    String content = sc.nextLine();

                    if (PrescriptionTable.CreatePrescription(appointmentId, content)) {
                        System.out.println("Prescription Added");

                        LocalDateTime myTime = LocalDateTime.now();
                        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                        String formattedTime = myTime.format(timeFormat);

                        // appointment update
                        if (AppointmentTable.AppointmentSchedulerUpdate(doctorId, appointmentId,
                                "done", formattedTime)) {
                            System.out.println("Appointment done ");
                        }

                    } else {
                        System.out.println("Failed to do so");
                    }
                    break;
                default:
                    break;
            }

        } while (true);

    }

}
