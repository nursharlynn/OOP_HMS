package Doctor;

import Appointment.*;
import HMS.DataLoader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DoctorAppointmentManager implements IAppointmentRepository {

	private Scanner scanner;
	private static final String APPOINTMENTS_CSV = "appointments.csv"; // Path to your CSV file

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
					.filter(data -> data.length > 4 &&
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
						appointment[0], 
						appointment[5], 
						appointment[6], 
						appointment[2], 
						appointment[3] 
				);
			}

			// Prompt for appointment selection
			System.out.println("\nOptions:");
			System.out.println("Enter the number of the appointment to accept/decline");
			System.out.println("Enter 0 to cancel action");

			System.out.print("Your choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine(); 

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
			scanner.nextLine(); 

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
									data[5], data[6]));
							System.out.println("Appointment accepted successfully.");
							break;
						case 2: // Decline
							lines.set(i, String.format("%s,%s,%s,%s,Cancelled,%s,%s",
									data[0], data[1], data[2], data[3],
									data[5], data[6]));
							//lines.set(i+1, String.format("%s,%s,%s,%s,Available,,",
									//data[0], data[1], data[2], data[3]));
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
			return false;
		}
	}
	

	
	public void recordAppointmentOutcome(Doctor doctor) {
		try {
			// Read available medicines
			List<String> availableMedicines = DataLoader.readAvailableMedicines();

			// Read appointments file
			Path path = Paths.get("data/Appointments.csv");
			List<String> lines = Files.readAllLines(path);

			// Find confirmed appointments for this specific doctor
			List<String[]> confirmedAppointments = lines.stream()
					.skip(1) 
					.map(line -> line.split(","))
					.filter(data -> data.length > 4 &&
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
						appointment[0],
						appointment[5], 
						appointment[6], 
						appointment[2], 
						appointment[3] 
				);
			}

			// Prompt for appointment selection
			System.out.println("\nEnter the number of the appointment to record outcome");
			System.out.println("Enter 0 to cancel");

			System.out.print("Your choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine(); 

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
					System.out.println((i + 1) + ". " + availableMedicines.get(i));
				}
				System.out.println("0. Finish adding medications");

				System.out.print("Choose a medicine (number): ");
				int medicineChoice = scanner.nextInt();
				scanner.nextLine(); 

				if (medicineChoice == 0)
					break;

				if (medicineChoice < 1 || medicineChoice > availableMedicines.size()) {
					System.out.println("Invalid choice.");
					continue;
				}

				String chosenMedicine = availableMedicines.get(medicineChoice - 1);

				System.out.print("Enter quantity for " + chosenMedicine + ": ");
				int quantity = scanner.nextInt();
				scanner.nextLine(); 

				prescribedMedications.add(chosenMedicine + ":" + quantity);
			}

			// Consultation notes
			System.out.print("Enter consultation notes: ");
			String consultationNotes = scanner.nextLine();

			// Write to AppointmentOutcomes.csv
			writeAppointmentOutcome(
					Integer.parseInt(selectedAppointment[0]), 
					doctor.getName(),
					selectedAppointment[2], 
					servicesProvided,
					prescribedMedications,
					consultationNotes,
					selectedAppointment[5], 
					selectedAppointment[6] 
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
			String patientName) throws IOException {
		Path outcomePath = Paths.get("data/AppointmentOutcomes.csv");

		int recordId = getNextAppointmentId(outcomePath);

		String prescriptionString = prescribedMedications.isEmpty() ? "None" : String.join("|", prescribedMedications);

		try {
			if (!Files.exists(outcomePath)) {
				Files.createDirectories(outcomePath.getParent());
				try (BufferedWriter initialWriter = Files.newBufferedWriter(outcomePath)) {
					initialWriter.write(
							"RecordID,DoctorName,PatientID,PatientName,Date,Service,PrescribedMedications,ConsultationNotes,Status");
					initialWriter.newLine();
				}
			}

			try (BufferedWriter writer = Files.newBufferedWriter(outcomePath, StandardOpenOption.APPEND)) {
				writer.write(String.format("%d,%s,%s,%s,%s,%s,%s,%s,Pending",
						recordId,
						doctorName,
						patientId,
						patientName,
						date,
						servicesProvided,
						prescriptionString,
						consultationNotes.replace(",", ";")));
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("Error writing appointment outcome: " + e.getMessage());
			throw e;
		}
	}

	public ArrayList<Appointment> viewUpcomingAppointments(Doctor doctor) {
		try {
			Path path = Paths.get("data/Appointments.csv");
			List<String> lines = Files.readAllLines(path);

			// Filter appointments for this specific doctor with Confirmed status
			List<String[]> upcomingAppointments = lines.stream()
					.skip(1) 
					.map(line -> line.split(","))
					.filter(data -> data.length > 4 &&
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
						appointment[0], 
						appointment[1], 
						appointment[6], 
						appointment[5], 
						appointment[2], 
						appointment[3]
				);
			}

			return appointmentList;
		} catch (IOException e) {
			System.out.println("Error retrieving upcoming appointments: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	public List<String> viewPersonalSchedule(Doctor doctor) {
	    DateTimeFormatter csvDateFormat1 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	    DateTimeFormatter csvDateFormat2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    DateTimeFormatter csvDateFormat3 = DateTimeFormatter.ofPattern("M/d/yyyy"); // New format to handle single-digit month/day
	    DateTimeFormatter displayDateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy"); // Desired display format

	    try {
	        Path path = Paths.get("data/Appointments.csv");
	        List<String> lines = Files.readAllLines(path);

	        // Filter appointments for this specific doctor
	        List<String[]> doctorAppointments = lines.stream()
	                .skip(1)
	                .map(line -> line.split(","))
	                .filter(data -> data.length > 1 &&
	                        data[1].trim().equalsIgnoreCase(doctor.getName()))
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
	            String formattedDate = appointment[2];
	            try {
	                // Attempt to parse the date with multiple formats
	                LocalDate date;
	                if (appointment[2].contains("/")) {
	                    try {
	                        date = LocalDate.parse(appointment[2], csvDateFormat1);
	                    } catch (DateTimeParseException e) {
	                        date = LocalDate.parse(appointment[2], csvDateFormat3); // Attempt with single-digit format
	                    }
	                } else {
	                    date = LocalDate.parse(appointment[2], csvDateFormat2);
	                }
	                formattedDate = date.format(displayDateFormat); // Format the date for display
	            } catch (DateTimeParseException e) {
	                System.out.println("Error parsing date: " + appointment[2]);
	            }

	            System.out.printf("%-10s %-20s %-15s %-15s %-15s%n",
	                    appointment[0], // Appointment ID
	                    appointment[1], // Doctor Name
	                    formattedDate,  // Formatted Date
	                    appointment[3], // Time
	                    appointment.length > 4 ? appointment[4] : "N/A" // Status
	            );

	            // Add to schedule details list
	            scheduleDetails.add(String.format("Appointment ID: %s, Date: %s, Time: %s, Status: %s",
	                    appointment[0], formattedDate, appointment[3],
	                    appointment.length > 4 ? appointment[4] : "N/A"));
	        }

	        return scheduleDetails;
	    } catch (IOException e) {
	        System.out.println("Error retrieving personal schedule: " + e.getMessage());
	        return new ArrayList<>();
	    }
	}


	public void setAvailability(Doctor doctor, String date, String time) {

	    // Get doctor's name
	    String doctorName = doctor.getName();
	    
	    boolean duplicateFound = !isSlotAvailable(doctorName, date, time);
        if (duplicateFound) {
            System.out.println("Error: An appointment slot already exists for this date and time.");
            return;
        }

	    try {
	        Path path = Paths.get("data/Appointments.csv");

	        // Ensure file exists and has header
	        if (!Files.exists(path) || Files.size(path) == 0) {
	            Files.createDirectories(path.getParent());
	            try (BufferedWriter initialWriter = Files.newBufferedWriter(path, StandardOpenOption.CREATE)) {
	                initialWriter.write("AppointmentID,DoctorName,Date,Time,Status");
	                initialWriter.newLine();
	            }
	        }
	        

	        // Get next appointment ID
	        int appointmentId = getNextAppointmentId(path);

	        // Append new appointment
	        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
	            writer.write(String.format("%d,%s,%s,%s,Available", appointmentId, doctorName, date, time));
	            writer.newLine();
	            
	            
	            System.out.println("Appointment slot created successfully!");
	            System.out.println("Appointment ID: " + appointmentId);
	        }
	    } catch (IOException e) {
	        System.out.println("Error creating appointment: " + e.getMessage());
	    }
	}


	private boolean isSlotAvailable(String doctorName, String date, String time) {
	    Path path = Paths.get("data/Appointments.csv");
	    DateTimeFormatter csvDateFormat1 = DateTimeFormatter.ofPattern("MM/dd/yyyy"); // Format with leading zeros
	    DateTimeFormatter csvDateFormat2 = DateTimeFormatter.ofPattern("M/d/yyyy"); // Format without leading zeros
	    DateTimeFormatter inputDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Input date format
	    
	    try {
	        List<String> lines = Files.readAllLines(path);
	        
	        LocalDate inputDate = LocalDate.parse(date, inputDateFormat); // Parse input date

	        boolean isHeader = true; // Flag to skip the header row
	        for (String line : lines) {
	            if (isHeader) {
	                isHeader = false; // Skip the first row (header)
	                continue;
	            }

	            String[] data = line.split(",");
	            if (data.length > 4) {
	                String existingDoctorName = data[1].trim();
	                String existingDateStr = data[2].trim();
	                String existingTime = data[3].trim();
	                
	                // Parse the date in the CSV format with multiple formats
	                LocalDate existingDate;
	                try {
	                    if (existingDateStr.contains("/")) {
	                        try {
	                            existingDate = LocalDate.parse(existingDateStr, csvDateFormat1);
	                        } catch (DateTimeParseException e) {
	                            existingDate = LocalDate.parse(existingDateStr, csvDateFormat2); // Attempt with single-digit format
	                        }
	                    } else {
	                        existingDate = LocalDate.parse(existingDateStr, inputDateFormat);
	                    }
	                } catch (DateTimeParseException e) {
	                    System.out.println("Error parsing date in CSV: " + existingDateStr);
	                    continue; // Skip lines with invalid date format
	                }

	                // Check for conflicting slot based only on doctor name, date, and time
	                if (existingDoctorName.equalsIgnoreCase(doctorName) && 
	                    existingDate.equals(inputDate) && 
	                    existingTime.equals(time)) {
	                    System.out.println("Duplicate found for Doctor: " + doctorName + ", Date: " + date + ", Time: " + time);
	                    return false; // Duplicate found
	                }
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Error reading appointments file: " + e.getMessage());
	    }
	    return true; // No duplicate found
	}



	

	public static int getNextAppointmentId(Path path) {
		try {
			if (!Files.exists(path) || Files.size(path) == 0) {
				return 1;
			}

			return (int) Files.lines(path)
					.skip(1) 
					.map(line -> {
						try {
							String[] parts = line.split("[,\t]");
							return Integer.parseInt(parts[0].trim());
						} catch (Exception e) {
							return 0;
						}
					})
					.max(Integer::compare)
					.orElse(0) + 1; 
		} catch (IOException e) {
			System.out.println("Error getting next appointment ID: " + e.getMessage());
			return 1;
		}
	}

}