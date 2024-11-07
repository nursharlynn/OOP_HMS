package Pharmacist;

import User.*;
import Appointment.*;

public class Pharmacist extends User {

	private ArrayList<Medication> inventory;
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
		// TODO - implement Pharmacist.Pharmacist
		throw new UnsupportedOperationException();
	}

	public void getAllAppointmentOutcomeRecord() {
		// TODO - implement Pharmacist.getAllAppointmentOutcomeRecord
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param appointmentsId
	 * @param status
	 */
	public void updatePrescriptionStatus(int appointmentsId, String status) {
		// TODO - implement Pharmacist.updatePrescriptionStatus
		throw new UnsupportedOperationException();
	}

	public List<Medication> viewInventory() {
		// TODO - implement Pharmacist.viewInventory
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param medication
	 * @param quantity
	 */
	public void submitReplenishmentRequest(String medication, int quantity) {
		// TODO - implement Pharmacist.submitReplenishmentRequest
		throw new UnsupportedOperationException();
	}

	public void toString() {
		// TODO - implement Pharmacist.toString
		throw new UnsupportedOperationException();
	}

}