package Pharmacist;

import Administrator.Medicine;
import HMS.DataLoader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class InventoryManager implements IInventoryManager {
    DataLoader dataLoader = new DataLoader(null);
    private List<Medicine> medicines;

    public InventoryManager(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    @Override
    public void viewInventory() {
        List<Medicine> medicines = dataLoader.getAllMedicines();

        if (medicines.isEmpty()) {
            System.out.println("No medicines available in inventory.");
            return;
        }

        System.out.println("\n=== Medicine Inventory ===");
        System.out.printf("%-20s %-10s %-15s%n", "Medicine Name", "Stock", "Low Stock Alert");
        System.out.println("-".repeat(50));
        for (Medicine medicine : medicines) {
            System.out.printf("%-20s %-10d %-15d%n",
                    medicine.getMedicineName(),
                    medicine.getStock(),
                    medicine.getLowStockLevelAlert());
        }
    }

    @Override
    public void submitReplenishmentRequest(Scanner scanner, String pharmacistId, String pharmacistName) {
        System.out.print("Enter the name of the medicine to request replenishment: ");
        String medicineName = scanner.nextLine().trim();
    
        int requestAmount = 0;
        boolean validInput = false;
    
        while (!validInput) {
            System.out.print("Enter the amount to request (must be greater than 0): ");
            try {
                requestAmount = scanner.nextInt();
                scanner.nextLine(); 
                if (requestAmount > 0) {
                    validInput = true; 
                } else {
                    System.out.println("Please enter a number greater than 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for request amount. Please enter a valid number.");
                scanner.nextLine(); 
            }
        }

        int replenishmentId = generateReplenishmentId();

        String status = "Pending Approval";

        try {
            writeReplenishmentRequest(replenishmentId, medicineName, requestAmount, pharmacistId, pharmacistName,
                    status);
            System.out.println("Replenishment request submitted successfully!");
        } catch (IOException e) {
            System.out.println("Error processing replenishment request: " + e.getMessage());
        }
    }

    private int generateReplenishmentId() {
        Path path = Path.of("data/ReplenishmentRequest.csv");
        try {
            if (!Files.exists(path) || Files.size(path) == 0) {
                return 1;
            }
            return (int) Files.lines(path).count() + 1;
        } catch (IOException e) {
            System.out.println("Error generating replenishment ID: " + e.getMessage());
            return 1;
        }
    }

    private void writeReplenishmentRequest(int replenishmentId, String medicineName, int requestAmount,
            String pharmacistId, String pharmacistName, String status) throws IOException {
        Path path = Path.of("data/ReplenishmentRequest.csv");
        String requestLine = String.format("%d,%s,%d,%s,%s,%s%n", replenishmentId, medicineName, requestAmount,
                pharmacistId, pharmacistName, status);

        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE)) {
                writer.write("ReplenishmentID,Medicine,RequestAmount,PharmacistID,Pharmacist,Status\n"); // Write header
            }
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            writer.write(requestLine);
        }
    }

    public void processMedications(List<String> prescribedMedications, boolean isDispensing) {
        for (String medicationEntry : prescribedMedications) {
            String[] parts = medicationEntry.split(":");
            String medicineName = parts[0].trim();
            int quantity = Integer.parseInt(parts[1].trim());

            for (Medicine medicine : medicines) {
                if (medicine.getMedicineName().equalsIgnoreCase(medicineName)) {
                    if (isDispensing) {

                        if (medicine.getStock() >= quantity) {
                            medicine.reduceStock(quantity);
                            System.out.printf("Dispensed %d of %s. Remaining stock: %d%n", quantity, medicineName,
                                    medicine.getStock());
                        } else {
                            System.out.printf("Not enough stock for %s. Available: %d, Requested: %d%n", medicineName,
                                    medicine.getStock(), quantity);
                        }
                    }
                    break;
                }
            }
        }
    }
}