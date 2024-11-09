package Doctor;

import java.util.ArrayList;
import Appointment.CompletedAppointment;
import Appointment.Appointment;

public interface IAppointment {

    boolean reviewAppointmentRequests(Doctor doctor);

    void recordAppointmentOutcome(Doctor doctor, int appointmentId);

    ArrayList<CompletedAppointment> viewPastAppointmentOutcomeRecord(String patientId);

    ArrayList<Appointment> viewUpcomingAppointments(Doctor doctor);
}
