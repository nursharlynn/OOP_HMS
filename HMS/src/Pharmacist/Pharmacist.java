package Pharmacist;

import Administrator.Medicine;
import Appointment.*;
import HMS.DataLoader;
import User.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Pharmacist extends User {

	private ArrayList<Medicine> inventory;
	private ArrayList<Appointment> appointments;
	private Scanner scanner;

	public Pharmacist(String hospitalId, String password, String name, String gender) {
		super(hospitalId, password, name, gender);
		this.scanner = new Scanner(System.in); 
	}

	@Override
	public void displayMenu() {
		System.out.println("\n--- Pharmacist Menu ---");
		System.out.println("1. View Inventory");
		System.out.println("2. Submit Replenishment Request");
		System.out.println("3. View Appointment Outcome Records");
		System.out.println("4. Update Prescription Status");
		System.out.println("5. Dispense Medications");
		System.out.println("6. Check Low Stock Alerts");
		System.out.println("7. Logout");
	}

	@Override
public void handleMenuChoice(int choice) {
    switch (choice) {
        case 1:
			viewInventory();
			break;
        case 2:
			submitReplenishmentRequest();
			break;
        case 3:
			AppointmentOutcomeViewer.viewAppointmentOutcomeRecords(System.out, null);
			break;
        case 4:
			AppointmentOutcomeViewer.updatePrescriptionStatus(scanner, System.out);
            break;
        case 5:
            System.out.println("Dispense Medications - Not implemented yet");
            break;
        case 6:
			checkLowStockAlerts(); // Call the new method for checking low stock
			break;
        case 7:
            System.out.println("Logging out...");
            return; // This will exit the menu loop
        default:
            System.out.println("Invalid choice. Please try again.");
    }
}


	private void viewInventory() {
        DataLoader dataLoader = new DataLoader(null); // Pass null or a valid LoginSystem instance
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

	public void submitReplenishmentRequest() {
		// Prompt for medicine name
		System.out.print("Enter the name of the medicine to request replenishment: ");
		String selectedMedicine = scanner.nextLine().trim();
	
		// Prompt for request amount
		System.out.print("Enter the amount to request: ");
		int requestAmount = 0; // Initialize requestAmount
		try {
			requestAmount = scanner.nextInt();
			scanner.nextLine(); // Consume newline
		} catch (InputMismatchException e) {
			System.out.println("Invalid input for request amount. Please enter a number.");
			scanner.nextLine(); // Clear the invalid input
			return; // Exit the method early
		}
	
		// Generate Replenishment ID
		int replenishmentId = generateReplenishmentId();
	
		// Prepare the request data
		String pharmacistId = getHospitalId();
		String pharmacistName = getName();
		String status = "Pending Approval";
	
		// Write to the ReplenishmentRequest.csv file
		try {
			writeReplenishmentRequest(replenishmentId, selectedMedicine, requestAmount, pharmacistId, pharmacistName, status);
			System.out.println("Replenishment request submitted successfully!");
		} catch (IOException e) {
			System.out.println("Error processing replenishment request: " + e.getMessage());
			e.printStackTrace(); // Print stack trace for debugging
		}
	}

	@Override
    public String toString() {
        return "Pharmacist: " + getName() + " (ID: " + getHospitalId() + ")";
    }

	private int generateReplenishmentId() {
        // Implement logic similar to Doctor.getNextAppointmentId
        Path path = Path.of("data/ReplenishmentRequest.csv");
        try {
            if (!Files.exists(path) || Files.size(path) == 0) {
                return 1; // Start from 1 if the file doesn't exist or is empty
            }
            return Files.lines(path)
                .skip(1) // Skip header
                .map(line -> line.split(",")[0]) // Get the first column (ReplenishmentID)
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0) + 1; // Return max ID + 1
        } catch (IOException e) {
            System.out.println("Error generating replenishment ID: " + e.getMessage());
            return 1; // Default to 1 in case of error
        }
    }

	private void writeReplenishmentRequest(int replenishmentId, String medicine, int requestAmount, String pharmacistId, String pharmacistName, String status) throws IOException {
		Path path = Path.of("data/ReplenishmentRequest.csv");
		String requestLine = String.format("%d,%s,%d,%s,%s,%s%n", replenishmentId, medicine, requestAmount, pharmacistId, pharmacistName, status);
	
		// Ensure the file exists and has a header
		if (!Files.exists(path)) {
			Files.createDirectories(path.getParent());
			try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE)) {
				writer.write("ReplenishmentID,Medicine,RequestAmount,PharmacistID,Pharmacist,Status\n"); // Write header
			}
		}
		// Append the new request
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
			writer.write(requestLine);
		}
	}

	public void checkLowStockAlerts() {
		DataLoader dataLoader = new DataLoader(null); // Pass null or a valid LoginSystem instance
		List<Medicine> medicines = dataLoader.getAllMedicines();
	
		if (medicines.isEmpty()) {
			System.out.println("No medicines available in inventory.");
			return;
		}
	
		System.out.println("\n=== Low Stock Alerts ===");
		System.out.printf("%-20s %-10s%n", "Medicine Name", "Stock Level");
		System.out.println("-".repeat(30));
	
		boolean lowStockFound = false;
	
		for (Medicine medicine : medicines) {
			if (medicine.isLowStock()) { // Check if stock is low
				System.out.printf("%-20s %-10d%n", medicine.getMedicineName(), medicine.getStock());
				lowStockFound = true;
			}
		}
	
		if (!lowStockFound) {
			System.out.println("All medicines are above the low stock level.");
		}
	}

}
	
