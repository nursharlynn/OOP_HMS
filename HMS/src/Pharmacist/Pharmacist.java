package Pharmacist;

import User.*;
import java.util.ArrayList;
import java.util.List;

import Administrator.Medicine;
import Appointment.*;

public class Pharmacist extends User {

	private ArrayList<Medicine> inventory;
	private ArrayList<Appointment> appointments;

	/**
	 * 
	 * @param hospitalId
	 * @param password
	 * @param name
	 * @param gender
	 * @param age
	 */
	public Pharmacist(String hospitalId, String password, String name, String gender, int age) {
		super(hospitalId, "password", name, gender, age);
        this.inventory = new ArrayList<>();
        this.appointments = new ArrayList<>();
	}

	public void getAllAppointmentOutcomeRecord() {
		for (Appointment appointment : appointments) {
            System.out.println(appointment.toString()); // Assumes Appointment has a proper toString method
        }
	}

	/**
	 * 
	 * @param appointmentsId
	 * @param status
	 */
	public void updatePrescriptionStatus(int appointmentsId, String status) {
		for (Appointment appointment : appointments) {
            if (appointment.getId() == appointmentsId) {
                appointment.setPrescriptionStatus(status); // Assuming Appointment has this method
                System.out.println("Prescription status updated to " + status);
                return;
            }
        }
        System.out.println("Appointment ID not found.");
	}

	public List<Medicine> viewInventory() {
		return inventory;
	}

	/**
	 * 
	 * @param medication
	 * @param quantity
	 */
	public void submitReplenishmentRequest(String medicineName, int quantity) {
		 ReplenishmentRequest request = new ReplenishmentRequest(medicineName,this,quantity);
	        System.out.println("Replenishment request submitted for " + medicineName + ", quantity: " + quantity);
	}

	public String toString() {
		return "Pharmacist: " + getName() + " (" + gethospitalId() + ")";
	}

	private String gethospitalId() {
		 return gethospitalId();
	}

}