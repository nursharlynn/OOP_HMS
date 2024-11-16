package Doctor;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class PatientRecordService {

    private PatientRecordManager recordManager;

    public PatientRecordService() {
        this.recordManager = new PatientRecordManager();
    }

    public void viewPatientMedicalRecords(Doctor doctor, Scanner scanner) {
        try {
            List<String[]> doctorPatients = recordManager.getConfirmedAppointmentsForDoctor(doctor.getName());

            if (doctorPatients.isEmpty()) {
                System.out.println("You have no confirmed appointments to view patient records.");
                return;
            }

            System.out.println("\n=== Patients You Can Update ===");
            System.out.printf("%-5s %-15s %-20s %-15s%n",
                    "No.", "Patient ID", "Patient Name", "Appointment Date");
            System.out.println("-".repeat(60));

            for (int i = 0; i < doctorPatients.size(); i++) {
                String[] patient = doctorPatients.get(i);
                System.out.printf("%-5d %-15s %-20s %-15s%n",
                        i + 1,
                        patient[5],
                        patient[6],
                        patient[2]);
            }

            System.out.print("\nEnter the number of the patient to view their medical record: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice < 1 || choice > doctorPatients.size()) {
                System.out.println("Invalid selection.");
                return;
            }

            String patientId = doctorPatients.get(choice - 1)[5];

            PatientRecord record = recordManager.getPatientRecord(patientId);

            if (record != null) {
                recordManager.displayPatientRecord(record);
            } else {
                System.out.println("Patient record not found.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while accessing patient records: " + e.getMessage());
        }
    }

}