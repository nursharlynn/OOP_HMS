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

                // Correct the date format and trim whitespace
                String date = formatDate(outcome[4].trim());

                if (patientId == null || outcome[2].equals(patientId)) {
                    output.printf("%-10s %-15s %-15s %-15s %-20s %-25s %-30s %-30s %-15s%n",
                            outcome[0],
                            outcome[1],
                            outcome[2],
                            outcome[3],
                            date,
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
                    // Update the status field to "Dispensed"
                    if (outcome.length > 8) {
                        outcome[8] = "Dispensed";
                    } else {
                        // Extend the array to add the status if missing
                        String[] updatedOutcome = new String[outcome.length + 1];
                        System.arraycopy(outcome, 0, updatedOutcome, 0, outcome.length);
                        updatedOutcome[8] = "Dispensed";
                        outcome = updatedOutcome;
                    }
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

    // Utility method to correct and format the date
    private static String formatDate(String date) {
        try {
            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                // Convert from yyyy-MM-dd to MM/dd/yyyy
                String[] parts = date.split("-");
                return parts[1] + "/" + parts[2] + "/" + parts[0];
            }
        } catch (Exception e) {
            // Return the original date if formatting fails
            return date;
        }
        return date;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PrintStream output = System.out;

        while (true) {
            output.println("\n=== Appointment Outcome Viewer ===");
            output.println("1. View Appointment Outcome Records");
            output.println("2. Update Prescription Status");
            output.println("3. Exit");
            output.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    output.print("Enter Patient ID to filter by (or press Enter to view all): ");
                    String patientId = scanner.nextLine();
                    if (patientId.isEmpty()) {
                        patientId = null; // Show all records if no input is provided
                    }
                    viewAppointmentOutcomeRecords(output, patientId);
                    break;
                case 2:
                    updatePrescriptionStatus(scanner, output);
                    break;
                case 3:
                    output.println("Exiting the program. Goodbye!");
                    scanner.close();
                    return;
                default:
                    output.println("Invalid choice. Please try again.");
            }
        }
    }
}
