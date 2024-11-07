package Appointment;

public class CompletedAppointment extends Appointment {

	private AppointmentOutcome outcome;

	/**
	 * 
	 * @param appointment
	 * @param outcome
	 */
	public CompletedAppointment(Appointment appointment, AppointmentOutcome outcome) {
		// TODO - implement CompletedAppointment.CompletedAppointment
		super(appointment.getStartTime(), appointment.getEndTime()); 
		throw new UnsupportedOperationException();
	}

	public AppointmentOutcome getOutcome() {
		return this.outcome;
	}

}