package Doctor;

import java.util.ArrayList;

import Appointment.*;

public class DoctorAppointmentManager implements IAppointment {

	private ArrayList<Appointment> appointments;
	private ArrayList<ViewScheduledAppointments> scheduledAppointments;
	private PastAppointmentOutcomeRecord pastOutcomes;

	/**
	 * 
	 * @param doctor
	 */
	public boolean reviewAppointmentRequests(Doctor doctor) {
		// TODO - implement DoctorAppointmentManager.reviewAppointmentRequests
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param doctor
	 * @param appointmentId
	 */
	public void recordAppointmentOutcome(Doctor doctor, int appointmentId) {
		// TODO - implement DoctorAppointmentManager.recordAppointmentOutcome
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param patientId
	 */
	public ArrayList<CompletedAppointment> viewPastAppointmentOutcomeRecord(String patientId) {
		// TODO - implement DoctorAppointmentManager.viewPastAppointmentOutcomeRecord
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param doctor
	 */
	public ArrayList<Appointment> viewUpcomingAppointments(Doctor doctor) {
		// TODO - implement DoctorAppointmentManager.viewUpcomingAppointments
		throw new UnsupportedOperationException();
	}

}