package Doctor;

import User.*;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Doctor extends User {

    private IAppointmentRepository apptHandler;
    private Scanner scanner;
    private int age;

    public Doctor(String hospitalId, String password, String name, String gender, int age) {
        super(hospitalId, password, name, gender, "Doctor", age);
        this.scanner = new Scanner(System.in);
        this.apptHandler = new DoctorAppointmentManager();
        this.age = age;
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
                viewPatientMedicalRecords();
                break;
            case 2:
                updatePatientMedicalRecords();
                break;
            case 3:
                viewPersonalSchedule();
                break;
            case 4:
                setAvailability();
                break;
            case 5:
                apptHandler.reviewAppointmentRequests(this);
                break;
            case 6:
                apptHandler.viewUpcomingAppointments(this);
                break;
            case 7:
                ((DoctorAppointmentManager) apptHandler).recordAppointmentOutcome(this);
                break;
            case 8:
                System.out.println("Logging out...");
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public List<String> viewPersonalSchedule() {
        DoctorAppointmentManager appointmentManager = new DoctorAppointmentManager();
        return appointmentManager.viewPersonalSchedule(this);
    }

    public void setAvailability() {
        Scanner scanner = new Scanner(System.in);

        String date;
        String time;
        String datePattern = "^\\d{4}-\\d{2}-\\d{2}$";
        String timePattern = "^\\d{2}:\\d{2}$";

        while (true) {
            System.out.print("Enter Date (YYYY-MM-DD): ");
            date = scanner.nextLine();
            if (Pattern.matches(datePattern, date)) {
                break;
            } else {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }

        while (true) {
            System.out.print("Enter Time (HH:MM): ");
            time = scanner.nextLine();
            if (Pattern.matches(timePattern, time)) {
                break;
            } else {
                System.out.println("Invalid time format. Please use HH:MM.");
            }
        }

        DoctorAppointmentManager appointmentManager = new DoctorAppointmentManager();
        appointmentManager.setAvailability(this, date, time);
    }

    private void updatePatientMedicalRecords() {
        PatientRecordManager recordManager = new PatientRecordManager();
        recordManager.updatePatientMedicalRecords(this);
    }

    public void viewPatientMedicalRecords() {
        PatientRecordService patientRecordService = new PatientRecordService();
        patientRecordService.viewPatientMedicalRecords(this, this.scanner);
    }

}