package Patient;

import Appointment.*;
import Records.*;
import User.*;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;

public class Patient extends User {

    private LocalDateTime dateofBirth;
    private String gender;
    private String contact;
    private String emailAddress;
    private String bloodType;
    private IAppointmentsHandler apptHandler;
    private ArrayList<ViewScheduledAppointments> scheduledAppointments;
    private MedicalRecord medicalRecord;
    private Schedule schedule;

    public Patient(String hospitalId, String password, LocalDateTime dateOfBirth, String gender, String emailAddress, String bloodType) {
        super(hospitalId, password, bloodType, gender, calculateAge(dateOfBirth));
        this.emailAddress = emailAddress;
        this.bloodType = bloodType;
        this.dateofBirth = dateOfBirth;
        this.gender = gender;
        this.contact = emailAddress;
    }

    @Override
    public void displayMenu() {
        System.out.println("\n--- Patient Menu ---");
        System.out.println("1. View Medical Record");
        System.out.println("2. Update Personal Information");
        System.out.println("3. View Available Appointment Slots");
        System.out.println("4. Schedule an Appointment");
        System.out.println("5. Reschedule an Appointment");
        System.out.println("6. Cancel an Appointment");
        System.out.println("7. View Scheduled Appointments");
        System.out.println("8. View Past Appointment Outcome Records");
        System.out.println("9. Logout");
    }

    @Override
	public void handleMenuChoice(int choice) {
    switch (choice) {
        case 1:
            System.out.println("View Medical Record - Not implemented yet");
            break;
        case 2:
            System.out.println("Update Personal Information - Not implemented yet");
            break;
        case 3:
            System.out.println("View Available Appointment Slots - Not implemented yet");
            break;
        case 4:
            System.out.println("Schedule an Appointment - Not implemented yet");
            break;
        case 5:
            System.out.println("Reschedule an Appointment - Not implemented yet");
            break;
        case 6:
            System.out.println("Cancel an Appointment - Not implemented yet");
            break;
        case 7:
            System.out.println("View Scheduled Appointments - Not implemented yet");
            break;
        case 8:
            System.out.println("View Past Appointment Outcome Records - Not implemented yet");
            break;
        case 9:
            System.out.println("Logging out...");
            return; // This will exit the menu loop
        default:
            System.out.println("Invalid choice. Please try again.");
    }
}

    // Existing getters and setters with minimal implementation
    public String getName() {
        return "Patient Name"; // Placeholder
    }

    public LocalDateTime getDateofBirth() {
        return this.dateofBirth;
    }

    public String getGender() {
        return this.gender;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPatientID() {
        return getHospitalId(); // Using hospitalId as patientID
    }

    public String getBloodType() {
        return this.bloodType;
    }

    // Helper method to calculate age from dateOfBirth
    private static int calculateAge(LocalDateTime dateOfBirth) {
        return Period.between(dateOfBirth.toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();
    }

    // Placeholder methods for other functionalities
    public void viewAvailableSlots() {
        System.out.println("View Available Slots - Not implemented");
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setApptHandler(IAppointmentsHandler apptHandler) {
        this.apptHandler = apptHandler;
    }
}