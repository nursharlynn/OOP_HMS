package Doctor;

import Appointment.*;
import HMS.DataLoader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
        	System.out.printf("%-5s %-15s %-15s %-20s %-15s %-15s%n", 
            "No.", "Appointment ID", "Patient ID", "Patient Name", "Date", "Time");
        	System.out.println("-".repeat(100));
            
			for (int i = 0; i < doctorAppointments.size(); i++) {
				String[] appointment = doctorAppointments.get(i);
				System.out.printf("%-5d %-15s %-15s %-20s %-15s %-15s%n", 
					i + 1, 
					appointment[0],  // Appointment ID
					appointment[5],  // Patient ID
					appointment[6], // Patient Name
					appointment[2],  // Date
					appointment[3]   // Time
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
        	System.out.printf("Patient ID: %s%n", selectedAppointment.length > 5 ? selectedAppointment[5] : "N/A");
        	System.out.printf("Patient Name: %s%n", selectedAppointment.length > 6 ? selectedAppointment[6] : "N/A");
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
							lines.set(i, String.format("%s,%s,%s,%s,Confirmed,%s,%s", 
								data[0], data[1], data[2], data[3], 
								data[5], data[6]
							));
                            System.out.println("Appointment accepted successfully.");
                            break;
                        case 2: // Decline
								lines.set(i, String.format("%s,%s,%s,%s,Available,,", 
									data[0], data[1], data[2], data[3]));
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
	public void recordAppointmentOutcome(Doctor doctor) {
		try {
			// Read available medicines
			List<String> availableMedicines = DataLoader.readAvailableMedicines();
	
			// Read appointments file
			Path path = Paths.get("data/Appointments.csv");
			List<String> lines = Files.readAllLines(path);
			
			// Find confirmed appointments for this specific doctor
			List<String[]> confirmedAppointments = lines.stream()
				.skip(1) // Skip header
				.map(line -> line.split(","))
				.filter(data -> 
					data.length > 4 && 
					data[1].trim().equalsIgnoreCase(doctor.getName()) && // Match doctor's name
					data[4].trim().equalsIgnoreCase("Confirmed") // Only confirmed appointments
				)
				.collect(Collectors.toList());
			
			// Display confirmed appointments
			System.out.println("\n=== Your Confirmed Appointments ===");
			System.out.printf("%-5s %-15s %-15s %-20s %-15s %-15s%n", 
				"No.", "Appointment ID", "Patient ID", "Patient Name", "Date", "Time");
			System.out.println("-".repeat(100));
			
			for (int i = 0; i < confirmedAppointments.size(); i++) {
				String[] appointment = confirmedAppointments.get(i);
				System.out.printf("%-5d %-15s %-15s %-20s %-15s %-15s%n", 
					i + 1, 
					appointment[0],  // Appointment ID
					appointment[5],  // Patient ID
					appointment[6],  // Patient Name
					appointment[2],  // Date
					appointment[3]   // Time
				);
			}
			
			// Prompt for appointment selection
			System.out.println("\nEnter the number of the appointment to record outcome");
			System.out.println("Enter 0 to cancel");
			
			System.out.print("Your choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline
			
			// Handle user choice
			if (choice == 0) {
				System.out.println("Action cancelled.");
				return;
			}
			
			if (choice < 1 || choice > confirmedAppointments.size()) {
				System.out.println("Invalid selection.");
				return;
			}
			
			// Get the selected appointment details
			String[] selectedAppointment = confirmedAppointments.get(choice - 1);
			
			// Prepare outcome details
			System.out.print("Enter services provided (e.g., consultation, X-ray, blood test): ");
			String servicesProvided = scanner.nextLine();
			
			// Prescribed Medications
			List<String> prescribedMedications = new ArrayList<>();
			while (true) {
				System.out.println("\nAvailable Medicines:");
				for (int i = 0; i < availableMedicines.size(); i++) {
					System.out.println((i+1) + ". " + availableMedicines.get(i));
				}
				System.out.println("0. Finish adding medications");
				
				System.out.print("Choose a medicine (number): ");
				int medicineChoice = scanner.nextInt();
				scanner.nextLine(); // Consume newline
				
				if (medicineChoice == 0) break;
				
				if (medicineChoice < 1 || medicineChoice > availableMedicines.size()) {
					System.out.println("Invalid choice.");
					continue;
				}
				
				String chosenMedicine = availableMedicines.get(medicineChoice - 1);
				
				System.out.print("Enter quantity for " + chosenMedicine + ": ");
				int quantity = scanner.nextInt();
				scanner.nextLine(); // Consume newline
				
				prescribedMedications.add(chosenMedicine + ":" + quantity);
			}
			
			// Consultation notes
			System.out.print("Enter consultation notes: ");
			String consultationNotes = scanner.nextLine();
			
			// Write to AppointmentOutcomes.csv
			writeAppointmentOutcome(
				Integer.parseInt(selectedAppointment[0]), // Appointment ID
				doctor.getName(), 
				selectedAppointment[2], // Date
				servicesProvided, 
				prescribedMedications, 
				consultationNotes,
				selectedAppointment[5], // Patient ID
				selectedAppointment[6]  // Patient Name
			);
			
			System.out.println("Appointment outcome recorded successfully!");
			
		} catch (IOException e) {
			System.out.println("Error recording appointment outcome: " + e.getMessage());
			e.printStackTrace();
		}
	}


	private void writeAppointmentOutcome(
    int appointmentId, 
    String doctorName, 
    String date, 
    String servicesProvided, 
    List<String> prescribedMedications, 
    String consultationNotes,
    String patientId,
    String patientName
) throws IOException {
    Path outcomePath = Paths.get("data/AppointmentOutcomes.csv");
    
    // Get next appointment ID
    int recordId = Doctor.getNextAppointmentId(outcomePath);

    
    // Prepare the outcome line
    String prescriptionString = prescribedMedications.isEmpty() ? 
        "None" : String.join("|", prescribedMedications);
    
    try {
        // Ensure file exists and has header
        if (!Files.exists(outcomePath)) {
            Files.createDirectories(outcomePath.getParent());
            try (BufferedWriter initialWriter = Files.newBufferedWriter(outcomePath)) {
                initialWriter.write("RecordID,DoctorName,PatientID,PatientName,Date,Service,PrescribedMedications,ConsultationNotes,Status");
                initialWriter.newLine();
            }
        }
        
        // Append new outcome
        try (BufferedWriter writer = Files.newBufferedWriter(outcomePath, StandardOpenOption.APPEND)) {
            writer.write(String.format("%d,%s,%s,%s,%s,%s,%s,%s,Pending", 
                recordId, 
                doctorName, 
                patientId,
                patientName,
                date, 
                servicesProvided, 
                prescriptionString, 
                consultationNotes.replace(",", ";")
            ));
            writer.newLine();
        }
    } catch (IOException e) {
        System.out.println("Error writing appointment outcome: " + e.getMessage());
        throw e;
    }
}
	


	/**
	 * 
	 * @param patientId
	 */
	public ArrayList<CompletedAppointment> viewPastAppointmentOutcomeRecord(String patientId) {
		// TODO - implement DoctorAppointmentManager.viewPastAppointmentOutcomeRecord
		throw new UnsupportedOperationException();
	}

	public ArrayList<Appointment> viewUpcomingAppointments(Doctor doctor) {
		try {
			Path path = Paths.get("data/Appointments.csv");
			List<String> lines = Files.readAllLines(path);
			
			// Filter appointments for this specific doctor with Confirmed status
			List<String[]> upcomingAppointments = lines.stream()
				.skip(1) // Skip header
				.map(line -> line.split(","))
				.filter(data -> 
					data.length > 4 && 
					data[1].trim().equalsIgnoreCase(doctor.getName()) && // Match doctor's name
					data[4].trim().equalsIgnoreCase("Confirmed") // Only confirmed appointments
				)
				.collect(Collectors.toList());
			
			// Check if there are any upcoming appointments
			if (upcomingAppointments.isEmpty()) {
				System.out.println("No upcoming appointments found.");
				return new ArrayList<>();
			}
			
			// Display upcoming appointments
			System.out.println("\n=== Your Upcoming Appointments ===");
			System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s%n", 
				"Appt ID", "Doctor Name", "Patient Name", "Patient ID", "Date", "Time");
			System.out.println("-".repeat(100));
			
			ArrayList<Appointment> appointmentList = new ArrayList<>();
			
			for (String[] appointment : upcomingAppointments) {
				System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s%n", 
					appointment[0],  // Appointment ID
					appointment[1],  // Doctor Name
					appointment[6],  // Patient Name
					appointment[5],  // Patient ID
					appointment[2],  // Date
					appointment[3]   // Time
				);
			}
			
			return appointmentList;
		} catch (IOException e) {
			System.out.println("Error retrieving upcoming appointments: " + e.getMessage());
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public List<String> viewPersonalSchedule(Doctor doctor) {
		try {
			Path path = Paths.get("data/Appointments.csv");
			List<String> lines = Files.readAllLines(path);
			
			// Filter appointments for this specific doctor
			List<String[]> doctorAppointments = lines.stream()
				.skip(1) // Skip header
				.map(line -> line.split(","))
				.filter(data -> 
					data.length > 1 && 
					data[1].trim().equalsIgnoreCase(doctor.getName())
				)
				.collect(Collectors.toList());
			
			// Check if there are any appointments
			if (doctorAppointments.isEmpty()) {
				System.out.println("No appointments found in your schedule.");
				return new ArrayList<>();
			}
			
			// Display doctor's schedule
			System.out.println("\n=== Your Personal Schedule ===");
			System.out.printf("%-10s %-20s %-15s %-15s %-15s%n", 
				"Appt ID", "Doctor Name", "Date", "Time", "Status");
			System.out.println("-".repeat(75));
			
			List<String> scheduleDetails = new ArrayList<>();
			
			for (String[] appointment : doctorAppointments) {
				// Format: AppointmentID, DoctorName, Date, Time, Status, PatientID, PatientName
				System.out.printf("%-10s %-20s %-15s %-15s %-15s%n", 
					appointment[0],      // Appointment ID
					appointment[1],      // Doctor Name
					appointment[2],      // Date
					appointment[3],      // Time
					appointment.length > 4 ? appointment[4] : "N/A"  // Status
				);
				
				// Add to schedule details list
				scheduleDetails.add(String.format("Appointment ID: %s, Date: %s, Time: %s, Status: %s", 
					appointment[0], appointment[2], appointment[3], 
					appointment.length > 4 ? appointment[4] : "N/A"));
			}
			
			return scheduleDetails;
		} catch (IOException e) {
			System.out.println("Error retrieving personal schedule: " + e.getMessage());
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	

}