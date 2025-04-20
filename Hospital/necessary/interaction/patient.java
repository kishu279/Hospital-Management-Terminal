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
            System.out.println("Enter Patients Details: ");
            System.out.println("Name : ");
            name = sc.nextLine();
            System.out.println("Age : ");
            age = sc.nextInt();
            System.out.println("ID Proof : ");
            sc.nextLine();
            idProof = sc.nextLine();

            // Insert the details query
            int userId = PatientTable.InsertQuery(name, idProof, age);
            System.err.println("Patient id : " + userId);

            System.err.println("Enter the Problems : ");
            problem = sc.nextLine();

            int appointmentId = AppointmentTable.InsertQuery(userId, problem);
            System.out.println("Appointment id : " + appointmentId);

            int billId = -1;

            while (true) {
                System.out.println();
                System.out.println("Check Appointment Status : 1");
                System.out.println("Prescription Panel Print : 2");
                System.out.println("Pay the bill : 3");
                System.out.println("Exit : CTRL^C");

                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        // Check Appointment Status
                        String status = AppointmentTable.GetAppointmentStatusQuery(appointmentId);
                        System.out.println("Appontment status : " + status);

                        if (status.equals("pending")) {
                            System.out.println("Pay the bill");
                        }

                        break;
                    case 2:
                        // Print the prescription

                        String statementString = PrescriptionTable.GetPrescriptionByAppointmentId(appointmentId);

                        System.out.println("Prescribed : " + statementString);

                        break;
                    case 3:
                        // Pay the bill
                        billId = BillTable.GetBillIdByAppointmentId(appointmentId);

                        if (BillTable.UpdateBillPaymentStatus(billId)) {
                            System.out.println("Billed");
                        } else {
                            System.out.println("get the bill first");
                        }

                        break;
                    default:
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
