package Pharmacist;

import Administrator.Medicine;
import Appointment.*;
import HMS.DataLoader;
import User.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pharmacist extends User {

	private Scanner scanner;
	private int age;
	private IInventoryManager inventoryManager;

	public Pharmacist(String hospitalId, String password, String name, String gender, int age) {
		super(hospitalId, password, name, gender, "Pharmacist", age);
		this.scanner = new Scanner(System.in);
		this.age = age;
		this.inventoryManager = new InventoryManager(new ArrayList<>());
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
				inventoryManager.viewInventory();
				break;
			case 2:
				inventoryManager.submitReplenishmentRequest(scanner, getHospitalId(), getName());
				break;
			case 3:
				AppointmentOutcomeViewer.viewAppointmentOutcomeRecords(System.out, null);
				break;
			case 4:
				AppointmentOutcomeViewer.updatePrescriptionStatus(scanner, System.out);
				break;
			case 5:
				dispenseMedications();
				break;
			case 6:
				checkLowStockAlerts();
				break;
			case 7:
				System.out.println("Logging out...");
				return;
			default:
				System.out.println("Invalid choice. Please try again.");
		}
	}

	private void dispenseMedications() {
		DataLoader dataLoader = new DataLoader(null);
		List<Medicine> medicines = dataLoader.getAllMedicines();
		List<String> prescribedMedications = readPrescribedMedications();

		for (String prescribed : prescribedMedications) {
			String[] parts = prescribed.split(":");
			if (parts.length == 2) {
				String medicineName = parts[0].trim();
				int quantity;

				try {
					quantity = Integer.parseInt(parts[1].trim());
				} catch (NumberFormatException e) {
					System.out.println("Invalid quantity for " + medicineName + ". Skipping.");
					continue;
				}

				for (Medicine medicine : medicines) {
					if (medicine.getMedicineName().equalsIgnoreCase(medicineName)) {
						if (medicine.getStock() >= quantity) {
							medicine.reduceStock(quantity);
							System.out.println("Dispensed " + quantity + " of " + medicineName);
							System.out.println("Remaining stock for " + medicineName + ": " + medicine.getStock());
						} else {
							System.out.println(
									"Not enough stock for " + medicineName + ". Available: " + medicine.getStock());
						}
						break;
					}
				}
			}
		}
		dataLoader.saveMedicines(medicines);
	}

	public void checkLowStockAlerts() {
		DataLoader dataLoader = new DataLoader(null);
		List<Medicine> medicines = dataLoader.getAllMedicines();

		System.out.println("\n=== Low Stock Alerts ===");
		System.out.printf("%-20s %-10s %-15s%n", "Medicine Name", "Stock Level", "Low Stock Alert");
		System.out.println("-".repeat(50));

		for (Medicine medicine : medicines) {
			if (medicine.isLowStock()) {
				System.out.printf("%-20s %-10d %-15d%n",
						medicine.getMedicineName(),
						medicine.getStock(),
						medicine.getLowStockLevelAlert());
			}
		}
	}

	private List<String> readPrescribedMedications() {
		List<String> prescribedMedications = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("data/AppointmentOutcomes.csv"))) {
			String line;
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length > 6 && "Pending".equalsIgnoreCase(data[8])) {
					String medications = data[6];
					String[] meds = medications.split("\\|");
					for (String med : meds) {
						med = med.trim();
						if (med.contains(":")) {
							String[] parts = med.split(":");
							if (parts.length == 2) {
								String quantityString = parts[1].trim();
								try {
									int quantity = Integer.parseInt(quantityString);
									prescribedMedications.add(med);
								} catch (NumberFormatException e) {
								}
							}
						}
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading Appointment Outcomes: " + e.getMessage());
		}
		return prescribedMedications;
	}
}
