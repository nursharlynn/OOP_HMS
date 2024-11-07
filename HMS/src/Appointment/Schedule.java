package Appointment;

import java.time.LocalDateTime;
import java.util.List;

import Doctor.*;

public class Schedule {

	private List<TimeSlot> schedule;
	private LocalDateTime date;

	public Schedule() {
		// TODO - implement Schedule.Schedule
		throw new UnsupportedOperationException();
	}

	public LocalDateTime getDate() {
		return this.date;
	}

	/**
	 * 
	 * @param startOfDay
	 * @param hoursOpen
	 */
	public List<TimeSlot> generateTimeslots(LocalDateTime startOfDay, int hoursOpen) {
		// TODO - implement Schedule.generateTimeslots
		throw new UnsupportedOperationException();
	}

	public List<TimeSlot> generateDefaultSchedule() {
		// TODO - implement Schedule.generateDefaultSchedule
		throw new UnsupportedOperationException();
	}

	public List<TimeSlot> generateWeeklySchedule() {
		// TODO - implement Schedule.generateWeeklySchedule
		throw new UnsupportedOperationException();
	}

	public List<TimeSlot> getDailySchedule() {
		// TODO - implement Schedule.getDailySchedule
		throw new UnsupportedOperationException();
	}

	public List<TimeSlot> getWeeklySchedule() {
		// TODO - implement Schedule.getWeeklySchedule
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param start
	 * @param doctor
	 */
	public boolean updateTimeslot(LocalDateTime start, Doctor doctor) {
		// TODO - implement Schedule.updateTimeslot
		throw new UnsupportedOperationException();
	}

}