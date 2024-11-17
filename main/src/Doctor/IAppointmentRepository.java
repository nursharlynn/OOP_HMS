package Doctor;

import Appointment.Appointment;
import java.util.ArrayList;

public interface IAppointmentRepository {

    boolean reviewAppointmentRequests(Doctor doctor);

    void recordAppointmentOutcome(Doctor doctor);

    ArrayList<Appointment> viewUpcomingAppointments(Doctor doctor);

}