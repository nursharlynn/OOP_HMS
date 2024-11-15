package Administrator;

import Appointment.*;
import HMS.DataLoader;
import Pharmacist.IInventoryManager;
import Pharmacist.InventoryManager;
import User.*;
import java.util.*;

public class Administrator extends User {

    private LoginSystem loginSystem;
    private int age;
    private StaffManager staffManager;
    private DataLoader dataLoader;
    private ReplenishmentRequestManager requestManager;
    private ReplenishmentRequestApprovalManager approvalManager;
    private AppointmentManager appointmentManager;
    private IInventoryManager inventoryManager;
    private StockManager stockManager;

    public Administrator(String hospitalId, String password, String name, String gender, int age) {
        super(hospitalId, password, name, gender, "Administrator", age);
        this.age = age;
        this.dataLoader = new DataLoader(loginSystem);
        this.staffManager = new StaffManager(dataLoader);
        this.requestManager = new ReplenishmentRequestManager();
        this.approvalManager = new ReplenishmentRequestApprovalManager(dataLoader);
        this.appointmentManager = new AppointmentManager();
        this.inventoryManager = new InventoryManager(new ArrayList<>());
        this.stockManager = new StockManager(dataLoader);
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
                staffManager.viewStaff(); // Call the method to view staff
                break;
            case 3:
                stockManager.manageStockLevels(); // Handle stock management
                break;
            case 4:
                inventoryManager.viewInventory(); // Call viewInventory method
                break;
            case 5:
                approvalManager.approveReplenishmentRequests();
                break;
            case 6:
                requestManager.viewReplenishmentRequests();
                break;
            case 7:
                appointmentManager.viewAllAppointments();
                break;
            case 8:
                System.out.println("Generate Reports - Not implemented yet");
                break;
            case 9:
                System.out.println("Logging out...");
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public void manageStaff() {
        DataLoader dataLoader = new DataLoader(loginSystem);
        List<User> staffList = dataLoader.getAllStaff();
        StaffManager staffManager = new StaffManager(dataLoader);

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Manage Staff ---");
        System.out.println("1. Add Staff Member");
        System.out.println("2. Update Staff Member");
        System.out.println("3. Remove Staff Member");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                try {
                    System.out.print("Enter staff role (Doctor/Pharmacist/Administrator): ");
                    String role = scanner.nextLine();

                    System.out.print("Enter Hospital ID: ");
                    String hospitalId = scanner.nextLine();

                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Gender: ");
                    String gender = scanner.nextLine();

                    System.out.print("Enter Age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();

                    User newUser = staffManager.createUserByRole(hospitalId, name, gender, age, role);
                    if (newUser != null) {
                        staffManager.addStaff(newUser);
                        System.out.println("Staff member added successfully.");
                    } else {
                        System.out.println("Invalid role entered. Staff member not added.");
                    }
                } catch (Exception e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }
                break;
            case 2:
                staffManager.updateStaffMember(staffList, dataLoader);
                break;
            case 3:
                staffManager.removeStaffMember(staffList, dataLoader);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    public void generateReports() {
        System.out.println("Generate Reports - Not implemented");
    }

}