package Administrator;

import Appointment.*;
import User.*;
import java.util.*;

public class Administrator extends User {

    private IInventory invHandler;
    private Collection<IRequestManager> reqHandler;
    private ArrayList<Appointment> appointments;
    private IManageStaff staffHandler;

    public Administrator(String hospitalId, String password, String name, String gender, int age) {
        super(hospitalId, password, name, gender, age);
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
            System.out.println("Manage Staff - Not implemented yet");
            break;
        case 2:
            System.out.println("View Staff - Not implemented yet");
            break;
        case 3:
            System.out.println("Manage Inventory - Not implemented yet");
            break;
        case 4:
            System.out.println("View Inventory - Not implemented yet");
            break;
        case 5:
            System.out.println("Manage Replenishment Requests - Not implemented yet");
            break;
        case 6:
            System.out.println("View Replenishment Requests - Not implemented yet");
            break;
        case 7:
            System.out.println("View Scheduled Appointments - Not implemented yet");
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
        System.out.println("Manage Staff - Not implemented");
    }

    public void viewStaff() {
        System.out.println("View Staff - Not implemented");
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
        System.out.println("View Replenishment Requests - Not implemented");
    }

    public void viewScheduledAppointments() {
        System.out.println("View Scheduled Appointments - Not implemented");
    }

    public void generateReports() {
        System.out.println("Generate Reports - Not implemented");
    }

    @Override
    public String toString() {
        return "Administrator: " + getName() + " (ID: " + getHospitalId() + ")";
    }
}