package Appointment;

import java.time.LocalDateTime;

public class Appointment extends TimeSlot {
    private int appointmentId;
    private String doctorName;
    private String patientName;
    private LocalDateTime dateTime;
    private String patientId;
    private Status status;

    public Appointment(int appointmentId, String doctorName, String patientName, String patientId,
            LocalDateTime dateTime, Status status) {
        this.appointmentId = appointmentId;
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.patientId = patientId;
        this.dateTime = dateTime;
        this.status = status;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void reschedule(LocalDateTime newDateTime) {
        this.dateTime = newDateTime;
        this.status = Status.Confirmed;
    }

    public void cancel() {
        this.status = Status.Canceled;
    }

    public String getPatientId() {
        return patientId;
    }

}