package Appointment;

import java.time.LocalDateTime;

public class Appointment {
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private LocalDateTime dateTime;
    private String status; // "scheduled", "confirmed", "canceled"

    public Appointment(String appointmentId, String patientId, String doctorId, LocalDateTime dateTime) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
        this.status = "scheduled"; // default status
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Appointment ID: " + appointmentId + ", Doctor ID: " + doctorId +
               ", Patient ID: " + patientId + ", Date/Time: " + dateTime +
               ", Status: " + status;
    }
}
