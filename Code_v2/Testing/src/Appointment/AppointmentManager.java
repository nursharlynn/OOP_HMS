package Appointment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class AppointmentManager {

    private String appointmentFilePath = "data/Appointments.csv";

    public void viewAllAppointments() {
        try {
            Path path = Path.of(appointmentFilePath);
            List<String> lines = Files.readAllLines(path);

            // Check if there are any appointments
            if (lines.size() <= 1) {
                System.out.println("No appointments found.");
                return;
            }

            // Print header
            System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s %-15s%n",
                    "Appt ID", "Doctor Name", "Date", "Time", "Status", "Patient ID", "Patient Name");
            System.out.println("-".repeat(105)); // Adjusted separator line length

            // Iterate through the lines and print appointment details
            for (String line : lines.subList(1, lines.size())) { // Skip header
                String[] data = line.split(",");
                // Determine Patient ID and Patient Name based on availability
                String patientId = (data.length > 5 && !data[5].isEmpty()) ? data[5] : "N/A"; // Patient ID
                String patientName = (data.length > 6 && !data[6].isEmpty()) ? data[6] : "N/A"; // Patient Name

                System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s %-15s%n",
                        data[0], // Appointment ID
                        data[1], // Doctor Name
                        data[2], // Date
                        data[3], // Time
                        data[4], // Status
                        patientId, // Patient ID
                        patientName); // Patient Name
            }
        } catch (IOException e) {
            System.out.println("Error reading appointments: " + e.getMessage());
        }
    }
}