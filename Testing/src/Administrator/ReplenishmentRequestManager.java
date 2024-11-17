package Administrator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReplenishmentRequestManager implements IReplenishmentRequestManager {
    private String requestFilePath = "data/ReplenishmentRequest.csv";

    public void viewReplenishmentRequests() {
        try {
            Path path = Path.of(requestFilePath);
            List<String> lines = Files.readAllLines(path);

            // Check if there are any requests
            if (lines.size() <= 1) {
                System.out.println("No replenishment requests found.");
                return;
            }

            System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s%n",
                    "Request ID", "Medicine", "Request Amount", "Pharmacist ID", "Pharmacist Name", "Status");
            System.out.println("-".repeat(100));

            // Print each request's details
            for (String line : lines.subList(1, lines.size())) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s%n",
                            data[0].trim(), // Request ID
                            data[1].trim(), // Medicine
                            data[2].trim(), // Request Amount
                            data[3].trim(), // Pharmacist ID
                            data[4].trim(), // Pharmacist Name
                            data[5].trim()); // Status
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading replenishment requests: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred.");
        }
    }

}