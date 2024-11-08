package Patient;

import Appointment.*;
import Records.*;
import User.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Patient extends User {

    private String dateofBirth;
    private String gender;
    private String contact;
    private String bloodType;
    private IAppointmentsHandler apptHandler;
    private ArrayList<ViewScheduledAppointments> scheduledAppointments;
    private MedicalRecord medicalRecord;
    private Schedule schedule;
    private Scanner scanner;
    private String name;
    

    public Patient(String hospitalId, String password, String name, String dateOfBirth, String gender, String bloodType, String contact) {
        super(hospitalId, password, name, gender);
        this.name = name;
        this.dateofBirth = dateOfBirth;
        this.gender = gender;
        this.contact = contact;
        this.bloodType = bloodType;
        this.medicalRecord = new MedicalRecord();
        this.scanner = new Scanner(System.in); 
        this.apptHandler = new PatientAppointmentManager();
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
            viewMedicalRecord();
            break;
        case 2:
            updatePersonalInformation();
            break;
        case 3:
            apptHandler.viewAvailableSlots(); // Call the method to view available slots
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
        return this.name;
    }

    public String getDateofBirth() {
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

    public String getPatientID() {
        return getHospitalId(); 
    }

    public String getBloodType() {
        return this.bloodType;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setApptHandler(IAppointmentsHandler apptHandler) {
        this.apptHandler = apptHandler;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    // Placeholder methods for other functionalities
    public void viewAvailableSlots() {
        System.out.println("View Available Slots - Not implemented");
    }

    private void viewMedicalRecord() {
        System.out.println("\n=== View Medical Record ===");
        if (medicalRecord != null) {
            medicalRecord.viewMedicalRecord(this);
        } else {
            System.out.println("Medical record not available.");
        }
    }

    private void updatePersonalInformation() {
        System.out.println("\n=== Update Personal Information ===");
        
        // Current contact information
        System.out.println("Current Contact: " + this.contact);
        
        // Prompt for new contact
        System.out.print("Enter new contact (press enter to keep current): ");
        String newContact = scanner.nextLine().trim();
        
        if (!newContact.isEmpty()) {
            this.contact = newContact;
            System.out.println("Contact updated successfully.");
        }
        
        System.out.println("Personal information update completed.");
    }

    

}