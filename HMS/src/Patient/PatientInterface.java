package project;

import java.util.Map;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.util.List;
import java.time.LocalDateTime;

public class PatientInterface {
    private static Map<String, PatientRecord> patientRecordMap; 

    public static void handler(Patient patient, Scanner sc) {
        patientRecordMap = HMS.getPatientRecordMap(); // assuming HMS class manages records
        boolean exit = false;
        System.out.println("Welcome, " + patient.getName());
        String patientId = patient.getPatientID();

        while (!exit) {
            System.out.println("\n1. View Medical Record\n" +
                               "2. Update Personal Information\n" +
                               "3. View Available Appointment Slots\n" +
                               "4. Schedule Appointment\n" +
                               "5. Reschedule Appointment\n" +
                               "6. Cancel Appointment\n" +
                               "7. View Scheduled Appointments\n" +
                               "8. View Past Appointment Outcome Records\n" +
                               "0. Logout\n" +
                               "Enter your choice: ");
            int choice = sc.nextInt();
            System.out.println();
            switch (choice) {
                case 1:
                    viewMedicalRecord(patientId);
                    break;
                case 2:
                    updatePersonalInformation(patient, sc);
                    break;
                case 3:
                    viewAvailableAppointments();
                    break;
                case 4:
                    scheduleAppointment(patientId, sc);
                    break;
                case 5:
                    rescheduleAppointment(patientId, sc);
                    break;
                case 6:
                    cancelAppointment(patientId, sc);
                    break;
                case 7:
                    viewScheduledAppointments(patientId);
                    break;
                case 8:
                    viewPastAppointmentRecords(patientId);
                    break;
                case 0:
                    System.out.println("Thank you. Logging out...\n");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid number.\n");
            }
        }
    }

    private static void viewMedicalRecord(String patientId) {
        PatientRecord record = patientRecordMap.get(patientId);
        if (record != null) {
            System.out.println("Medical Record for Patient ID: " + patientId);
            System.out.println(record); // assuming toString method formats medical record details
        } else {
            System.out.println("Record not found.");
        }
    }

    private static void updatePersonalInformation(Patient patient, Scanner sc) {
        System.out.println("Updating Personal Information...");
        System.out.print("Enter new contact number: ");
        String newContact = sc.next();
        System.out.print("Enter new email address: ");
        String newEmail = sc.next();
        patient.setContactInformation(newContact, newEmail);
        System.out.println("Information updated successfully.");
    }

    private static void viewAvailableAppointments() {
        System.out.println("Available appointment slots:");

        // Retrieve all available slots from HMS
        Map<String, List<String>> availableSlots = HMS.getAllAvailableSlots();

        if (availableSlots.isEmpty()) {
            System.out.println("No available appointment slots at the moment.");
            return;
        }

        for (Map.Entry<String, List<String>> entry : availableSlots.entrySet()) {
            String doctorId = entry.getKey();
            List<String> slots = entry.getValue();

            System.out.println("Doctor ID: " + doctorId);
            if (slots.isEmpty()) {
                System.out.println("  No available slots.");
            } else {
                for (String slot : slots) {
                    System.out.println("  Available slot: " + slot);
                }
            }
        }
    }

    private static void scheduleAppointment(String patientId, Scanner sc) {
        System.out.print("Enter Doctor ID: ");
        String doctorId = sc.next();

        // Input and parse date with error handling
        LocalDateTime dateTime = null;
        while (dateTime == null) {
            System.out.print("Enter appointment date and time (YYYY-MM-DDTHH:MM): ");
            String dateInput = sc.next();
            try {
                dateTime = LocalDateTime.parse(dateInput);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter date as YYYY-MM-DDTHH:MM.");
            }
        }

        Appointment appointment = HMS.scheduleAppointment(patientId, doctorId, dateTime);
        System.out.println("Appointment scheduled with ID: " + appointment.getAppointmentId());
    }

    private static void rescheduleAppointment(String patientId, Scanner sc) {
        System.out.print("Enter Appointment ID to reschedule: ");
        String appointmentId = sc.next();

        System.out.print("Enter appointment date and time (YYYY-MM-DDTHH:MM): ");
        String dateInput = sc.next();
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateInput);
            Appointment appointment = HMS.scheduleAppointment(patientId, doctorId, dateTime.toString()); // Use the formatted string here
            System.out.println("Appointment scheduled with ID: " + appointment.getAppointmentId());
        } catch (Exception e) {
            System.out.println("Invalid date format. Please enter date as YYYY-MM-DDTHH:MM.");
        }

        if (HMS.rescheduleAppointment(appointmentId, newDateTime)) {
            System.out.println("Appointment rescheduled.");
        } else {
            System.out.println("Unable to reschedule appointment. Please check the appointment ID or status.");
        }
    }

    private static void cancelAppointment(String patientId, Scanner sc) {
        System.out.print("Enter Appointment ID to cancel: ");
        String appointmentId = sc.next();
        if (HMS.cancelAppointment(appointmentId)) {
            System.out.println("Appointment canceled.");
        } else {
            System.out.println("Unable to cancel appointment. Please check the appointment ID or status.");
        }
    }

    private static void viewScheduledAppointments(String patientId) {
        List<Appointment> futureAppointments = HMS.getFutureAppointments(patientId);
        if (futureAppointments.isEmpty()) {
            System.out.println("No upcoming appointments.");
        } else {
            for (Appointment appt : futureAppointments) {
                System.out.println(appt);
            }
        }
    }

    private static void viewPastAppointmentRecords(String patientId) {
        List<Appointment> pastAppointments = HMS.getPastAppointments(patientId);
        if (pastAppointments.isEmpty()) {
            System.out.println("No past appointments.");
        } else {
            for (Appointment appt : pastAppointments) {
                System.out.println(appt);
            }
        }
    }
}
