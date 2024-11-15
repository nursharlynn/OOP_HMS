package HMS;

import Administrator.Administrator;
import Doctor.Doctor;
import Patient.Patient;
import Pharmacist.Pharmacist;
import User.LoginSystem;
import User.PasswordManager;
import User.User;
import java.util.Scanner;

public class HMSApp {
    private LoginSystem loginSystem;
    private Scanner scanner;
    private User currentUser;
    private PasswordManager passwordManager;

    public HMSApp() {
        this.loginSystem = new LoginSystem();
        this.scanner = new Scanner(System.in);
        this.passwordManager = new PasswordManager(loginSystem);

        DataLoader dataLoader = new DataLoader(loginSystem);
        dataLoader.loadAllData();
    }

    public void start() {
        while (true) {
            System.out.println("\nWelcome to Hospital Management System");
            System.out.println("1. Login");
            System.out.println("2. Exit");

            try {
                System.out.print("Choose an option: ");
                int choice = getValidIntInput();

                if (choice == 1) {
                    handleLogin();
                } else if (choice == 2) {
                    System.out.println("Thank you for using HMS. Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private int getValidIntInput() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    private void handleLogin() {
        scanner.nextLine();

        System.out.print("Enter Hospital ID: ");
        String hospitalId = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        currentUser = loginSystem.login(hospitalId, password);

        if (currentUser != null) {
            System.out.println("Login successful!");

            if (currentUser.isFirstLogin()) {
                passwordManager.handleFirstTimeLogin(currentUser);
            }

            boolean continueSession = true;
            while (continueSession) {
                currentUser.displayMenu();

                try {
                    System.out.print("Enter your choice: ");
                    int menuChoice = getValidIntInput();

                    currentUser.handleMenuChoice(menuChoice);

                    continueSession = !isLogoutChoice(currentUser, menuChoice);
                } catch (Exception e) {
                    System.out.println("An error occurred. Please try again.");
                    scanner.nextLine();
                }
            }
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private boolean isLogoutChoice(User user, int choice) {
        if (user instanceof Patient)
            return choice == 9;
        if (user instanceof Doctor)
            return choice == 8;
        if (user instanceof Pharmacist)
            return choice == 7;
        if (user instanceof Administrator)
            return choice == 9;
        return false;
    }

    public static void main(String[] args) {
        HMSApp app = new HMSApp();
        app.start();
    }
}