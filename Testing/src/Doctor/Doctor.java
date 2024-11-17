package Doctor;

import User.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

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

        while (true) {
            System.out.print("Enter Date (YYYY-MM-DD): ");
            date = scanner.nextLine();
            try {
                LocalDate.parse(date); 
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format or value. Please use YYYY-MM-DD and ensure the date is valid.");
            }
        }

        while (true) {
            System.out.print("Enter Time (HH:MM): ");
            time = scanner.nextLine();

            String[] timeParts = time.split(":");
            if (timeParts.length != 2) {
                System.out.println("Invalid time format. Please use HH:MM.");
                continue; 
            }

            try {
                int hours = Integer.parseInt(timeParts[0]);
                int minutes = Integer.parseInt(timeParts[1]);

                if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
                    System.out.println("Invalid time. Please ensure the time is within 00:00 to 23:59.");
                    continue;
                }
                break; 
            } catch (NumberFormatException e) {
                System.out.println("Invalid time. Please ensure the time is in the correct format.");
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