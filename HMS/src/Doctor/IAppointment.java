package Doctor;

import Appointment.*;
import java.util.ArrayList;

public interface IAppointment {

	/**
	 * 
	 * @param doctor
	 */
	boolean reviewAppointmentRequests(Doctor doctor);

	/**
	 * 
	 * @param doctor
	 * @param appointmentId
	 */
	void recordAppointmentOutcome(Doctor doctor, int appointmentId);

	/**
	 * 
	 * @param patientId
	 */
	ArrayList<CompletedAppointment> viewPastAppointmentOutcomeRecord(String patientId);

	

}