package Patient;

import Appointment.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PatientAppointmentManager implements IAppointmentsHandler {

    private static final String APPOINTMENTS_FILE = "data/Appointments.csv";
    private Scanner scanner;

    public PatientAppointmentManager() {
        this.scanner = new Scanner(System.in);
    }

    private List<String[]> getPatientBookedAppointments(String hospitalId) throws IOException {
        Path path = Paths.get(APPOINTMENTS_FILE);
        List<String> lines = Files.readAllLines(path);

        return lines.stream()
                .skip(1)
                .map(line -> line.split(","))
                .filter(data -> data.length > 5 &&
                        data[4].trim().equalsIgnoreCase("Booked") &&
                        data[5].trim().equals(hospitalId))
                .collect(Collectors.toList());
    }

    private int displayBookedAppointments(List<String[]> patientBookedAppointments) {
        if (patientBookedAppointments.isEmpty()) {
            System.out.println("No booked appointments found.");
            return -1;
        }

        System.out.println("\n=== Your Current Booked Appointments ===");
        for (int i = 0; i < patientBookedAppointments.size(); i++) {
            String[] appointment = patientBookedAppointments.get(i);
            System.out.printf("%d. Doctor: %s, Date: %s, Time: %s%n",
                    i + 1, appointment[1], appointment[2], appointment[3]);
        }

        System.out.print("\nEnter the number of the appointment: ");
        int appointmentChoice = scanner.nextInt();
        scanner.nextLine();

        if (appointmentChoice < 1 || appointmentChoice > patientBookedAppointments.size()) {
            System.out.println("Invalid appointment selection.");
            return -1;
        }

        return appointmentChoice;
    }

    @Override
    public void rescheduleAppointments(String hospitalId) {
        try {
            List<String[]> patientBookedAppointments = getPatientBookedAppointments(hospitalId);

            int appointmentChoice = displayBookedAppointments(patientBookedAppointments);
            if (appointmentChoice == -1)
                return;

            String[] currentAppointment = patientBookedAppointments.get(appointmentChoice - 1);
            int currentAppointmentId = Integer.parseInt(currentAppointment[0]);

            System.out.println("\nSelect a new appointment to reschedule your current appointment.");

            List<String[]> availableAppointments = viewAvailableSlots();

            if (availableAppointments.isEmpty()) {
                System.out.println("No available appointments to reschedule.");
                return;
            }

            System.out.print("Enter the Appointment ID you want to book: ");
            int newAppointmentId = scanner.nextInt();
            scanner.nextLine();

            performRescheduling(currentAppointmentId, newAppointmentId, hospitalId);

        } catch (IOException e) {
            System.out.println("Error occurred while rescheduling appointment.");
        }
    }

    private void performRescheduling(int currentAppointmentId, int newAppointmentId, String hospitalId)
            throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(APPOINTMENTS_FILE));

        boolean currentAppointmentFound = false;
        boolean newAppointmentFound = false;

        for (int i = 1; i < lines.size(); i++) {
            String[] data = lines.get(i).split(",");

            if (Integer.parseInt(data[0]) == currentAppointmentId) {
                lines.set(i, String.format("%s,%s,%s,%s,Available,,",
                        data[0], data[1], data[2], data[3]));
                currentAppointmentFound = true;
            }

            if (Integer.parseInt(data[0]) == newAppointmentId) {
                if (data.length >= 5 && data[4].trim().equalsIgnoreCase("Available")) {
                    String patientName = "";
                    for (String[] searchData : getPatientBookedAppointments(hospitalId)) {
                        if (Integer.parseInt(searchData[0]) == currentAppointmentId) {
                            patientName = searchData.length > 6 ? searchData[6] : "";
                            break;
                        }
                    }

                    lines.set(i, String.format("%s,%s,%s,%s,Booked,%s,%s",
                            data[0], data[1], data[2], data[3], hospitalId, patientName));
                    newAppointmentFound = true;
                } else {
                    System.out.println("The selected appointment is not available.");
                    return;
                }
            }
        }

        if (!currentAppointmentFound || !newAppointmentFound) {
            System.out.println("Unable to find appointments.");
            return;
        }

        Files.write(Paths.get(APPOINTMENTS_FILE), lines);

        System.out.println("Appointment successfully rescheduled.");
    }

    @Override
    public void cancelAppointment(String hospitalId) {
        try {
            List<String[]> patientBookedAppointments = getPatientBookedAppointments(hospitalId);

            int appointmentChoice = displayBookedAppointments(patientBookedAppointments);
            if (appointmentChoice == -1)
                return;

            String[] appointmentToCancel = patientBookedAppointments.get(appointmentChoice - 1);
            int appointmentId = Integer.parseInt(appointmentToCancel[0]);

            performCancellation(appointmentId, hospitalId);

        } catch (IOException e) {
            System.out.println("Error occurred while canceling appointment.");
            e.printStackTrace();
        }
    }

    private void performCancellation(int appointmentId, String hospitalId) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(APPOINTMENTS_FILE));

        boolean appointmentFound = false;

        for (int i = 1; i < lines.size(); i++) {
            String[] data = lines.get(i).split(",");

            if (Integer.parseInt(data[0]) == appointmentId &&
                    data.length > 5 &&
                    data[4].trim().equalsIgnoreCase("Booked") &&
                    data[5].trim().equals(hospitalId)) {

                lines.set(i, String.format("%s,%s,%s,%s,Available,,",
                        data[0], data[1], data[2], data[3]));

                appointmentFound = true;
                break;
            }
        }

        if (appointmentFound) {
            Files.write(Paths.get(APPOINTMENTS_FILE), lines);
            System.out.println("Appointment successfully canceled.");
        } else {
            System.out.println("Unable to cancel the appointment.");
        }
    }

    @Override
    public void viewPastAppointmentOutcomeRecords(String patientId) {
        AppointmentOutcomeViewer.viewAppointmentOutcomeRecords(System.out, patientId);
    }

    @Override
    public void scheduleAppointments(Patient patient, int appointmentId) {
        try {
            Path path = Paths.get("data/Appointments.csv");
            List<String> lines = Files.readAllLines(path);

            boolean appointmentFound = false;
            for (int i = 1; i < lines.size(); i++) {
                String[] data = lines.get(i).split(",");

                if (Integer.parseInt(data[0]) == appointmentId &&
                        data[4].trim().equalsIgnoreCase("Available")) {

                    lines.set(i, String.format("%s,%s,%s,%s,Booked,%s,%s",
                            data[0],
                            data[1],
                            data[2],
                            data[3],
                            patient.getHospitalId(),
                            patient.getName()));

                    appointmentFound = true;
                    break;
                }
            }

            if (appointmentFound) {
                Files.write(path, lines);
                System.out.println("Appointment successfully booked!");
            } else {
                System.out.println("Appointment not found or no longer available.");
            }
        } catch (IOException e) {
            System.out.println("Error booking appointment: " + e.getMessage());
        }
    }

    @Override
    public List<String[]> viewAvailableSlots() {
        List<String[]> availableAppointments = new ArrayList<>();
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

        try (BufferedReader br = new BufferedReader(new FileReader("data/Appointments.csv"))) {
            String line;
            System.out.printf("%-10s %-20s %-15s %-15s %-15s%n",
                    "Appt ID", "Doctor Name", "Date", "Time", "Status");
            System.out.println("-".repeat(75));

            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length >= 5 && data[4].trim().equalsIgnoreCase("Available")) {
                    String formattedDate;
                    try {
                        formattedDate = outputFormat.format(inputFormat.parse(data[2]));
                    } catch (ParseException e) {
                        formattedDate = data[2];
                    }

                    System.out.printf("%-10s %-20s %-15s %-15s %-15s%n",
                            data[0], data[1], formattedDate, data[3], data[4]);

                    availableAppointments.add(data);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading appointments: " + e.getMessage());
        }

        return availableAppointments;
    }

    public void viewScheduledAppointments(String hospitalId) {
        try {
            Path path = Paths.get(APPOINTMENTS_FILE);
            List<String> lines = Files.readAllLines(path);
            List<String[]> patientAppointments = lines.stream()
                    .skip(1)
                    .map(line -> line.split(","))
                    .filter(data -> data.length > 5 &&
                            data[5].trim().equals(hospitalId))
                    .collect(Collectors.toList());

            if (patientAppointments.isEmpty()) {
                System.out.println("No scheduled appointments found.");
                return;
            }

            System.out.println("\n=== Your Scheduled Appointments ===");
            System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s%n",
                    "Appt ID", "Doctor Name", "Date", "Time", "Status", "Patient Name");
            System.out.println("-".repeat(90));

            for (String[] appointment : patientAppointments) {
                System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s%n",
                        appointment[0],
                        appointment[1],
                        appointment[2],
                        appointment[3],
                        appointment[4],
                        appointment[6]);
            }
        } catch (IOException e) {
            System.out.println("Error occurred while retrieving scheduled appointments.");
        }
    }
}
