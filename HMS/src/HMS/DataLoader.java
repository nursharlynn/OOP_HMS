package HMS;

import Administrator.Administrator;
import Administrator.Medicine;
import Appointment.Schedule;
import Doctor.Doctor;
import Doctor.DoctorAppointmentManager;
import Doctor.IAppointment;
import Patient.Patient;
import Pharmacist.Pharmacist;
import User.LoginSystem;
import User.User;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataLoader {
    private LoginSystem loginSystem;
    private String staffFilePath = "data/Staff_List.csv";
    private String patientFilePath = "data/Patient_List.csv";
    private String medicineFilePath = "data/Medicine_List.csv";

    public DataLoader(LoginSystem loginSystem) {
        this.loginSystem = loginSystem;
    }

    public void loadAllData() {
        loadStaffData();
        loadPatientData();
        loadMedicineData();
    }

    private void loadStaffData() {
        try (BufferedReader br = new BufferedReader(new FileReader(staffFilePath))) {
            String line;
            // Skip header line
            br.readLine();
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String staffId = data[0].trim();
                String name = data[1].trim();
                String role = data[2].trim();
                String gender = data[3].trim();
                int age = Integer.parseInt(data[4].trim());

                // Create user based on role with default password "password"
                User user = createUserByRole(staffId, name, role, gender, age);
                if (user != null) {
                    loginSystem.addUser(user);
                    System.out.println("Added user: " + name + " (" + role + ")");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading staff file: " + e.getMessage());
        }
    }

    private User createUserByRole(String staffId, String name, String role, String gender, int age) {
        switch (role.toLowerCase()) {
            case "doctor":
                Schedule schedule = new Schedule();
                IAppointment apptHandler = new DoctorAppointmentManager();
                return new Doctor(staffId, "password", name, gender, age);
                
            case "pharmacist":
                return new Pharmacist(staffId, "password", name, gender, age);
                
            case "administrator":
                return new Administrator(staffId, "password", name, gender, age);
                
            default:
                System.out.println("Unknown role: " + role);
                return null;
        }
    }

    private void loadPatientData() {
        try (BufferedReader br = new BufferedReader(new FileReader(patientFilePath))) {
            String line;
            // Skip header
            br.readLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String patientId = data[0].trim();
                String name = data[1].trim();
                LocalDateTime dob = LocalDateTime.parse(data[2].trim() + "T00:00:00");
                String gender = data[3].trim();
                String bloodType = data[4].trim();
                String contact = data[5].trim();
    
                // Adjusted constructor call
                Patient patient = new Patient(patientId, "password", dob, gender, contact, bloodType);
                loginSystem.addUser(patient);
                System.out.println("Added patient: " + name);
            }
        } catch (IOException e) {
            System.err.println("Error reading patient file: " + e.getMessage());
        }
    }

    private void loadMedicineData() {
        List<Medicine> medicines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(medicineFilePath))) {
            String line;
            // Skip header
            br.readLine();
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String medicineName = data[0].trim();
                int initialStock = Integer.parseInt(data[1].trim());
                int lowStockAlert = Integer.parseInt(data[2].trim());
                
                Medicine medicine = new Medicine(medicineName, initialStock, lowStockAlert);
                medicines.add(medicine);
                System.out.println("Added medicine: " + medicineName);
            }
        } catch (IOException e) {
            System.err.println("Error reading medicine file: " + e.getMessage());
        }
    }
}