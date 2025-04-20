package Hospital.necessary.interaction;

import java.util.Scanner;

import Hospital.necessary.database.AppointmentTable;
import Hospital.necessary.database.PatientTable;

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

            while (true) {
                System.out.println();
                System.out.println("Check Appointment Status : 1");
                System.out.println("Prescription Panel Print : 2");
                System.out.println("Bill Status : 3");
                System.out.println("Exit : CTRL^C");

                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        // Check Appointment Status
                        String status = AppointmentTable.GetAppointmentStatusQuery(appointmentId);
                        System.out.println("Appontment status : " + status);

                        break;
                    case 2:
                        // Print the prescription

                        break;
                    case 3:
                        // bill status

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
