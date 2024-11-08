package HMS;

import Administrator.Administrator;
import Administrator.Medicine;
import Doctor.Doctor;
import Patient.Patient;
import Pharmacist.Pharmacist;
import User.LoginSystem;
import User.User;
import java.io.*;
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
    
                // Create user based on role with default password "password"
                User user = createUserByRole(staffId, name, role, gender);
                if (user != null) {
                    loginSystem.addUser(user);
                    System.out.println("Added user: " + name + " (" + role + ")");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading staff file: " + e.getMessage());
    }
    
}

    private User createUserByRole(String staffId, String name, String role, String gender) {
        switch (role.toLowerCase()) {
            case "doctor":
                return new Doctor(staffId, "password", name, gender);
                
            case "pharmacist":
                return new Pharmacist(staffId, "password", name, gender);
                
            case "administrator":
                return new Administrator(staffId, "password", name, gender);
                
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
            
            while ((line = br.readLine()) != null) {
                // Split by tab
                String[] data = line.split(",");
                String patientId = data[0].trim();
                String name = data[1].trim();
                String dateOfBirth = data[2].trim();
                String gender = data[3].trim();
                String bloodType = data[4].trim();
                String contact = data[5].trim();
    
                // Update the date parsing to handle different formats
                Patient patient = new Patient(patientId, "password", name, dateOfBirth, gender, bloodType, contact);
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