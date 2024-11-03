package Patient;

import Appointment.*;

public interface IAppointmentsHandler {

	/**
	 * 
	 * @param patient
	 * @param doctorName
	 * @param date
	 * @param time
	 */
	void scheduleAppointments(Patient patient, String doctorName, LocalDateTime date, LocalDateTime time);

	/**
	 * 
	 * @param AppointmentsID
	 * @param scheduledAppointments
	 */
	void rescheduleAppointments(int AppointmentsID, ArrayList<ViewScheduledAppointments> scheduledAppointments);

	/**
	 * 
	 * @param AppointmentsID
	 */
	boolean cancelAppointments(int AppointmentsID);

	/**
	 * 
	 * @param patientId
	 */
	void getPastOutcomes(String patientId);

}