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
	void recordAppointmentOutcome(Doctor doctor);

	/**
	 * 
	 * @param patientId
	 */
	ArrayList<CompletedAppointment> viewPastAppointmentOutcomeRecord(String patientId);

	ArrayList<Appointment> viewUpcomingAppointments(Doctor doctor);

	

}