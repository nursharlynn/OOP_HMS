package Doctor;

import Appointment.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DoctorAppointmentManager implements IAppointment {

	private ArrayList<Appointment> appointments;
	private ArrayList<ViewScheduledAppointments> scheduledAppointments;
	private PastAppointmentOutcomeRecord pastOutcomes;
	private Scanner scanner;

	public DoctorAppointmentManager() {
        this.scanner = new Scanner(System.in);
    }

	@Override
    public boolean reviewAppointmentRequests(Doctor doctor) {
        try {
            // Read appointments file
            Path path = Paths.get("data/Appointments.csv");
            List<String> lines = Files.readAllLines(path);
            
            // Find appointments for this specific doctor
            List<String[]> doctorAppointments = lines.stream()
                .skip(1) // Skip header
                .map(line -> line.split(","))
                .filter(data -> 
                    data.length > 4 && 
                    data[1].trim().equalsIgnoreCase(doctor.getName()) && // Match doctor's name
                    data[4].trim().equalsIgnoreCase("Booked") // Only booked appointments
                )
                .collect(Collectors.toList());
            
            // Check if there are any booked appointments
            if (doctorAppointments.isEmpty()) {
                System.out.println("No booked appointments found.");
                return false;
            }
            
            // Display booked appointments
            System.out.println("\n=== Your Booked Appointments ===");
            System.out.printf("%-5s %-15s %-15s %-15s %-15s %-15s%n", 
                "No.", "Appointment ID", "Patient ID", "Date", "Time", "Status");
            System.out.println("-".repeat(85));
            
            for (int i = 0; i < doctorAppointments.size(); i++) {
                String[] appointment = doctorAppointments.get(i);
                System.out.printf("%-5d %-15s %-15s %-15s %-15s %-15s%n", 
                    i + 1, 
                    appointment[0],  // Appointment ID
                    appointment[5],  // Patient ID
                    appointment[2],  // Date
                    appointment[3],  // Time
                    appointment[4]   // Status
                );
            }
            
            // Prompt for appointment selection
            System.out.println("\nOptions:");
            System.out.println("Enter the number of the appointment to accept/decline");
            System.out.println("Enter 0 to cancel action");
            
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            // Handle user choice
            if (choice == 0) {
                System.out.println("Action cancelled.");
                return false;
            }
            
            if (choice < 1 || choice > doctorAppointments.size()) {
                System.out.println("Invalid selection.");
                return false;
            }
            
            // Selected appointment
            String[] selectedAppointment = doctorAppointments.get(choice - 1);
            
            // Confirm action
            System.out.println("\nSelected Appointment Details:");
            System.out.printf("Appointment ID: %s%n", selectedAppointment[0]);
            System.out.printf("Patient ID: %s%n", selectedAppointment[5]);
            System.out.printf("Date: %s%n", selectedAppointment[2]);
            System.out.printf("Time: %s%n", selectedAppointment[3]);
            
            System.out.println("\nChoose an action:");
            System.out.println("1. Accept Appointment");
            System.out.println("2. Decline Appointment");
            System.out.println("0. Cancel Action");
            
            System.out.print("Your choice: ");
            int action = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            // Process the action
            return processAppointmentAction(selectedAppointment[0], action);
            
        } catch (IOException e) {
            System.out.println("Error processing appointments: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

	private boolean processAppointmentAction(String appointmentId, int action) {
        try {
            Path path = Paths.get("data/Appointments.csv");
            List<String> lines = Files.readAllLines(path);
            
            for (int i = 1; i < lines.size(); i++) {
                String[] data = lines.get(i).split(",");
                
                if (data[0].equals(appointmentId)) {
                    switch (action) {
                        case 1: // Accept
                            lines.set(i, String.format("%s,%s,%s,%s,Confirmed,%s", 
                                data[0], data[1], data[2], data[3], data[5]));
                            System.out.println("Appointment accepted successfully.");
                            break;
                        case 2: // Decline
                            lines.set(i, String.format("%s,%s,%s,%s,Available,%s", 
                                data[0], data[1], data[2], data[3], ""));
                            System.out.println("Appointment declined successfully. Slot is now available.");
                            break;
                        case 0: // Cancel
                            System.out.println("Action cancelled.");
                            return false;
                        default:
                            System.out.println("Invalid action.");
                            return false;
                    }
                    
                    // Write changes back to file
                    Files.write(path, lines);
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            System.out.println("Error processing appointment action: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


	/**
	 * 
	 * @param doctor
	 * @param appointmentId
	 */
	public void recordAppointmentOutcome(Doctor doctor, int appointmentId) {
		// TODO - implement DoctorAppointmentManager.recordAppointmentOutcome
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param patientId
	 */
	public ArrayList<CompletedAppointment> viewPastAppointmentOutcomeRecord(String patientId) {
		// TODO - implement DoctorAppointmentManager.viewPastAppointmentOutcomeRecord
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param doctor
	 */
	public ArrayList<Appointment> viewUpcomingAppointments(Doctor doctor) {
		// TODO - implement DoctorAppointmentManager.viewUpcomingAppointments
		throw new UnsupportedOperationException();
	}

}