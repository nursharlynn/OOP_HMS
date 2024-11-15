package Patient;

import java.util.List;

public interface IAppointmentsHandler {

	void scheduleAppointments(Patient patient, int appointmentId);

	void rescheduleAppointments(String hospitalId);

	void cancelAppointment(String hospitalId);

	void viewPastAppointmentOutcomeRecords(String patientId);

	List<String[]> viewAvailableSlots();

	void viewScheduledAppointments(String hospitalId);

}