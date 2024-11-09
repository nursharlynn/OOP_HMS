package Patient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import Appointment.*;
import Doctor.Doctor;

public interface IAppointmentsHandler {

    void scheduleAppointments(Patient patient, Doctor doctor, LocalDateTime date, LocalDateTime time);

    void rescheduleAppointments(int AppointmentsID, ArrayList<ViewScheduledAppointments> scheduledAppointments);

    boolean cancelAppointments(int AppointmentsID);

    void getPastOutcomes(String patientId);
}
