package Hospital.necessary.interaction;

import java.util.Scanner;

import Hospital.necessary.database.AppointmentTable;
import Hospital.necessary.database.BillTable;
import Hospital.necessary.database.PatientTable;
import Hospital.necessary.database.PrescriptionTable;

public class patient {

    public static void patientInteraction() {
        Scanner sc = new Scanner(System.in);

        try {
            String name, idProof, problem;
            int age;

            // Ask for the details
            System.out.println("=== Enter Patient Details ===");
            System.out.print("Name: ");
            name = sc.nextLine();
            System.out.print("Age: ");
            age = sc.nextInt();
            System.out.print("ID Proof: ");
            sc.nextLine(); // Consume the newline character
            idProof = sc.nextLine();

            // Insert the details query
            int userId = PatientTable.InsertQuery(name, idProof, age);
            System.out.println("\nPatient ID: " + userId);

            System.out.print("\nEnter the Problem Description: ");
            problem = sc.nextLine();

            int appointmentId = AppointmentTable.InsertQuery(userId, problem);
            System.out.println("\nAppointment ID: " + appointmentId);

            int billId = -1;

            while (true) {
                System.out.println("\n=== Patient Menu ===");
                System.out.println("1. Check Appointment Status");
                System.out.println("2. Print Prescription");
                System.out.println("3. Pay the Bill");
                System.out.println("4. Exit (CTRL+C)");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        // Check Appointment Status
                        String status = AppointmentTable.GetAppointmentStatusQuery(appointmentId);
                        System.out.println("\nAppointment Status: " + status);

                        if (status.equals("pending")) {
                            System.out.println("Action Required: Please pay the bill.");
                        }
                        break;

                    case 2:
                        // Print the prescription
                        String prescription = PrescriptionTable.GetPrescriptionByAppointmentId(appointmentId);

                        if (prescription != null) {
                            System.out.println("\n=== Prescription ===");
                            System.out.println(prescription);
                        } else {
                            System.out.println("\nNo prescription found for this appointment.");
                        }
                        break;

                    case 3:
                        // Pay the bill
                        billId = BillTable.GetBillIdByAppointmentId(appointmentId);

                        if (billId != -1 && BillTable.UpdateBillPaymentStatus(billId)) {
                            System.out.println("\nPayment Successful! Bill ID: " + billId);
                        } else {
                            System.out.println("\nError: Please ensure the bill exists before attempting payment.");
                        }
                        break;

                    default:
                        System.out.println("\nInvalid choice. Please try again.");
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }

}
