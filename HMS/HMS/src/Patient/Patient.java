package Patient;

import User.*;
import Appointment.*;
import Records.*;

public class Patient extends User {

	private LocalDateTime dateofBirth;
	private String gender;
	private String contact;
	private String emailAddress;
	private String bloodType;
	private IAppointmentsHandler apptHandler;
	private ArrayList<ViewScheduledAppointments> scheduledAppointments;
	private MedicalRecord medicalRecord;
	private Schedule schedule;

	/**
	 * 
	 * @param hospitalId
	 * @param password
	 * @param dateOfBirth
	 * @param gender
	 * @param emailAddress
	 * @param bloodType
	 */
	public Patient(String hospitalId, String password, LocalDateTime dateOfBirth, String gender, Stromg emailAddress, String bloodType) {
		// TODO - implement Patient.Patient
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param schedule
	 */
	public Patient(Schedule schedule) {
		// TODO - implement Patient.Patient
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param apptHandler
	 */
	public Patient(IAppointmentsHandler apptHandler) {
		// TODO - implement Patient.Patient
		throw new UnsupportedOperationException();
	}

	public String getName() {
		// TODO - implement Patient.getName
		throw new UnsupportedOperationException();
	}

	public Date getDateofBirth() {
		// TODO - implement Patient.getDateofBirth
		throw new UnsupportedOperationException();
	}

	public String getGender() {
		return this.gender;
	}

	public String getContact() {
		return this.contact;
	}

	/**
	 * 
	 * @param contact
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	/**
	 * 
	 * @param emailAddress
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPatientID() {
		// TODO - implement Patient.getPatientID
		throw new UnsupportedOperationException();
	}

	public String getBloodType() {
		return this.bloodType;
	}

	public void viewAvailableSlots() {
		// TODO - implement Patient.viewAvailableSlots
		throw new UnsupportedOperationException();
	}

}