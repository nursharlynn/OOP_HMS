package project;

import java.util.Scanner;
import java.util.List;

import project.Doctor;
import project.Appointment;
import project.HMS;

public class DoctorInterface {

    public static void handler(HMS hms, Doctor doctor, Scanner sc) {
        boolean exit = false;
        System.out.println("\nWelcome, Dr. " + doctor.getName() + "!\n");

        while (!exit) {
            System.out.println("1. View Patient Medical Records\n" +
                               "2. Update Patient Medical Records\n" +
                               "3. View Personal Schedule\n" +
                               "4. Set Availability for Appointments\n" +
                               "5. Accept or Decline Appointment Requests\n" +
                               "6. View Upcoming Appointments\n" +
                               "7. Record Appointment Outcome\n" +
                               "0. Logout\n" +
                               "Enter your choice: ");
            
            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    viewPatientMedicalRecords(hms, doctor, sc);
                    break;
                case 2:
                    updatePatientMedicalRecords(hms, doctor, sc);
                    break;
                case 3:
                    doctor.viewSchedule();
                    break;
                case 4:
                    setAvailability(doctor, sc);
                    break;
                case 5:
                    respondToAppointmentRequest(hms, doctor, sc);
                    break;
                case 6:
                    doctor.viewUpcomingAppointments();
                    break;
                case 7:
                    recordAppointmentOutcome(hms, doctor, sc);
                    break;
                case 0:
                    System.out.println("Logging out. Goodbye, Dr. " + doctor.getName() + "!\n");
                    exit = true;
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 7.\n");
            }
        }
    }

    private static void viewPatientMedicalRecords(HMS hms, Doctor doctor, Scanner sc) {
        System.out.println("Enter Patient ID: ");
        String patientId = sc.nextLine();
        Patient patient = hms.getPatientById(patientId);
        
        if (patient != null) {
            doctor.viewMedicalRecord(patient);
        } else {
            System.out.println("Patient ID " + patientId + " not found.");
        }
    }

    private static void updatePatientMedicalRecords(HMS hms, Doctor doctor, Scanner sc) {
        System.out.println("Enter Patient ID to update medical records: ");
        String patientId = sc.nextLine();
        Patient patient = hms.getPatientById(patientId);
        
        if (patient != null) {
            doctor.updateMedicalRecord(patient, sc);
        } else {
            System.out.println("Patient ID " + patientId + " not found.");
        }
    }

    private static void setAvailability(Doctor doctor, Scanner sc) {
        System.out.println("Enter availability details (e.g., date and time): ");
        String availability = sc.nextLine();
        doctor.setAvailability(availability);
        System.out.println("Availability set.");
    }

    private static void respondToAppointmentRequest(HMS hms, Doctor doctor, Scanner sc) {
        System.out.println("Enter Appointment ID to respond: ");
        String appointmentId = sc.next();
        sc.nextLine();
        Appointment appointment = hms.getAppointmentById(appointmentId);
        
        if (appointment != null && doctor.canHandleAppointment(appointment)) {
            System.out.println("Enter 'accept' to confirm or 'decline' to cancel: ");
            String decision = sc.nextLine();
            doctor.respondToAppointmentRequest(appointment, decision);
            System.out.println("Appointment " + (decision.equalsIgnoreCase("accept") ? "accepted" : "declined") + ".");
        } else {
            System.out.println("Appointment ID " + appointmentId + " not found or not relevant to you.");
        }
    }

    private static void recordAppointmentOutcome(HMS hms, Doctor doctor, Scanner sc) {
        System.out.print("Enter Appointment ID to record outcome: ");
        String appointmentId = sc.next();
        sc.nextLine(); 

        Appointment appointment = hms.getAppointmentById(appointmentId);

        if (appointment != null && appointment.getDoctorId().equals(doctor.getId())) {
            System.out.print("Enter type of service provided: ");
            String serviceType = sc.nextLine();
            System.out.print("Enter prescribed medications: ");
            String medications = sc.nextLine();
            System.out.print("Enter consultation notes: ");
            String notes = sc.nextLine();

            appointment.setServiceType(serviceType);
            appointment.setPrescribedMedications(medications);
            appointment.setConsultationNotes(notes);
            appointment.setStatus("completed");

            System.out.println("Appointment outcome recorded successfully.");
        } else {
            System.out.println("Appointment not found or you are not authorized to modify this appointment.");
        }
    }
}
