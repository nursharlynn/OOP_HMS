package Patient;

import User.*;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;

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
	public Patient(String hospitalId, String password, LocalDateTime dateOfBirth, String gender, String emailAddress, String bloodType) {
		// TODO - implement Patient.Patient
		super(hospitalId, password, bloodType, gender, calculateAge(dateOfBirth)); // calculate age from DOB
		this.emailAddress = emailAddress;
        this.bloodType = bloodType;
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param schedule
	 */
	 public void setSchedule(Schedule schedule) {
		// TODO - implement Patient.Patient
		this.schedule = schedule;
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param apptHandler
	 */
	 public void setApptHandler(IAppointmentsHandler apptHandler) {
		// TODO - implement Patient.Patient
		this.apptHandler = apptHandler;
		throw new UnsupportedOperationException();
	}

	public String getName() {
		// TODO - implement Patient.getName
		throw new UnsupportedOperationException();
	}

	public LocalDateTime getDateofBirth() {
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
	
	// Helper method to calculate age from dateOfBirth
    private static int calculateAge(LocalDateTime dateOfBirth) {
        return Period.between(dateOfBirth.toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();
    }

}