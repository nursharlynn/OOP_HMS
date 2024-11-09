package project;
import java.util.*;

import java.time.LocalDateTime;

public class HMS {
    private static Map<String, List<LocalDateTime>> availableSlots = new HashMap<>();
    private static Map<String, Appointment> appointmentMap = new HashMap<>();
    public static Map<String, List<LocalDateTime>> getAllAvailableSlots() {
        return availableSlots;
    }

    public static List<Appointment> getAppointmentsForPatient(String patientId) {
        List<Appointment> appointments = new ArrayList<>();
        for (Appointment appointment : appointmentMap.values()) {
            if (appointment.getPatientId().equals(patientId)) {
                appointments.add(appointment);
            }
        }
        return appointments;
    }

    public static Appointment scheduleAppointment(String patientId, String doctorId, LocalDateTime dateTime) {
        if (!availableSlots.containsKey(doctorId)) {
            System.out.println("Doctor ID does not exist.");
            return null;
        }
        
        List<LocalDateTime> slots = availableSlots.get(doctorId);
        if (slots == null || !slots.contains(dateTime)) {
            System.out.println("The selected slot is not available.");
            return null;
        }
        
        slots.remove(dateTime);
        String appointmentId = generateAppointmentId();
        Appointment appointment = new Appointment(appointmentId, patientId, doctorId, dateTime);   
        appointmentMap.put(appointmentId, appointment);
        System.out.println("Appointment successfully scheduled!");
        return appointment;
    }

    public static boolean rescheduleAppointment(String appointmentId, String newDoctorId, LocalDateTime newDateTime) {
        Appointment appointment = appointmentMap.get(appointmentId);
        if (appointment == null) {
            System.out.println("Appointment not found!");
            return false;
        }

        availableSlots.get(appointment.getDoctorId()).add(appointment.getDateTime());
        
        if (!availableSlots.containsKey(newDoctorId) || !availableSlots.get(newDoctorId).contains(newDateTime)) {
            System.out.println("The new slot is not available.");
            return false;
        }
        
        availableSlots.get(newDoctorId).remove(newDateTime);

        appointment.setDoctorId(newDoctorId);
        appointment.setDateTime(newDateTime);
        appointment.setStatus("rescheduled");

        appointmentMap.put(appointment.getAppointmentId(), appointment);
        
        System.out.println("Appointment successfully rescheduled!");
        return true;
    }

    public static boolean cancelAppointment(String appointmentId) {
        Appointment appointment = appointmentMap.get(appointmentId);
        if (appointment == null) {
            System.out.println("Appointment not found!");
            return false;
        }

        availableSlots.get(appointment.getDoctorId()).add(appointment.getDateTime());
        appointment.setStatus("canceled");
        appointmentMap.remove(appointmentId);
        System.out.println("Appointment successfully canceled!");
        return true;
    }

    public static Appointment getAppointment(String appointmentId) {
        return appointmentMap.get(appointmentId);
    }

    public static void addDoctor(String doctorId, List<LocalDateTime> slots) {
        availableSlots.put(doctorId, new ArrayList<>(slots));
    }

    private static String generateAppointmentId() {
        return "APPT-" + System.currentTimeMillis();
    }

    public static List<Appointment> getUpcomingAppointmentsForDoctor(String doctorId) {
        List<Appointment> upcomingAppointments = new ArrayList<>();
        for (Appointment appointment : appointmentMap.values()) {
            if (appointment.getDoctorId().equals(doctorId) && appointment.getStatus().equals("scheduled")) {
                upcomingAppointments.add(appointment);
            }
        }
        return upcomingAppointments;
    }

    public static List<Appointment> getConfirmedAppointmentsForDoctor(String doctorId) {
        List<Appointment> confirmedAppointments = new ArrayList<>();
        for (Appointment appointment : appointmentMap.values()) {
            if (appointment.getDoctorId().equals(doctorId) && appointment.getStatus().equals("confirmed")) {
                confirmedAppointments.add(appointment);
            }
        }
        return confirmedAppointments;
    }

    public static List<Appointment> getAllAppointmentsForDoctor(String doctorId) {
        List<Appointment> allAppointments = new ArrayList<>();
        for (Appointment appointment : appointmentMap.values()) {
            if (appointment.getDoctorId().equals(doctorId)) {
                allAppointments.add(appointment);
            }
        }
        return allAppointments;
    }

    public static List<LocalDateTime> getAvailableSlotsForDoctor(String doctorId) {
        return availableSlots.get(doctorId);
    }
}


/* public class HMS {
    private static Map<String, Patient> patients = new HashMap<>();
    private static Map<String, Doctor> doctors = new HashMap<>();
    private static Map<String, Appointment> appointments = new HashMap<>();
    private static Map<Integer, Appointment> appointmentRecords = new HashMap<>();
    private static Map<String, PatientRecord> patientRecordMap = new HashMap<>();
    private static int appointmentCounter = 1;

    public static void addPatient(Patient patient) {
        patients.put(patient.getId(), patient);
    }
    public static void addDoctor(Doctor doctor) {
        doctors.put(doctor.getId(), doctor);
    }

    public static Appointment getAppointmentById(int id) {
        return appointmentRecords.get(id);
    }

    public static Patient getPatientById(String patientId) {
        return patients.get(patientId);
    }

    public static Doctor getDoctorById(String doctorId) {
        return doctors.get(doctorId);
    }

    public static Appointment scheduleAppointment(String patientId, String doctorId, String date, String time) {
        String appointmentId = "A" + appointmentCounter++;
        Appointment appointment = new Appointment(appointmentId, patientId, doctorId, date, time);
        appointments.put(appointmentId, appointment);
        appointmentRecords.put(appointment.getAppointmentId(), appointment);
        System.out.println("Scheduled appointment " + appointmentId);
        return appointment;
    }

    public static boolean rescheduleAppointment(int appointmentId, String newDate, String newTime) {
        Appointment appointment = appointmentRecords.get(appointmentId);
        if (appointment != null && appointment.getStatus().equals("scheduled")) {
            appointment.setDate(newDate);
            appointment.setTime(newTime);
            appointment.setStatus("rescheduled");  // You can set a new status like "rescheduled" if necessary
            return true;
        }
        return false;
    }

    public static boolean cancelAppointment(int appointmentId) {
        Appointment appointment = appointmentRecords.get(appointmentId);
        if (appointment != null && appointment.getStatus().equals("scheduled")) {
            appointment.setStatus("canceled");
            return true;
        }
        return false;
    }

    public static List<Appointment> getPastAppointments(String patientId) {
        List<Appointment> pastAppointments = new ArrayList<>();
        for (Appointment appt : appointmentRecords.values()) {
            if (appt.getPatientId().equals(patientId) && appt.getStatus().equals("completed")) {
                pastAppointments.add(appt);
            }
        }
        return pastAppointments;
    }
    
    public static Map<Integer, PatientRecord> getPatientRecordMap() {
        return patientRecordMap;  // Ensure you have a map for patient records in your HMS class
    }

    
    public static Map<String, List<String>> getAllAvailableSlots() {
        Map<String, List<String>> availableSlotsMap = new HashMap<>();
        for (Doctor doctor : doctors.values()) {
            availableSlotsMap.put(doctor.getId(), doctor.getAvailableSlots());
        }
        return availableSlotsMap;
    }

    
} */
