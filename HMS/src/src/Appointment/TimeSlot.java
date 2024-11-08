package Appointment;

import java.time.LocalDateTime;

public class TimeSlot {

    Schedule contains;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Status status;

    public TimeSlot(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        // Default status is Available
        this.status = Status.Available;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDate(LocalDateTime date) {
        // Update start and end times based on the new date
        long hoursDifference = startTime.getHour();
        long minutesDifference = startTime.getMinute();
        
        startTime = date.withHour((int)hoursDifference).withMinute((int)minutesDifference);
        endTime = startTime.plusHours(1); // Assuming 1-hour slots
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public String toString() {
        return "TimeSlot{" +
               "startTime=" + startTime +
               ", endTime=" + endTime +
               ", status=" + status +
               '}';
    }
}