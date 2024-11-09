package Doctor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import Appointment.Appointment;
import Appointment.CompletedAppointment;
import Appointment.Schedule;
import Appointment.ViewScheduledAppointments;
import Appointment.PastAppointmentOutcomeRecord;

public class DoctorAppointmentManager implements IAppointment {

    private ArrayList<Appointment> appointments;
    private ArrayList<ViewScheduledAppointments> scheduledAppointments;
    private PastAppointmentOutcomeRecord pastOutcomes;

    public DoctorAppointmentManager() {
        this.appointments = new ArrayList<>();
        this.scheduledAppointments = new ArrayList<>();
        this.pastOutcomes = new PastAppointmentOutcomeRecord();
    }

    public boolean reviewAppointmentRequests(Doctor doctor) {
        System.out.println("Reviewing appointment requests for Dr. " + doctor.getName());
        for (Appointment appt : appointments) {
            if (appt.getDoctor().equals(doctor.gethospitalId()) && appt.isPending()) {
                System.out.println("Pending appointment request: " + appt);
            }
        }
        return true;
    }

    public void recordAppointmentOutcome(Doctor doctor, int appointmentId) {
        for (Appointment appt : appointments) {
            if (appt.generateAppointmentId() == appointmentId) {
                CompletedAppointment completedAppt = new CompletedAppointment(
                    appt.getPatient(), appt.getDoctor(), LocalDateTime.now(), 
                    "Consultation", "Paracetamol", "pending", "General checkup completed."
                );
                pastOutcomes.addCompletedAppointment(completedAppt);
                System.out.println("Recorded outcome for appointment ID: " + appointmentId);
                return;
            }
        }
        System.out.println("Appointment ID not found.");
    }

    public ArrayList<CompletedAppointment> viewPastAppointmentOutcomeRecord(String patientId) {
        ArrayList<CompletedAppointment> records = pastOutcomes.getRecordsByPatient(patientId);
        if (records.isEmpty()) {
            System.out.println("No past appointment records found for patient ID: " + patientId);
        }
        return records;
    }

    public ArrayList<Appointment> viewUpcomingAppointments(Doctor doctor) {
        ArrayList<Appointment> upcomingAppointments = new ArrayList<>();
        for (ViewScheduledAppointments scheduled : scheduledAppointments) {
            if (scheduled.getDoctorId().equals(doctor.gethospitalId())) {
                upcomingAppointments.addAll(scheduled.getAppointments());
            }
        }
        return upcomingAppointments;
    }
}
