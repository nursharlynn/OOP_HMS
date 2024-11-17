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

            if (lines.size() <= 1) {
                System.out.println("No appointments found.");
                return;
            }

            // Print header
            System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s %-15s%n",
                    "Appt ID", "Doctor Name", "Date", "Time", "Status", "Patient ID", "Patient Name");
            System.out.println("-".repeat(105));

            for (String line : lines.subList(1, lines.size())) {
                String[] data = line.split(",");
                String patientId = (data.length > 5 && !data[5].isEmpty()) ? data[5] : "N/A";
                String patientName = (data.length > 6 && !data[6].isEmpty()) ? data[6] : "N/A";

                System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s %-15s%n",
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4],
                        patientId,
                        patientName);
            }
        } catch (IOException e) {
            System.out.println("Error reading appointments: " + e.getMessage());
        }
    }
}