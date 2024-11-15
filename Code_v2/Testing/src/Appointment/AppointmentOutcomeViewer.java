package Appointment;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class AppointmentOutcomeViewer {

    public static void viewAppointmentOutcomeRecords(PrintStream output, String patientId) {
        try {
            Path outcomePath = Paths.get("data/AppointmentOutcomes.csv");
            List<String> lines = Files.readAllLines(outcomePath);

            if (lines.size() <= 1) {
                output.println("No appointment outcome records found.");
                return;
            }

            output.println("\n=== Appointment Outcome Records ===");
            output.printf("%-10s %-15s %-15s %-15s %-20s %-25s %-30s %-30s %-15s%n",
                    "RecordID", "Doctor Name", "Patient ID", "Patient Name", "Date",
                    "Services Provided", "Prescribed Medications", "Consultation Notes", "Status");
            output.println("-".repeat(200));

            for (int i = 1; i < lines.size(); i++) {
                String[] outcome = lines.get(i).split(",");

                // If patientId is null, show all records; otherwise, filter by patient ID
                if (patientId == null || outcome[2].equals(patientId)) {
                    output.printf("%-10s %-15s %-15s %-15s %-20s %-25s %-30s %-30s %-15s%n",
                            outcome[0], // RecordID
                            outcome[1], // Doctor Name
                            outcome[2], // Patient ID
                            outcome[3], // Patient Name
                            outcome[4], // Date
                            outcome[5], // Services Provided
                            outcome[6], // Prescribed Medications
                            outcome.length > 7 ? outcome[7] : "N/A", // Consultation Notes
                            outcome.length > 8 ? outcome[8] : "Pending" // Status
                    );
                }
            }

        } catch (IOException e) {
            output.println("Error reading appointment outcomes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void updatePrescriptionStatus(Scanner scanner, PrintStream output) {
        try {
            Path outcomePath = Paths.get("data/AppointmentOutcomes.csv");
            List<String> lines = Files.readAllLines(outcomePath);

            if (lines.size() <= 1) {
                output.println("No appointment outcome records found.");
                return;
            }

            // Display the records for selection
            viewAppointmentOutcomeRecords(output, null); // Show all records for the pharmacist

            // Prompt for record selection
            output.print("Enter the Record ID to update the status to 'Dispensed': ");
            int recordId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            boolean recordFound = false;

            for (int i = 1; i < lines.size(); i++) {
                String[] outcome = lines.get(i).split(",");
                if (Integer.parseInt(outcome[0]) == recordId) {
                    // Update the status to Dispensed
                    outcome[8] = "Dispensed"; // Assuming status is at index 8
                    lines.set(i, String.join(",", outcome)); // Update the line
                    recordFound = true;
                    output.println("Status updated to 'Dispensed' for Record ID: " + recordId);
                    break;
                }
            }

            if (!recordFound) {
                output.println("Record ID not found.");
            } else {
                // Write changes back to the file
                Files.write(outcomePath, lines);
            }

        } catch (IOException e) {
            output.println("Error updating appointment outcome: " + e.getMessage());
            e.printStackTrace();
        }
    }
}