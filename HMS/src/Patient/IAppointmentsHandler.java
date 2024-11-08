package Patient;

import java.util.List;

public interface IAppointmentsHandler {

	/**
	 * 
	 * @param patient
	 * @param doctorName
	 * @param date
	 * @param time
	 */
	void scheduleAppointments(Patient patient, int appointmentId);
	/**
	 * 
	 * @param AppointmentsID
	 * @param scheduledAppointments
	 */
	void rescheduleAppointments(String hospitalId);


	/**
	 * 
	 * @param AppointmentsID
	 */
	void cancelAppointment(String hospitalId);

	/**
	 * 
	 * @param patientId
	 */
	void getPastOutcomes(String patientId);

	List<String[]> viewAvailableSlots();

	void viewScheduledAppointments(String hospitalId);
	

}