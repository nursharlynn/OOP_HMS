package Appointment;

public class CompletedAppointment extends Appointment {

	
	/**
	 * 
	 * @param appointment
	 * @param outcome
	 */
	public CompletedAppointment(Appointment appointment) {
		// TODO - implement CompletedAppointment.CompletedAppointment
		super(appointment.getStartTime(), appointment.getEndTime()); 
		throw new UnsupportedOperationException();
	}

	

}