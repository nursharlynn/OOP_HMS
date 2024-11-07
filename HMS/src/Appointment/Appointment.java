package Appointment;

import Patient.*;

import java.time.LocalDateTime;

import Doctor.*;

public class Appointment extends TimeSlot {

	private int id;
	private Patient patient;
	private Doctor doctor;
	private LocalDateTime date;

	/**
	 * 
	 * @param start
	 * @param end
	 */
	public Appointment(LocalDateTime start, LocalDateTime end) {
		// TODO - implement Appointment.Appointment
		super(start, end);
		throw new UnsupportedOperationException();
	}

	public Patient getPatient() {
		return this.patient;
	}

	/**
	 * 
	 * @param doctor
	 */
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Doctor getDoctor() {
		return this.doctor;
	}

	public LocalDateTime getDate() {
		return this.date;
	}

	/**
	 * 
	 * @param date
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public int generateAppointmentId() {
		// TODO - implement Appointment.generateAppointmentId
		throw new UnsupportedOperationException();
	}

	public int getId() {
		return this.id;
	}

	public String toString() {
		// TODO - implement Appointment.toString
		throw new UnsupportedOperationException();
	}

}