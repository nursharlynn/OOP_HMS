package Doctor;

import Appointment.*;
import Records.*;
import User.*;
import java.time.LocalDateTime;
import java.util.List;

public class Doctor extends User {

    private Schedule schedule;
    private IAppointment apptHandler;
    private MedicalRecord medicalRecord;

    public Doctor(String hospitalId, String password, String name, String gender) {
        super(hospitalId, password, name, gender);
    }

    @Override
    public void displayMenu() {
        System.out.println("\n--- Doctor Menu ---");
        System.out.println("1. View Patient Medical Records");
        System.out.println("2. Update Patient Medical Records");
        System.out.println("3. View Personal Schedule");
        System.out.println("4. Set Availability for Appointments");
        System.out.println("5. Accept or Decline Appointment Requests");
        System.out.println("6. View Upcoming Appointments");
        System.out.println("7. Record Appointment Outcome");
        System.out.println("8. Logout");
    }

	@Override
	public void handleMenuChoice(int choice) {
    switch (choice) {
        case 1:
            System.out.println("View Patient Medical Records - Not implemented yet");
            break;
        case 2:
            System.out.println("Update Patient Medical Records - Not implemented yet");
            break;
        case 3:
            System.out.println("View Personal Schedule - Not implemented yet");
            break;
        case 4:
            System.out.println("Set Availability for Appointments - Not implemented yet");
            break;
        case 5:
            System.out.println("Accept or Decline Appointment Requests - Not implemented yet");
            break;
        case 6:
            System.out.println("View Upcoming Appointments - Not implemented yet");
            break;
        case 7:
            System.out.println("Record Appointment Outcome - Not implemented yet");
            break;
        case 8:
            System.out.println("Logging out...");
            return; // This will exit the menu loop and return to main menu
        default:
            System.out.println("Invalid choice. Please try again.");
    }
}

    /**
     * Sets the appointment handler for the doctor
     * @param apptHandler Appointment handler
     */
    public void setApptHandler(IAppointment apptHandler) {
        this.apptHandler = apptHandler;
    }

    /**
     * Sets the schedule for the doctor
     * @param schedule Doctor's schedule
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    // Placeholder methods for potential future implementation
    public List<String> viewPersonalSchedule() {
        System.out.println("View Personal Schedule - Not implemented");
        return null;
    }

    /**
     * Updates doctor's availability
     * @param start Start time for availability
     * @return boolean indicating if update was successful
     */
    public boolean updateAvailability(LocalDateTime start) {
        System.out.println("Update Availability - Not implemented");
        return false;
    }

    /**
     * Updates patient's medical records
     * @param patientId Patient's ID
     * @param newDiagnosis New diagnosis
     * @param prescriptions Prescriptions
     * @param newTreatment New treatment
     */
    public void updatePatientMedicalRecords(String patientId, String newDiagnosis, 
                                            String prescriptions, String newTreatment) {
        System.out.println("Update Patient Medical Records - Not implemented");
    }

    @Override
    public String toString() {
        return "Doctor: " + getName() + " (ID: " + getHospitalId() + ")";
    }
}