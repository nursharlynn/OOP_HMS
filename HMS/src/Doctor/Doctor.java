package Doctor;

import Appointment.*;
import Records.*;
import User.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Doctor extends User {

    private Schedule schedule;
    private IAppointment apptHandler;
    private MedicalRecord medicalRecord;
    private Scanner scanner;

    public Doctor(String hospitalId, String password, String name, String gender) {
        super(hospitalId, password, name, gender);
        this.scanner = new Scanner(System.in);
        this.apptHandler = new DoctorAppointmentManager();
    }

    @Override
    public void displayMenu() {
        System.out.println("\n--- Doctor Menu ---");
        System.out.println("1. View Patient Medical Records");
        System.out.println("2. Update Patient Medical Records");
        System.out.println("3. View Personal Schedule");
        System.out.println("4. Set Availability for Appointments");
        System.out.println("5. Accept or Decline Appointment Requests");
        System.out.println("6. View Upcoming Appointments");
        System.out.println("7. Record Appointment Outcome");
        System.out.println("8. Logout");
    }

	@Override
	public void handleMenuChoice(int choice) {
    switch (choice) {
        case 1:
            viewPatientMedicalRecords(); 
            break;
        case 2:
            updatePatientMedicalRecords();
            break;
        case 3:
            viewPersonalSchedule();
            break;
        case 4:
            setAvailability();
            break;
        case 5:
            apptHandler.reviewAppointmentRequests(this);
            break;
        case 6:
            apptHandler.viewUpcomingAppointments(this);
            break;
        case 7:
            ((DoctorAppointmentManager)apptHandler).recordAppointmentOutcome(this);
            break;
        case 8:
            System.out.println("Logging out...");
            return; // This will exit the menu loop and return to main menu
        default:
            System.out.println("Invalid choice. Please try again.");
    }
}

    /**
     * Sets the appointment handler for the doctor
     * @param apptHandler Appointment handler
     */
    public void setApptHandler(IAppointment apptHandler) {
        this.apptHandler = apptHandler;
    }

    /**
     * Sets the schedule for the doctor
     * @param schedule Doctor's schedule
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    // Placeholder methods for potential future implementation
    public List<String> viewPersonalSchedule() {
        DoctorAppointmentManager appointmentManager = new DoctorAppointmentManager();
        return appointmentManager.viewPersonalSchedule(this);
    }

    /**
     * Updates doctor's availability
     * @param start Start time for availability
     * @return boolean indicating if update was successful
     */
    public boolean updateAvailability(LocalDateTime start) {
        System.out.println("Update Availability - Not implemented");
        return false;
    }

    /**
     * Updates patient's medical records
     * @param patientId Patient's ID
     * @param newDiagnosis New diagnosis
     * @param prescriptions Prescriptions
     * @param newTreatment New treatment
     */
    public void updatePatientMedicalRecords(String patientId, String newDiagnosis, 
                                            String prescriptions, String newTreatment) {
        System.out.println("Update Patient Medical Records - Not implemented");
    }

    @Override
    public String toString() {
        return "Doctor: " + getName() + " (ID: " + getHospitalId() + ")";
    }

    public void setAvailability() {
        System.out.println("Set Availability for Appointments");
        
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        
        System.out.print("Enter Time (HH:MM): ");
        String time = scanner.nextLine();
        
        // Get doctor's name from current user
        String doctorName = getName();
        
        try {
            Path path = Paths.get("data/Appointments.csv");
            
            // Get next appointment ID using the static method
            int appointmentId = getNextAppointmentId(path);
            
            // Ensure file exists and has header
            if (!Files.exists(path) || Files.size(path) == 0) {
                Files.createDirectories(path.getParent());
                try (BufferedWriter initialWriter = Files.newBufferedWriter(path)) {
                    initialWriter.write("AppointmentID,DoctorName,Date,Time,Status");
                    initialWriter.newLine();
                }
            }
            
            // Append new appointment
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.write(String.format("%d,%s,%s,%s,Available", 
                    appointmentId, doctorName, date, time));
                writer.newLine();
                
                System.out.println("Appointment slot created successfully!");
                System.out.println("Appointment ID: " + appointmentId);
            }
        } catch (IOException e) {
            System.out.println("Error creating appointment: " + e.getMessage());
        }
    }

    public static int getNextAppointmentId(Path path) {
        try {
            // If file doesn't exist or is empty, return 1
            if (!Files.exists(path) || Files.size(path) == 0) {
                return 1;
            }
            
            // Read lines, skip header, find max ID
            Optional<Integer> maxId = Files.lines(path)
                .skip(1) // Skip header
                .map(line -> {
                    try {
                        // Split by comma or tab to handle different file formats
                        String[] parts = line.split("[,\t]");
                        return Integer.parseInt(parts[0].trim());
                    } catch (Exception e) {
                        return 0;
                    }
                })
                .max(Integer::compare);
            
            // Return max ID + 1, or 1 if no valid IDs found
            return maxId.orElse(0) + 1;
        } catch (IOException e) {
            System.out.println("Error getting next appointment ID: " + e.getMessage());
            return 1;
        }
    }

    public void updatePatientMedicalRecords() {
    try {
        // Read AppointmentOutcomes.csv to get patients the doctor has seen
        Path outcomesPath = Paths.get("data/AppointmentOutcomes.csv");
        List<String> outcomeLines = Files.readAllLines(outcomesPath);
        
        // Filter outcomes for this specific doctor
        List<String[]> doctorPatients = outcomeLines.stream()
            .skip(1) // Skip header
            .map(line -> line.split(","))
            .filter(data -> 
                data.length > 1 && 
                data[1].trim().equalsIgnoreCase(getName()) // Filter by doctor's name
            )
            .collect(Collectors.toList());
        
        // Check if doctor has any appointments
        if (doctorPatients.isEmpty()) {
            System.out.println("You have no appointments to update records for.");
            return;
        }
        
        // Display patients the doctor can update
        System.out.println("\n=== Patients You Can Update ===");
        System.out.printf("%-5s %-15s %-20s %-15s%n", 
            "No.", "Patient ID", "Patient Name", "Appointment Date");
        System.out.println("-".repeat(60));
        
        // Use a list to store unique patients to avoid duplicates
        Map<String, String[]> uniquePatients = new HashMap<>();
        for (int i = 0; i < doctorPatients.size(); i++) {
            String[] patient = doctorPatients.get(i);
            // Use patient ID as the key to ensure uniqueness
            uniquePatients.putIfAbsent(patient[2], patient);
        }
        
        // Convert unique patients to a list for display
        List<String[]> uniquePatientsList = new ArrayList<>(uniquePatients.values());
        
        for (int i = 0; i < uniquePatientsList.size(); i++) {
            String[] patient = uniquePatientsList.get(i);
            System.out.printf("%-5d %-15s %-20s %-15s%n", 
                i + 1, 
                patient[2],  // Patient ID
                patient[3],  // Patient Name
                patient[4]   // Date of appointment
            );
        }
        
        // Prompt for patient selection
        System.out.print("\nEnter the number of the patient to update: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (choice < 1 || choice > uniquePatientsList.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        
        // Get selected patient details
        String[] selectedPatient = uniquePatientsList.get(choice - 1);
        String patientId = selectedPatient[2];
        
        // Read current Patient_List.csv
        Path patientListPath = Paths.get("data/Patient_List.csv");
        List<String> patientLines = Files.readAllLines(patientListPath);
        
        // Find the specific patient in the list
        boolean patientFound = false;
        for (int i = 1; i < patientLines.size(); i++) {
            String[] patientData = patientLines.get(i).split(",");
            
            if (patientData[0].trim().equals(patientId)) {
                // Prompt for new diagnosis
                System.out.print("Enter new diagnosis (current: " + 
                    (patientData.length > 6 ? patientData[6] : "None") + "): ");
                String newDiagnosis = scanner.nextLine().trim();
                
                // Prompt for new treatment
                System.out.print("Enter new treatment (current: " + 
                    (patientData.length > 7 ? patientData[7] : "None") + "): ");
                String newTreatment = scanner.nextLine().trim();
                
                // Prepare updated patient line
                String updatedLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s", 
                    patientData[0],  // Patient ID
                    patientData[1],  // Name
                    patientData[2],  // Date of Birth
                    patientData[3],  // Gender
                    patientData[4],  // Blood Type
                    patientData[5],  // Contact
                    newDiagnosis.isEmpty() ? (patientData.length > 6 ? patientData[6] : "") : newDiagnosis,
                    newTreatment.isEmpty() ? (patientData.length > 7 ? patientData[7] : "") : newTreatment
                );
                
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
        e.printStackTrace();
    }
}

public void viewPatientMedicalRecords() {
    try {
        // Read Appointments.csv to find confirmed appointments for this doctor
        Path appointmentsPath = Paths.get("data/Appointments.csv");
        List<String> appointmentLines = Files.readAllLines(appointmentsPath);
        
        // Filter confirmed appointments for this specific doctor
        List<String[]> doctorPatients = appointmentLines.stream()
            .skip(1) // Skip header
            .map(line -> line.split(","))
            .filter(data -> 
                data.length > 4 && 
                data[1].trim().equalsIgnoreCase(getName()) && // Match doctor's name
                data[4].trim().equalsIgnoreCase("Confirmed") // Only confirmed appointments
            )
            .collect(Collectors.toList());
        
        // Check if doctor has any confirmed appointments
        if (doctorPatients.isEmpty()) {
            System.out.println("You have no confirmed appointments to view patient records.");
            return;
        }
        
        // Display patients with confirmed appointments
        System.out.println("\n=== Patients with Confirmed Appointments ===");
        System.out.printf("%-5s %-15s %-20s %-15s %-15s%n", 
            "No.", "Patient ID", "Patient Name", "Date", "Time");
        System.out.println("-".repeat(75));
        
        // Use a map to ensure unique patients
        Map<String, String[]> uniquePatients = new HashMap<>();
        for (String[] patient : doctorPatients) {
            // Use patient ID as the key to ensure uniqueness
            uniquePatients.putIfAbsent(patient[5], patient);
        }
        
        // Convert to list for display
        List<String[]> uniquePatientsList = new ArrayList<>(uniquePatients.values());
        
        for (int i = 0; i < uniquePatientsList.size(); i++) {
            String[] patient = uniquePatientsList.get(i);
            System.out.printf("%-5d %-15s %-20s %-15s %-15s%n", 
                i + 1, 
                patient[5],  // Patient ID
                patient[6],  // Patient Name
                patient[2],  // Date
                patient[3]   // Time
            );
        }
        
        // Prompt for patient selection
        System.out.print("\nEnter the number of the patient to view medical record: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (choice < 1 || choice > uniquePatientsList.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        
        // Get selected patient details
        String[] selectedPatient = uniquePatientsList.get(choice - 1);
        String patientId = selectedPatient[5];
        
        // Read Patient_List.csv to get full medical record
        Path patientListPath = Paths.get("data/Patient_List.csv");
        List<String> patientLines = Files.readAllLines(patientListPath);
        
        // Find and display the specific patient's medical record
        boolean patientFound = false;
        for (int i = 1; i < patientLines.size(); i++) {
            String[] patientData = patientLines.get(i).split(",");
            
            if (patientData[0].trim().equals(patientId)) {
                patientFound = true;
                
                // Display detailed medical record
                System.out.println("\n=== Detailed Medical Record ===");
                System.out.println("Patient ID: " + patientData[0]);
                System.out.println("Name: " + patientData[1]);
                System.out.println("Date of Birth: " + patientData[2]);
                System.out.println("Gender: " + patientData[3]);
                System.out.println("Blood Type: " + patientData[4]);
                System.out.println("Contact: " + patientData[5]);
                
                // Display diagnosis (if exists)
                System.out.println("Current Diagnosis: " + 
                    (patientData.length > 6 && !patientData[6].isEmpty() ? patientData[6] : "No current diagnosis"));
                
                // Display treatment (if exists)
                System.out.println("Current Treatment: " + 
                    (patientData.length > 7 && !patientData[7].isEmpty() ? patientData[7] : "No current treatment"));
                break;
            }
        }
        
        if (!patientFound) {
            System.out.println("Patient not found in the system.");
        }
        
    } catch (IOException e) {
        System.out.println("Error viewing patient medical record: " + e.getMessage());
        e.printStackTrace();
    }
}
    
}