package Doctor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PatientRecordManager {

    public List<String[]> getConfirmedAppointmentsForDoctor(String doctorName) throws IOException {
        Path appointmentsPath = Path.of("data/Appointments.csv");
        List<String> appointmentLines = Files.readAllLines(appointmentsPath);

        return appointmentLines.stream()
                .skip(1)
                .map(line -> line.split(","))
                .filter(data -> data.length > 4 &&
                        data[1].trim().equalsIgnoreCase(doctorName) && 
                        data[4].trim().equalsIgnoreCase("Confirmed") 
                )
                .collect(Collectors.toList());
    }

    public PatientRecord getPatientRecord(String patientId) throws IOException {
        Path patientListPath = Path.of("data/Patient_List.csv");
        List<String> patientLines = Files.readAllLines(patientListPath);

        for (int i = 1; i < patientLines.size(); i++) {
            String[] patientData = patientLines.get(i).split(",");
            if (patientData[0].trim().equals(patientId)) {
                return new PatientRecord(patientData[0], patientData[1], patientData[2], patientData[3],
                        patientData[4], patientData[5], patientData[6], patientData[7]);
            }
        }
        return null; // Patient not found
    }

    public void displayPatientRecord(PatientRecord record) {
        System.out.println("\n=== Detailed Medical Record ===");
        System.out.println("Patient ID: " + record.getId());
        System.out.println("Name: " + record.getName());
        System.out.println("Date of Birth: " + record.getDateOfBirth());
        System.out.println("Gender: " + record.getGender());
        System.out.println("Blood Type: " + record.getBloodType());
        System.out.println("Contact: " + record.getContact());
        System.out.println("Current Diagnosis: " + record.getDiagnosis());
        System.out.println("Current Treatment: " + record.getTreatment());
    }

    public void updatePatientMedicalRecords(Doctor doctor) {
        try {
            // Read AppointmentOutcomes.csv to get patients the doctor has seen
            Path outcomesPath = Path.of("data/AppointmentOutcomes.csv");
            List<String> outcomeLines = Files.readAllLines(outcomesPath);

            // Filter outcomes for this specific doctor
            List<String[]> doctorPatients = outcomeLines.stream()
                    .skip(1) 
                    .map(line -> line.split(","))
                    .filter(data -> data.length > 1 && data[1].trim().equalsIgnoreCase(doctor.getName()))
                    .collect(Collectors.toList());

            // Check if doctor has any appointments
            if (doctorPatients.isEmpty()) {
                System.out.println("You have no appointments to update records for.");
                return;
            }

            // Display patients the doctor can update
            System.out.println("\n=== Patients You Can Update ===");
            System.out.printf("%-5s %-15s %-20s %-15s%n", "No.", "Patient ID", "Patient Name", "Appointment Date");
            System.out.println("-".repeat(60));

            // Use a list to store unique patients to avoid duplicates
            for (int i = 0; i < doctorPatients.size(); i++) {
                String[] patient = doctorPatients.get(i);
                System.out.printf("%-5d %-15s %-20s %-15s%n",
                        i + 1, patient[2], patient[3], patient[4]); 
            }

            // Prompt for patient selection
            Scanner scanner = new Scanner(System.in);
            System.out.print("\nEnter the number of the patient to update: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice < 1 || choice > doctorPatients.size()) {
                System.out.println("Invalid selection.");
                return;
            }

            // Get selected patient details
            String[] selectedPatient = doctorPatients.get(choice - 1);
            String selectedPatientId = selectedPatient[2];

            // Read current Patient_List.csv
            Path patientListPath = Path.of("data/Patient_List.csv");
            List<String> patientLines = Files.readAllLines(patientListPath);

            // Find the specific patient in the list
            boolean patientFound = false;
            for (int i = 1; i < patientLines.size(); i++) {
                String[] patientData = patientLines.get(i).split(",");

                if (patientData[0].trim().equals(selectedPatientId)) {
                    // Prompt for new diagnosis
                    System.out.print("Enter new diagnosis (current: " +
                            (patientData.length > 6 ? patientData[6] : "None") + "): ");
                    String newDiagnosisInput = scanner.nextLine().trim();

                    // Prompt for new treatment
                    System.out.print("Enter new treatment (current: " +
                            (patientData.length > 7 ? patientData[7] : "None") + "): ");
                    String newTreatmentInput = scanner.nextLine().trim();

                    // Prepare updated patient line
                    String updatedLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s",
                            patientData[0], // Patient ID
                            patientData[1], // Name
                            patientData[2], // Date of Birth
                            patientData[3], // Gender
                            patientData[4], // Blood Type
                            patientData[5], // Contact
                            newDiagnosisInput.isEmpty() ? (patientData.length > 6 ? patientData[6] : "")
                                    : newDiagnosisInput,
                            newTreatmentInput.isEmpty() ? (patientData.length > 7 ? patientData[7] : "")
                                    : newTreatmentInput);

                    // Update the line
                    patientLines.set(i, updatedLine);
                    patientFound = true;
                    break;
                }
            }

            if (patientFound) {
                // Write back to Patient_List.csv
                Files.write(patientListPath, patientLines);
                System.out.println("Patient medical record updated successfully!");
            } else {
                System.out.println("Patient not found in the system.");
            }

        } catch (IOException e) {
            System.out.println("Error updating patient medical record: " + e.getMessage());
        }
    }
}