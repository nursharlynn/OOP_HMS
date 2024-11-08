package Pharmacist;

import Administrator.Medicine;
import Appointment.*;
import User.*;
import java.util.ArrayList;
import java.util.List;

public class Pharmacist extends User {

	private ArrayList<Medicine> inventory;
	private ArrayList<Appointment> appointments;

	public Pharmacist(String hospitalId, String password, String name, String gender) {
		super(hospitalId, password, name, gender);
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
            System.out.println("View Inventory - Not implemented yet");
            break;
        case 2:
            System.out.println("Submit Replenishment Request - Not implemented yet");
            break;
        case 3:
            System.out.println("View Appointment Outcome Records - Not implemented yet");
            break;
        case 4:
            System.out.println("Update Prescription Status - Not implemented yet");
            break;
        case 5:
            System.out.println("Dispense Medications - Not implemented yet");
            break;
        case 6:
            System.out.println("Check Low Stock Alerts - Not implemented yet");
            break;
        case 7:
            System.out.println("Logging out...");
            return; // This will exit the menu loop
        default:
            System.out.println("Invalid choice. Please try again.");
    }
}

	public void getAllAppointmentOutcomeRecord() {
		System.out.println("Get All Appointment Outcome Record - Not implemented");
	}

	public void updatePrescriptionStatus(int appointmentsId, String status) {
		System.out.println("Update Prescription Status - Not implemented");
	}

	public List<Medicine> viewInventory() {
		System.out.println("View Inventory - Not implemented");
		return null;
	}

	public void submitReplenishmentRequest(String medication, int quantity) {
		System.out.println("Submit Replenishment Request - Not implemented");
	}

	@Override
	public String toString() {
		return "Pharmacist: " + getName() + " (ID: " + getHospitalId() + ")";
	}
}