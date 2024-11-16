package Administrator;

import Doctor.Doctor;
import HMS.DataLoader;
import Pharmacist.Pharmacist;
import User.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class StaffManager implements IStaffManagement {

    private LoginSystem loginSystem;

    private DataLoader dataLoader;

    public StaffManager(DataLoader dataLoader) {
        this.loginSystem = loginSystem;
        this.dataLoader = dataLoader;
    }

    public void viewStaff() {
        DataLoader dataLoader = new DataLoader(loginSystem); 
        List<User> staffList = dataLoader.getAllStaff(); 

        System.out.println("\n--- View Staff ---");
        if (staffList.isEmpty()) {
            System.out.println("No staff members found.");
        } else {

            System.out.printf("%-15s %-20s %-10s %-10s %-5s%n", "Staff ID", "Name", "Role", "Gender", "Age");
            System.out.println("-".repeat(70));
            for (User staff : staffList) {

                System.out.printf("%-15s %-20s %-10s %-10s %-5d%n",
                        staff.getHospitalId(), 
                        staff.getName(), 
                        staff.getRole(),
                        staff.getGender(), 
                        staff.getAge()); 
            }
        }
    }

    public void addStaffMember(DataLoader dataLoader) {
        Scanner scanner = new Scanner(System.in);
    
        System.out.print("Enter Staff ID: ");
        String staffId = scanner.nextLine().trim();
    
        List<User> staffList = dataLoader.getAllStaff();
    
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
    
        if (!role.equalsIgnoreCase("Doctor") && !role.equalsIgnoreCase("Pharmacist")
                && !role.equalsIgnoreCase("Administrator")) {
            System.out.println("Invalid role. Staff member not added.");
            return;
        }
    
        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine().trim();
    
        System.out.print("Enter Age: ");
        int age = 0;
        try {
            age = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid age input. Staff member not added.");
            scanner.nextLine();
            return;
        }
    
        User newStaffMember = createUserByRole(staffId, name, gender, age, role);
        if (newStaffMember != null) {
            staffList.add(newStaffMember);
            dataLoader.saveStaffList(staffList);
            System.out.println("Staff member added successfully.");
            dataLoader.saveUserCredentials(staffId);
        } else {
            System.out.println("Error adding staff member.");
        }
    }

    public void addStaff(User newUser) {
        List<User> staffList = dataLoader.getAllStaff();
        staffList.add(newUser); 
        saveStaffList(staffList); 
    }

    public User createUserByRole(String hospitalId, String name, String gender, int age, String role) {
        switch (role.toLowerCase()) {
            case "doctor":
                return new Doctor(hospitalId, "password", name, gender, age);
            case "pharmacist":
                return new Pharmacist(hospitalId, "password", name, gender, age);
            case "administrator":
                return new Administrator(hospitalId, "password", name, gender, age);
            default:
                return null; 
        }
    }

    public void updateStaffMember(List<User> staffList, DataLoader dataLoader) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select staff member to update by row number:");
        for (int i = 0; i < staffList.size(); i++) {
            System.out.println((i + 1) + ". " + staffList.get(i));
        }
        int rowToUpdate = scanner.nextInt() - 1;
        scanner.nextLine();

        if (rowToUpdate >= 0 && rowToUpdate < staffList.size()) {
            User staffMember = staffList.get(rowToUpdate);

            System.out.print("Enter new name (leave blank to keep current: " + staffMember.getName() + "): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                staffMember.setName(newName);
            }

            System.out.print("Enter new role (leave blank to keep current: " + staffMember.getRole() + "): ");
            String newRole = scanner.nextLine();
            if (!newRole.isEmpty()) {
                if (newRole.equalsIgnoreCase("Doctor") || newRole.equalsIgnoreCase("Pharmacist")
                        || newRole.equalsIgnoreCase("Administrator")) {
                    staffMember.setRole(newRole);
                } else {
                    System.out.println("Invalid role. Keeping the current role: " + staffMember.getRole());
                }
            }

            System.out.print("Enter new gender (leave blank to keep current: " + staffMember.getGender() + "): ");
            String newGender = scanner.nextLine();
            if (!newGender.isEmpty()) {
                staffMember.setGender(newGender);
            }

            System.out.print("Enter new age (leave blank to keep current: " + staffMember.getAge() + "): ");
            String newAgeInput = scanner.nextLine();
            if (!newAgeInput.isEmpty()) {
                try {
                    int newAge = Integer.parseInt(newAgeInput);
                    staffMember.setAge(newAge);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid age input. Keeping the current age: " + staffMember.getAge());
                }
            }

            dataLoader.saveStaffList(staffList);
            System.out.println("Staff member updated successfully.");
        } else {
            System.out.println("Invalid selection.");
        }
    }

    public void removeStaffMember(List<User> staffList, DataLoader dataLoader) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select staff member to remove by row number:");
        for (int i = 0; i < staffList.size(); i++) {
            System.out.println((i + 1) + ". " + staffList.get(i));
        }
        int rowToRemove = scanner.nextInt() - 1;

        if (rowToRemove >= 0 && rowToRemove < staffList.size()) {
            User staffToRemove = staffList.get(rowToRemove);
            staffList.remove(rowToRemove);
            dataLoader.saveStaffList(staffList);
            System.out.println("Staff member removed successfully.");
            dataLoader.removeUserCredentials(staffToRemove.getHospitalId());
        } else {
            System.out.println("Invalid selection.");
        }
    }

    public void updateStaff(User updatedUser) {
        List<User> staffList = dataLoader.getAllStaff(); 
        saveStaffList(staffList); 
    }

    public void removeStaff(User userToRemove) {
        List<User> staffList = dataLoader.getAllStaff(); 
        staffList.remove(userToRemove); 
        saveStaffList(staffList); 
    }

    public void saveStaffList(List<User> staffList) {
        String staffFilePath = dataLoader.getStaffFilePath(); 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(staffFilePath, false))) {
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
}
