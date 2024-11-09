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
	// Main constructor that initializes all attributes, calling the superclass constructor
    public Doctor(String hospitalId, String password, String name, String gender, int age, Schedule schedule, IAppointment apptHandler) {
        super(hospitalId, password, name, gender, age); // Call User constructor
        this.schedule = schedule;
        this.apptHandler = apptHandler;
    }

    // Constructor when only appointment handler is provided
    public Doctor(String hospitalId, String password, String name, String gender, int age, IAppointment apptHandler) {
        super(hospitalId, password, name, gender, age); // Call User constructor
        this.apptHandler = apptHandler;
    }

    // Constructor when only schedule is provided
    public Doctor(String hospitalId, String password, String name, String gender, int age, Schedule schedule) {
        super(hospitalId, password, name, gender, age); // Call User constructor
        this.schedule = schedule;
    }

	public List<TimeSlot> viewPersonalSchedule() {
		 return schedule.getDailySchedule();
	}

	/**
	 * 
	 * @param start
	 */
	

	/**
	 * 
	 * @param patientId
	 * @param newDiagnosis
	 * @param prescriptions
	 * @param newTreatment
	 */
	public void updatePatientMedicalRecords(String patientId, String newDiagnosis, String prescriptions, String newTreatment) {
		 medicalRecord.updateRecord(patientId, newDiagnosis, prescriptions, newTreatment); // Assumes MedicalRecord has this method
	}
	
	
	public String toString() {
		return "Doctor: " + getName() + " (" + gethospitalId() + ")";
	}

	public String gethospitalId() {
		 return gethospitalId();
	}

}