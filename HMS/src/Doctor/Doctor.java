package Doctor;

import User.*;
import Appointment.*;
import Records.*;

public class Doctor extends User {

	private Schedule schedule;
	private IAppointment apptHandler;
	private MedicalRecord medicalRecord;

	/**
	 * 
	 * @param hospitalId
	 * @param name
	 * @param gender
	 * @param age
	 */
	public Doctor(String hospitalId, String name, String gender, int age) {
		// TODO - implement Doctor.Doctor
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param apptHandler
	 */
	public Doctor(IAppointment apptHandler) {
		// TODO - implement Doctor.Doctor
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param schedule
	 */
	public Doctor(Schedule schedule) {
		// TODO - implement Doctor.Doctor
		throw new UnsupportedOperationException();
	}

	public List<String> viewPersonalSchedule() {
		// TODO - implement Doctor.viewPersonalSchedule
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param start
	 */
	public boolean updateAvailability(LocalDateTime start) {
		// TODO - implement Doctor.updateAvailability
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param patientId
	 * @param newDiagnosis
	 * @param prescriptions
	 * @param newTreatment
	 */
	public void updatePatientMedicalRecords(String patientId, String newDiagnosis, String prescriptions, String newTreatment) {
		// TODO - implement Doctor.updatePatientMedicalRecords
		throw new UnsupportedOperationException();
	}

	public void toString() {
		// TODO - implement Doctor.toString
		throw new UnsupportedOperationException();
	}

}