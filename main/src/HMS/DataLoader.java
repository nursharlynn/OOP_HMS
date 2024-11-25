package HMS;

import Administrator.Administrator;
import Administrator.Medicine;
import Doctor.Doctor;
import Patient.Patient;
import Pharmacist.Pharmacist;
import User.LoginSystem;
import User.User;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

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
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String staffId = data[0].trim();
                String name = data[1].trim();
                String role = data[2].trim();
                String gender = data[3].trim();
                int age = Integer.parseInt(data[4].trim());

                User user = createUserByRole(staffId, name, role, gender, age);
                if (user != null) {
                    loginSystem.addUser(user);
                    saveUserCredentials(staffId);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading staff file: " + e.getMessage());
        }

    }

    public User createUserByRole(String staffId, String name, String role, String gender, int age) {
        switch (role.toLowerCase()) {
            case "doctor":
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
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String patientId = data[0].trim();
                String name = data[1].trim();
                String dateOfBirth = data[2].trim();
                String gender = data[3].trim();
                String bloodType = data[4].trim();
                String contact = data[5].trim();

                String diagnosis = data.length > 6 ? data[6].trim() : "";
                String treatment = data.length > 7 ? data[7].trim() : "";

                Patient patient = new Patient(patientId, "password", name, dateOfBirth, gender, bloodType, contact, 0);

                patient.setDiagnosis(diagnosis);
                patient.setTreatment(treatment);

                loginSystem.addUser(patient);
                saveUserCredentials(patientId);
            }
        } catch (IOException e) {
            System.err.println("Error reading patient file: " + e.getMessage());
        }
    }

    private void loadMedicineData() {
        List<Medicine> medicines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(medicineFilePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String medicineName = data[0].trim();
                int initialStock = Integer.parseInt(data[1].trim());
                int lowStockAlert = Integer.parseInt(data[2].trim());

                Medicine medicine = new Medicine(medicineName, initialStock, lowStockAlert);
                medicines.add(medicine);
            }
        } catch (IOException e) {
            System.err.println("Error reading medicine file: " + e.getMessage());
        }
    }

    public static List<String> readAvailableMedicines() {
        try {
            Path medicinePath = Paths.get("data/Medicine_List.csv");
            return Files.lines(medicinePath)
                    .skip(1)
                    .map(line -> line.split(",")[0].trim())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error reading medicine file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Medicine> getAllMedicines() {
        List<Medicine> medicines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(medicineFilePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String medicineName = data[0].trim();
                int initialStock = Integer.parseInt(data[1].trim());
                int lowStockAlert = Integer.parseInt(data[2].trim());

                Medicine medicine = new Medicine(medicineName, initialStock, lowStockAlert);
                medicines.add(medicine);
            }
        } catch (IOException e) {
            System.err.println("Error reading medicine file: " + e.getMessage());
        }
        return medicines;
    }

    public void saveMedicines(List<Medicine> medicines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(medicineFilePath))) {
            writer.write("Medicine Name,Initial Stock,Low Stock Level Alert\n"); // Write header
            for (Medicine medicine : medicines) {
                writer.write(String.format("%s,%d,%d%n",
                        medicine.getMedicineName(),
                        medicine.getStock(),
                        medicine.getLowStockLevelAlert()));
            }
        } catch (IOException e) {
            System.err.println("Error saving medicines: " + e.getMessage());
        }
    }

    public void saveStaffList(List<User> staffList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(staffFilePath))) {
            writer.write("Staff ID,Name,Role,Gender,Age\n");
            for (User staff : staffList) {
                writer.write(String.format("%s,%s,%s,%s,%d%n",
                        staff.getHospitalId(),
                        staff.getName(),
                        staff.getRole(),
                        staff.getGender(),
                        staff.getAge()));
            }
        } catch (IOException e) {
            System.err.println("Error saving staff list: " + e.getMessage());
        }
    }

    public List<User> getAllStaff() {
        List<User> staffList = new ArrayList<>();
        String staffFilePath = "data/Staff_List.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(staffFilePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 5)
                    continue;

                String staffId = data[0].trim();
                String name = data[1].trim();
                String role = data[2].trim();
                String gender = data[3].trim();
                int age = Integer.parseInt(data[4].trim());

                User user = createUserByRole(staffId, name, role, gender, age);
                if (user != null) {
                    staffList.add(user);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading staff file: " + e.getMessage());
        }

        return staffList;
    }

    public String getStaffFilePath() {
        return staffFilePath;
    }

    public void updatePatientContact(String patientId, String newContact) throws IOException {
        Path path = Path.of(patientFilePath);
        List<String> lines = Files.readAllLines(path);

        for (int i = 1; i < lines.size(); i++) {
            String[] data = lines.get(i).split(",");
            if (data[0].trim().equals(patientId)) {
                data[5] = newContact;
                lines.set(i, String.join(",", data));
                break;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), false))) {
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
        }
    }

    public void saveUserCredentials(String userId) {
        String credentialsFilePath = "data/UserCredentials.csv";
        Set<String> existingUserIds = new HashSet<>();

        try {
            File credentialsFile = new File(credentialsFilePath);
            File parentDir = credentialsFile.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            boolean fileExists = credentialsFile.exists();

            if (fileExists) {
                List<String> lines = Files.readAllLines(credentialsFile.toPath());
                for (String line : lines) {
                    String[] data = line.split(",");
                    if (data.length == 2) {
                        existingUserIds.add(data[0].trim());
                    }
                }
            } else {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(credentialsFilePath))) {
                    writer.write("HospitalID,Password\n");
                }
            }

            if (existingUserIds.contains(userId)) {
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(credentialsFilePath, true))) {
                writer.write(String.format("%s,password%n", userId));
            }

        } catch (IOException e) {
            System.err.println("Error saving user credentials: " + e.getMessage());
        }
    }

    public void removeUserCredentials(String userId) {
        String credentialsFilePath = "data/UserCredentials.csv";
        try {
            List<String> lines = Files.readAllLines(Paths.get(credentialsFilePath));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String[] data = line.split(",");
                if (data.length > 0 && !data[0].trim().equals(userId)) {
                    updatedLines.add(line);
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(credentialsFilePath))) {
                for (String updatedLine : updatedLines) {
                    writer.write(updatedLine + System.lineSeparator());
                }
            }

        } catch (IOException e) {
            System.err.println("Error removing user credentials: " + e.getMessage());
        }
    }

}
