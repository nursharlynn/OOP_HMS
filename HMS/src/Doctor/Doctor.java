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
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Doctor extends User {

    private Schedule schedule;
    private IAppointment apptHandler;
    private MedicalRecord medicalRecord;
    private Scanner scanner;

    public Doctor(String hospitalId, String password, String name, String gender) {
        super(hospitalId, password, name, gender);
        this.scanner = new Scanner(System.in);
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
            System.out.println("View Patient Medical Records - Not implemented yet");
            break;
        case 2:
            System.out.println("Update Patient Medical Records - Not implemented yet");
            break;
        case 3:
            System.out.println("View Personal Schedule - Not implemented yet");
            break;
        case 4:
            setAvailability();
            break;
        case 5:
            System.out.println("Accept or Decline Appointment Requests - Not implemented yet");
            break;
        case 6:
            System.out.println("View Upcoming Appointments - Not implemented yet");
            break;
        case 7:
            System.out.println("Record Appointment Outcome - Not implemented yet");
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
        System.out.println("View Personal Schedule - Not implemented");
        return null;
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
        
        // Get next appointment ID
        int appointmentId = getNextAppointmentId();
        
        try {
            Path path = Paths.get("data/Appointments.csv");
            
            // Ensure file exists and has header
            if (!Files.exists(path) || Files.size(path) == 0) {
                // Create file and write header
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

private int getNextAppointmentId() {
    try {
        Path path = Paths.get("data/Appointments.csv");
        
        // If file doesn't exist or is empty, return 1
        if (!Files.exists(path) || Files.size(path) == 0) {
            return 1;
        }
        
        // Read lines, skip header, find max ID
        Optional<Integer> maxId = Files.lines(path)
            .skip(1) // Skip header
            .map(line -> {
                try {
                    return Integer.parseInt(line.split(",")[0]);
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
}