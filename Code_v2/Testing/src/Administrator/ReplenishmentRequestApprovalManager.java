package Administrator;

import HMS.DataLoader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class ReplenishmentRequestApprovalManager {
    private String requestFilePath = "data/ReplenishmentRequest.csv";
    private DataLoader dataLoader;

    public ReplenishmentRequestApprovalManager(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public void approveReplenishmentRequests() {
        try {
            Path path = Paths.get(requestFilePath);
            List<String> lines = Files.readAllLines(path);

            // Check if there are any requests
            if (lines.size() <= 1) {
                System.out.println("No replenishment requests found.");
                return;
            }

            System.out.println("\n=== Select Replenishment Request to Approve ===");
            boolean hasPendingRequests = false;

            for (int i = 1; i < lines.size(); i++) {
                String[] data = lines.get(i).split(",");
                if (data.length == 6 && !data[5].trim().equalsIgnoreCase("Approved")) {

                    System.out.printf("%d. Request ID: %s, Medicine: %s, Request Amount: %s, Status: %s%n",
                            i,
                            data[0].trim(), // Request ID
                            data[1].trim(), // Medicine
                            data[2].trim(), // Request Amount
                            data[5].trim()); // Status
                    hasPendingRequests = true;
                }
            }

            if (!hasPendingRequests) {
                System.out.println("No requests to approve.");
                return;
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Select the number of the request to approve: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice < 1 || choice >= lines.size()) {
                System.out.println("Invalid selection.");
                return;
            }

            String[] selectedRequest = lines.get(choice).split(",");
            if (selectedRequest[5].trim().equalsIgnoreCase("Approved")) {
                System.out.println("This request has already been approved.");
                return;
            }

            // Approve the request
            selectedRequest[5] = "Approved";

            // Update stock or add new medicine
            updateStockOrAddMedicine(selectedRequest);

            // Update the CSV file with the new status
            updateRequestFile(lines, selectedRequest);

            System.out.println("Replenishment request approved successfully.");
        } catch (IOException e) {
            System.out.println("Error processing replenishment requests: " + e.getMessage());
        }
    }

    private void updateStockOrAddMedicine(String[] selectedRequest) {
        String medicineName = selectedRequest[1].trim();
        int requestAmount = Integer.parseInt(selectedRequest[2].trim());

        // Check if the medicine exists in the medicine list
        List<Medicine> medicines = dataLoader.getAllMedicines();
        boolean medicineExists = false;

        for (Medicine medicine : medicines) {
            if (medicine.getMedicineName().equalsIgnoreCase(medicineName)) {
                // Increase the stock by the requested amount
                medicine.addStock(requestAmount);
                System.out.println("Stock updated for " + medicineName + ". New stock: " + medicine.getStock());
                medicineExists = true;
                break;
            }
        }

        // If the medicine does not exist, prompt for low stock alert and add it
        if (!medicineExists) {
            System.out.println("Medicine " + medicineName + " does not exist in the inventory.");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the low stock level alert for " + medicineName + ": ");
            int lowStockLevelAlert = scanner.nextInt();
            scanner.nextLine();

            // Create a new Medicine object and add it to the list
            Medicine newMedicine = new Medicine(medicineName, requestAmount, lowStockLevelAlert);
            medicines.add(newMedicine); // Add the new medicine to the list

            System.out.println("New medicine " + medicineName + " added with initial stock: " + requestAmount);
        }

        // Save the updated medicines list to the file
        dataLoader.saveMedicines(medicines);
    }

    private void updateRequestFile(List<String> lines, String[] selectedRequest) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(requestFilePath))) {
            writer.write("Request ID,Medicine,Request Amount,Pharmacist ID,Pharmacist Name,Status");
            writer.newLine();
            for (int i = 1; i < lines.size(); i++) {
                String[] data = lines.get(i).split(",");
                if (data[0].equals(selectedRequest[0])) {
                    writer.write(String.join(",", selectedRequest)); // Write the updated request
                } else {
                    writer.write(lines.get(i));
                }
                writer.newLine();
            }
        }
    }
}