package Patient;

import java.time.LocalDateTime;
import java.util.ArrayList;

import Appointment.*;

public class PatientAppointmentManager implements IAppointmentsHandler {

	private PastAppointmentOutcomeRecord pastOutcomes;
	private ArrayList<Appointment> Appointments;

	/**
	 * 
	 * @param patient
	 * @param doctorName
	 * @param date
	 * @param time
	 */
	public void scheduleAppointments(Patient patient, String doctorName, LocalDateTime date, LocalDateTime time) {
		// TODO - implement PatientAppointmentManager.scheduleAppointments
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param AppointmentsID
	 * @param scheduledAppointments
	 */
	public void rescheduleAppointments(int AppointmentsID, ArrayList<ViewScheduledAppointments> scheduledAppointments) {
		// TODO - implement PatientAppointmentManager.rescheduleAppointments
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param AppointmentsID
	 */
	public boolean cancelAppointments(int AppointmentsID) {
		// TODO - implement PatientAppointmentManager.cancelAppointments
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param patientId
	 */
	public void getPastOutcomes(String patientId) {
		// TODO - implement PatientAppointmentManager.getPastOutcomes
		throw new UnsupportedOperationException();
	}

}