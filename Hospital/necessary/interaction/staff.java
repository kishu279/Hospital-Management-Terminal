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

        while (true) {
            System.out.println("\n=== Login Page ===");
            System.out.println("Enter the email ");
            userEmail = sc.nextLine();
            System.out.println("Enter password ");
            password = sc.nextLine();

            StaffTable.EmailPasswordVerifyQuery(userEmail, password);
        }
    }

    public static void RecieptionistInteraction() {
        Scanner sc = new Scanner(System.in);

        ArrayList<String> patients = new ArrayList<>();

        do {
            System.out.println("\n=== Receptionist Menu ===");
            System.out.println("1. Get Pending Appointment Status");
            System.out.println("2. Update Appointments");
            System.out.println("3. Create a Bill");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    // Get Pending Appointment Status
                    System.out.println("\n=== Pending Appointments ===");
                    patients = AppointmentTable.GetPatientAppointmentsQuery();

                    if (patients.isEmpty()) {
                        System.out.println("No pending appointments found.");
                    } else {
                        for (int i = 0; i < patients.size(); i++) {
                            System.out.println((i + 1) + ". " + patients.get(i));
                        }
                    }
                    break;

                case 2:
                    // Update Appointments
                    System.out.println("\n=== Update Appointment ===");
                    System.out.print("Enter the Doctor ID: ");
                    int doctorId = sc.nextInt();

                    System.out.print("Enter the Appointment ID: ");
                    int appointmentId = sc.nextInt();

                    System.out.print("Enter the date and time for scheduling (format: YYYY-MM-DD HH:MM:SS): ");
                    sc.nextLine(); // Consume the newline character
                    String scheduledTime = sc.nextLine();

                    if (AppointmentTable.AppointmentSchedulerUpdate(doctorId, appointmentId, "scheduled",
                            scheduledTime)) {
                        System.out.println("Appointment status updated successfully.");
                    } else {
                        System.out.println("Failed to update the appointment status.");
                    }
                    break;

                case 3:
                    // Create a Bill
                    System.out.println("\n=== Create Bill ===");
                    System.out.print("Enter the Appointment ID for billing: ");
                    int billAppointmentId = sc.nextInt();

                    int billId = BillTable.CreateBill(billAppointmentId);

                    if (billId != -1) {
                        System.out.println("Bill created successfully. Bill ID: " + billId);
                    } else {
                        System.out.println("Failed to create the bill. Please check the appointment ID.");
                    }
                    break;

                case 4:
                    // Exit
                    System.out.println("Exiting Receptionist Menu. Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (true);
    }

    public static void DoctorInteraction(int doctorId) {
        Scanner sc = new Scanner(System.in);

        ArrayList<String> patients = new ArrayList<>();

        do {
            System.out.println("\n=== Doctor Menu ===");
            System.out.println("1. Check Scheduled Appointments");
            System.out.println("2. Create Prescription and Mark Appointment as Done");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    // Check Scheduled Appointments
                    System.out.println("\n=== Scheduled Appointments ===");
                    patients = BillTable.BilledScheduledAppointments(doctorId);

                    if (patients.isEmpty()) {
                        System.out.println("No scheduled appointments found.");
                    } else {
                        for (int i = 0; i < patients.size(); i++) {
                            System.out.println((i + 1) + ". " + patients.get(i));
                        }
                    }
                    break;

                case 2:
                    // Create Prescription
                    System.out.println("\n=== Create Prescription ===");
                    System.out.print("Enter the Appointment ID: ");
                    int appointmentId = sc.nextInt();

                    System.out.print("Enter the Prescription Content: ");
                    sc.nextLine(); // Consume the newline character
                    String content = sc.nextLine();

                    if (PrescriptionTable.CreatePrescription(appointmentId, content)) {
                        System.out.println("\nPrescription Added Successfully!");

                        LocalDateTime myTime = LocalDateTime.now();
                        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String formattedTime = myTime.format(timeFormat);

                        // Update Appointment Status
                        if (AppointmentTable.AppointmentSchedulerUpdate(doctorId, appointmentId, "done",
                                formattedTime)) {
                            System.out.println("Appointment marked as 'done'.");
                        } else {
                            System.out.println("Failed to update the appointment status.");
                        }
                    } else {
                        System.out.println("Failed to add the prescription. Please try again.");
                    }
                    break;

                case 3:
                    // Exit
                    System.out.println("Exiting Doctor Menu. Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (true);
    }

}
