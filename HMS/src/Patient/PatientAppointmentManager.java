package Patient;

import Appointment.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PatientAppointmentManager implements IAppointmentsHandler {

	private PastAppointmentOutcomeRecord pastOutcomes;
	private ArrayList<Appointment> Appointments;

	/**
	 * 
	 * @param patient
	 * @param doctorName
	 * @param date
	 * @param time
	 */
	public void scheduleAppointments(Patient patient, String doctorName, LocalDateTime date, LocalDateTime time) {
		// TODO - implement PatientAppointmentManager.scheduleAppointments
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param AppointmentsID
	 * @param scheduledAppointments
	 */
	public void rescheduleAppointments(int AppointmentsID, ArrayList<ViewScheduledAppointments> scheduledAppointments) {
		// TODO - implement PatientAppointmentManager.rescheduleAppointments
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param AppointmentsID
	 */
	public boolean cancelAppointments(int AppointmentsID) {
		// TODO - implement PatientAppointmentManager.cancelAppointments
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param patientId
	 */
	public void getPastOutcomes(String patientId) {
		// TODO - implement PatientAppointmentManager.getPastOutcomes
		throw new UnsupportedOperationException();
	}

	

	@Override
    public void viewAvailableSlots() {
        System.out.println("--- Appointment Slots ---");
        ArrayList<String> appointmentSlots = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/Appointments.csv"))) {
            String line;
            // Print header
            System.out.printf("%-10s %-20s %-15s %-15s %-15s%n", 
                "Appt ID", "Doctor Name", "Date", "Time", "Status");
            System.out.println("-".repeat(75));

            // Skip CSV header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                
                // Ensure we have enough data
                if (data.length >= 5) {
                    String appointmentId = data[0];
                    String doctorName = data[1];
                    String date = data[2];
                    String time = data[3];
                    String status = data[4];

                    // Print formatted appointment information
                    System.out.printf("%-10s %-20s %-15s %-15s %-15s%n", 
                        appointmentId, doctorName, date, time, status);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading appointments: " + e.getMessage());
        }
    }
}
