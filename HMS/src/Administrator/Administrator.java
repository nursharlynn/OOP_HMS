package Administrator;

import Appointment.*;
import Doctor.Doctor;
import HMS.DataLoader;
import Pharmacist.Pharmacist;
import User.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Administrator extends User {

    private IInventory invHandler;
    private Collection<IRequestManager> reqHandler;
    private ArrayList<Appointment> appointments;
    private IManageStaff staffHandler;
    private LoginSystem loginSystem;
    private int age;
    
    public Administrator(String hospitalId, String password, String name, String gender, int age) {
        super(hospitalId, password, name, gender, "Administrator"); // Set role to "Administrator"
        this.age = age;
    }

    @Override
    public void displayMenu() {
        System.out.println("\n--- Administrator Menu ---");
        System.out.println("1. Manage Staff");
        System.out.println("2. View Staff");
        System.out.println("3. Manage Inventory");
        System.out.println("4. View Inventory");
        System.out.println("5. Manage Replenishment Requests");
        System.out.println("6. View Replenishment Requests");
        System.out.println("7. View Scheduled Appointments");
        System.out.println("8. Generate Reports");
        System.out.println("9. Logout");
    }

    @Override
public void handleMenuChoice(int choice) {
    switch (choice) {
        case 1:
            manageStaff(); // Call the method to manage staff
            break;
        case 2:
            viewStaff(); // Call the method to view staff
            break;
        case 3:
            manageStockLevels(); // Handle stock management
            break;
        case 4:
            viewMedicineList(); // Handle viewing medicine list
            break;
        case 5:
            approveReplenishmentRequests(); // Handle approving replenishment requests
            break;
        case 6:
            viewReplenishmentRequests(); // Handle viewing replenishment requests
            break;
        case 7:
            viewAllAppointments(); // Call the new method
            break;
        case 8:
            System.out.println("Generate Reports - Not implemented yet");
            break;
        case 9:
            System.out.println("Logging out...");
            return; // This will exit the menu loop
        default:
            System.out.println("Invalid choice. Please try again.");
    }
}

    public void setInvHandler(IInventory invHandler) {
        this.invHandler = invHandler;
    }

    public void setReqHandler(Collection<IRequestManager> reqHandler) {
        this.reqHandler = reqHandler;
    }

    public void setStaffHandler(IManageStaff staffHandler) {
        this.staffHandler = staffHandler;
    }

    // Placeholder methods for potential functionalities
    public void manageStaff() {
        DataLoader dataLoader = new DataLoader(loginSystem); // Pass an instance of LoginSystem
        List<User> staffList = dataLoader.getAllStaff(); // Fetch staff list
    
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Manage Staff ---");
        System.out.println("1. Add Staff Member");
        System.out.println("2. Update Staff Member");
        System.out.println("3. Remove Staff Member");
        System.out.print("Choose an option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
    
        if (choice == 1) {
            addStaffMember(staffList, dataLoader); // Call the method to add staff member
        } else if (choice == 2) {
            updateStaffMember(staffList, dataLoader); // Call the method to update staff member
        } else if (choice == 3) {
            removeStaffMember(staffList, dataLoader); // Call the method to remove staff member
        } else {
            System.out.println("Invalid option.");
        }
    }

    private void addStaffMember(List<User> staffList, DataLoader dataLoader) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter Staff ID: ");
        String staffId = scanner.nextLine().trim();
    
        // Check if staff ID already exists
        for (User  staff : staffList) {
            if (staff.getHospitalId().equals(staffId)) {
                System.out.println("Staff ID already exists. Please enter a unique Staff ID.");
                return;
            }
        }
    
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
    
        System.out.print("Enter Role (Doctor/Pharmacist/Administrator): ");
        String role = scanner.nextLine().trim();
    
        // Validate role
        if (!role.equalsIgnoreCase("Doctor") && !role.equalsIgnoreCase("Pharmacist") && !role.equalsIgnoreCase("Administrator")) {
            System.out.println("Invalid role. Staff member not added.");
            return;
        }
    
        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine().trim();
    
        System.out.print("Enter Age: ");
        int age = 0;
        try {
            age = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            System.out.println("Invalid age input. Staff member not added.");
            scanner.nextLine(); // Clear the invalid input
            return;
        }
    
        // Create new staff member based on role
        User newStaffMember = createUserByRole(staffId, name, role, gender, age);
        if (newStaffMember != null) {
            staffList.add(newStaffMember); // Add to staff list
            dataLoader.saveStaffList(staffList); // Save updated staff list to CSV
            System.out.println("Staff member added successfully.");
        } else {
            System.out.println("Error adding staff member.");
        }
    }

    private void updateStaffMember(List<User> staffList, DataLoader dataLoader) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Select staff member to update by row number:");
        for (int i = 0; i < staffList.size(); i++) {
            System.out.println((i + 1) + ". " + staffList.get(i));
        }
        int rowToUpdate = scanner.nextInt() - 1; // Convert to zero-based index
        scanner.nextLine(); // Consume newline
    
        if (rowToUpdate >= 0 && rowToUpdate < staffList.size()) {
            User staffMember = staffList.get(rowToUpdate);
    
            // Prompt for fields to update
            System.out.print("Enter new name (leave blank to keep current: " + staffMember.getName() + "): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                staffMember.setName(newName); // Assuming setName method exists
            }
    
            System.out.print("Enter new role (leave blank to keep current: " + staffMember.getRole() + "): ");
            String newRole = scanner.nextLine();
            if (!newRole.isEmpty()) {
                // Update role only if it's valid
                if (newRole.equalsIgnoreCase("Doctor") || newRole.equalsIgnoreCase("Pharmacist") || newRole.equalsIgnoreCase("Administrator")) {
                    staffMember.setRole(newRole); // Assuming setRole method exists
                } else {
                    System.out.println("Invalid role. Keeping the current role: " + staffMember.getRole());
                }
            }
    
            System.out.print("Enter new gender (leave blank to keep current: " + staffMember.getGender() + "): ");
            String newGender = scanner.nextLine();
            if (!newGender.isEmpty()) {
                staffMember.setGender(newGender); // Assuming setGender method exists
            }
    
            System.out.print("Enter new age (leave blank to keep current: " + staffMember.getAge() + "): ");
            String newAgeInput = scanner.nextLine();
            if (!newAgeInput.isEmpty()) {
                try {
                    int newAge = Integer.parseInt(newAgeInput);
                    staffMember.setAge(newAge); // Assuming setAge method exists
                } catch (NumberFormatException e) {
                    System.out.println("Invalid age input. Keeping the current age: " + staffMember.getAge());
                }
            }
    
            // Save updated staff list back to CSV
            dataLoader.saveStaffList(staffList); // Implement this method
            System.out.println("Staff member updated successfully.");
        } else {
            System.out.println("Invalid selection.");
        }
    }

    private void removeStaffMember(List<User> staffList, DataLoader dataLoader) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Select staff member to remove by row number:");
        for (int i = 0; i < staffList.size(); i++) {
            System.out.println((i + 1) + ". " + staffList.get(i));
        }
        int rowToRemove = scanner.nextInt() - 1; // Convert to zero-based index
    
        if (rowToRemove >= 0 && rowToRemove < staffList.size()) {
            staffList.remove(rowToRemove);
            // Save updated staff list back to CSV
            dataLoader.saveStaffList(staffList); // Implement this method
            System.out.println("Staff member removed successfully.");
        } else {
            System.out.println("Invalid selection.");
        }
    }

    

    public void viewStaff() {
        DataLoader dataLoader = new DataLoader(loginSystem); // Pass the LoginSystem instance
        List<User> staffList = dataLoader.getAllStaff(); // Fetch staff list
    
        System.out.println("\n--- View Staff ---");
        if (staffList.isEmpty()) {
            System.out.println("No staff members found.");
        } else {
            // Update the header to include Age
            System.out.printf("%-15s %-20s %-10s %-10s %-5s%n", "Staff ID", "Name", "Role", "Gender", "Age");
            System.out.println("-".repeat(70)); // Print a separator line
            for (User  staff : staffList) {
                // Print staff details, including age
                System.out.printf("%-15s %-20s %-10s %-10s %-5d%n", 
                    staff.getHospitalId(), // Staff ID
                    staff.getName(),       // Staff member's name
                    staff.getRole(),       // Staff member's role
                    staff.getGender(),     // Staff member's gender
                    staff.getAge());       // Staff member's age
            }
        }
    }

    public void manageInventory() {
        System.out.println("Manage Inventory - Not implemented");
    }

    public void viewInventory() {
        System.out.println("View Inventory - Not implemented");
    }

    public void manageReplenishmentRequests() {
        System.out.println("Manage Replenishment Requests - Not implemented");
    }

    public void viewReplenishmentRequests() {
        try {
            Path path = Paths.get("data/ReplenishmentRequest.csv");
            List<String> lines = Files.readAllLines(path);
            
            // Check if there are any requests
            if (lines.size() <= 1) {
                System.out.println("No replenishment requests found.");
                return;
            }
    
            // Print header
            System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s%n", 
                "Request ID", "Medicine", "Request Amount", "Pharmacist ID", "Pharmacist Name", "Status");
            System.out.println("-".repeat(100)); // Separator line
    
            // Print each request's details
            for (String line : lines.subList(1, lines.size())) { // Skip header
                String[] data = line.split(",");
                if (data.length == 6) { // Ensure we have the expected number of columns
                    System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s%n", 
                        data[0].trim(), // Request ID
                        data[1].trim(), // Medicine
                        data[2].trim(), // Request Amount
                        data[3].trim(), // Pharmacist ID
                        data[4].trim(), // Pharmacist Name
                        data[5].trim()); // Status
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading replenishment requests: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred.");
        }
    }

    public void generateReports() {
        System.out.println("Generate Reports - Not implemented");
    }

    @Override
    public String toString() {
        return "Administrator: " + getName() + " (ID: " + getHospitalId() + ")";
    }

     private User createUserByRole(String staffId, String name, String role, String gender, int age) {
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

    public void viewAllAppointments() {
        try {
            Path path = Paths.get("data/Appointments.csv");
            List<String> lines = Files.readAllLines(path);
            
            // Check if there are any appointments
            if (lines.size() <= 1) {
                System.out.println("No appointments found.");
                return;
            }
    
            // Print header
            System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s %-15s%n", 
                "Appt ID", "Doctor Name", "Date", "Time", "Status", "Patient ID", "Patient Name");
            System.out.println("-".repeat(105)); // Adjusted separator line length
    
            // Iterate through the lines and print appointment details
            for (String line : lines.subList(1, lines.size())) { // Skip header
                String[] data = line.split(",");
                // Determine Patient ID and Patient Name based on availability
                String patientId = (data.length > 5 && !data[5].isEmpty()) ? data[5] : "N/A"; // Patient ID
                String patientName = (data.length > 6 && !data[6].isEmpty()) ? data[6] : "N/A"; // Patient Name
                
                System.out.printf("%-10s %-20s %-15s %-15s %-15s %-15s %-15s%n", 
                    data[0], // Appointment ID
                    data[1], // Doctor Name
                    data[2], // Date
                    data[3], // Time
                    data[4], // Status
                    patientId, // Patient ID
                    patientName); // Patient Name
            }
        } catch (IOException e) {
            System.out.println("Error reading appointments: " + e.getMessage());
        }
    }

    public void viewMedicineList() {
        try {
            Path path = Paths.get("data/Medicine_List.csv");
            List<String> lines = Files.readAllLines(path);
            
            // Check if there are any medicines
            if (lines.size() <= 1) {
                System.out.println("No medicines found.");
                return;
            }
    
            // Print header
            System.out.printf("%-20s %-15s %-20s%n", "Medicine Name", "Initial Stock", "Low Stock Level Alert");
            System.out.println("-".repeat(60)); // Separator line
    
            // Print each medicine's details
            for (String line : lines.subList(1, lines.size())) { // Skip header
                String[] data = line.split(",");
                if (data.length == 3) { // Ensure we have the expected number of columns
                    System.out.printf("%-20s %-15s %-20s%n", 
                        data[0].trim(), // Medicine Name
                        data[1].trim(), // Initial Stock
                        data[2].trim()); // Low Stock Level Alert
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading medicine list: " + e.getMessage());
        }
    }

    

    public void manageStockLevels() {
        DataLoader dataLoader = new DataLoader(null); // Pass a valid LoginSystem instance if necessary
        List<Medicine> medicines = dataLoader.getAllMedicines(); // Load current medicines
   
        // Check if there are any medicines
        if (medicines.isEmpty()) {
            System.out.println("No medicines available.");
            return;
        }
   
        // Display the list of medicines
        System.out.println("\n=== Select Medicine to Update Stock ===");
        for (int i = 0; i < medicines.size(); i++) {
            Medicine medicine = medicines.get(i);
            System.out.printf("%d. %s (Current Stock: %d)%n", 
                i + 1, 
                medicine.getMedicineName(), 
                medicine.getStock());
        }
   
        // Prompt for selection
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select the number of the medicine to update: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
   
        if (choice < 1 || choice > medicines.size()) {
            System.out.println("Invalid selection.");
            return;
        }
   
        Medicine selectedMedicine = medicines.get(choice - 1);
   
        // Prompt for the amount to add
        System.out.print("Enter the amount to add to stock: ");
        int amountToAdd = scanner.nextInt();
        scanner.nextLine(); // Consume newline
   
        if (amountToAdd <= 0) {
            System.out.println("Invalid amount. Please enter a positive number.");
            return;
        }
   
        // Update the stock
        selectedMedicine.addStock(amountToAdd);
        dataLoader.saveMedicines(medicines); // Save updated medicines list
   
        System.out.println("Stock updated successfully! New stock for " + selectedMedicine.getMedicineName() + ": " + selectedMedicine.getStock());
    }

    public void approveReplenishmentRequests() {
        try {
            Path path = Paths.get("data/ReplenishmentRequest.csv");
            List<String> lines = Files.readAllLines(path);
            
            // Check if there are any requests
            if (lines.size() <= 1) {
                System.out.println("No replenishment requests found.");
                return;
            }
    
            // Display requests
            System.out.println("\n=== Select Replenishment Request to Approve ===");
            boolean hasPendingRequests = false; // Track if there are any pending requests
    
            for (int i = 1; i < lines.size(); i++) { // Skip header
                String[] data = lines.get(i).split(",");
                if (data.length == 6) { // Ensure we have the expected number of columns
                    String status = data[5].trim();
                    if (!status.equalsIgnoreCase("Approved")) { // Only show requests that are not approved
                        System.out.printf("%d. Request ID: %s, Medicine: %s, Request Amount: %s, Status: %s%n", 
                            i, // List number
                            data[0].trim(), // Request ID
                            data[1].trim(), // Medicine
                            data[2].trim(), // Request Amount
                            status); // Status
                        hasPendingRequests = true; // Mark that we have at least one pending request
                    }
                }
            }
    
            // If no pending requests are found, inform the user
            if (!hasPendingRequests) {
                System.out.println("No requests to approve.");
                return;
            }
    
            // Prompt for selection
            Scanner scanner = new Scanner(System.in);
            System.out.print("Select the number of the request to approve: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
    
            if (choice < 1 || choice >= lines.size()) {
                System.out.println("Invalid selection.");
                return;
            }
    
            // Get the selected request
            String[] selectedRequest = lines.get(choice).split(",");
            
            // Check if the request is already approved
            if (selectedRequest[5].trim().equalsIgnoreCase("Approved")) {
                System.out.println("This request has already been approved.");
                return;
            }
    
            // Update the status to "Approved"
            selectedRequest[5] = "Approved";
    
            // Check if the medicine exists in the medicine list
            DataLoader dataLoader = new DataLoader(null); // Pass a valid LoginSystem instance if necessary
            List<Medicine> medicines = dataLoader.getAllMedicines();
            String medicineName = selectedRequest[1].trim();
            int requestAmount = Integer.parseInt(selectedRequest[2].trim());
    
            // Check if the medicine exists in the medicine list
            boolean medicineExists = false;
            for (Medicine medicine : medicines) {
                if (medicine.getMedicineName().equalsIgnoreCase(medicineName)) {
                    medicineExists = true;
                    // Update the stock
                    medicine.addStock(requestAmount); // Increase the stock by the requested amount
                    System.out.println("Stock updated for " + medicineName + ". New stock: " + medicine.getStock());
                    break;
                }
            }
    
            // If the medicine does not exist, add it to the list
            if (!medicineExists) {
                System.out.print("Enter low stock level alert for " + medicineName + ": ");
                int lowStockAlert = scanner.nextInt();
                scanner.nextLine(); // Consume newline
    
                // Create a new Medicine object and add it to the list
                Medicine newMedicine = new Medicine(medicineName, requestAmount, lowStockAlert);
                medicines.add(newMedicine);
                System.out.println("New medicine " + medicineName + " added with stock: " + requestAmount);
            }
    
            // Update the CSV file with the new status
            try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                for (String line : lines) {
                    if (line.startsWith(selectedRequest[0])) {
                        writer.write(String.join(",", selectedRequest) + "\n");
                    } else {
                        writer.write(line + "\n");
                    }
                }
            }
    
            // Save updated medicines list to CSV
            dataLoader.saveMedicines(medicines);
    
            System.out.println("Replenishment request approved successfully.");
        } catch (IOException e) {
            System.out.println("Error processing replenishment requests: " + e.getMessage());
        }
    }
  
}