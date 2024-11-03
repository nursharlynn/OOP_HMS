package Appointment;

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

	public void toString() {
		// TODO - implement TimeSlot.toString
		throw new UnsupportedOperationException();
	}

}