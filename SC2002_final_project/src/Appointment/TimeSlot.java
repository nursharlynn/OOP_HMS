package Appointment;

import java.io.ObjectInputFilter.Status;
import java.time.LocalDateTime;

public class TimeSlot {

	Schedule contains;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Status status;

	/**
	 * 
	 * @param startTime
	 * @param endTime
	 */
	public TimeSlot() {
        // Initialize default values if necessary
    }
	public TimeSlot(LocalDateTime startTime, LocalDateTime endTime) {
		// TODO - implement TimeSlot.TimeSlot
		throw new UnsupportedOperationException();
	}

	public Status getStatus() {
		return this.status;
	}

	/**
	 * 
	 * @param status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * 
	 * @param date
	 */
	public void setDate(LocalDateTime date) {
		// TODO - implement TimeSlot.setDate
		throw new UnsupportedOperationException();
	}

	public LocalDateTime getStartTime() {
		return this.startTime;
	}

	public LocalDateTime getEndTime() {
		return this.endTime;
	}

	public String toString() {
		// TODO - implement TimeSlot.toString
		throw new UnsupportedOperationException();
	}

}