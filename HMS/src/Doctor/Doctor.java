package Doctor;

import User.*;

import java.time.LocalDateTime;
import java.util.List;

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
	public Doctor(String hospitalId, String password, String name, String gender, int age) {
		// TODO - implement Doctor.Doctor
		super(hospitalId, password, name, gender, age);
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param apptHandler
	 */
	public void setApptHandler(IAppointment apptHandler) {
        this.apptHandler = apptHandler;
    }

	/**
	 * 
	 * @param schedule
	 */
	public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
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

	public String toString() {
		// TODO - implement Doctor.toString
		throw new UnsupportedOperationException();
	}

}