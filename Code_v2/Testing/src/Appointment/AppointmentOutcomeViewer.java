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

                if (patientId == null || outcome[2].equals(patientId)) {
                    output.printf("%-10s %-15s %-15s %-15s %-20s %-25s %-30s %-30s %-15s%n",
                            outcome[0],
                            outcome[1],
                            outcome[2],
                            outcome[3],
                            outcome[4],
                            outcome[5],
                            outcome[6],
                            outcome.length > 7 ? outcome[7] : "N/A",
                            outcome.length > 8 ? outcome[8] : "Pending");
                }
            }

        } catch (IOException e) {
            output.println("Error reading appointment outcomes: " + e.getMessage());
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

            viewAppointmentOutcomeRecords(output, null);

            output.print("Enter the Record ID to update the status to 'Dispensed': ");
            int recordId = scanner.nextInt();
            scanner.nextLine();

            boolean recordFound = false;

            for (int i = 1; i < lines.size(); i++) {
                String[] outcome = lines.get(i).split(",");
                if (Integer.parseInt(outcome[0]) == recordId) {
                    outcome[8] = "Dispensed";
                    lines.set(i, String.join(",", outcome));
                    recordFound = true;
                    output.println("Status updated to 'Dispensed' for Record ID: " + recordId);
                    break;
                }
            }

            if (!recordFound) {
                output.println("Record ID not found.");
            } else {
                Files.write(outcomePath, lines);
            }

        } catch (IOException e) {
            output.println("Error updating appointment outcome: " + e.getMessage());
        }
    }
}