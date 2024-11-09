package Appointment;

import java.time.LocalDateTime;

import Doctor.Doctor;
import Patient.Patient;

public class CompletedAppointment extends Appointment {

	private AppointmentOutcome outcome;


	public CompletedAppointment(Appointment appointment, AppointmentOutcome outcome) {
		// TODO - implement CompletedAppointment.CompletedAppointment
		throw new UnsupportedOperationException();
	}

	public CompletedAppointment(Patient patient, Doctor doctor, LocalDateTime now, String string, String string2,
			String string3, String string4) {
		return;
		}

	public AppointmentOutcome getOutcome() {
		return this.outcome;
	}

}