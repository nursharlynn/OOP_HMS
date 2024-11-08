package User;

import java.util.Scanner;

public class PasswordManager {
    private LoginSystem loginSystem;
    private Scanner scanner;

    public PasswordManager(LoginSystem loginSystem) {
        this.loginSystem = loginSystem;
        this.scanner = new Scanner(System.in);
    }

    public void handleFirstTimeLogin(User user) {
        if (user.isFirstLogin()) {
            System.out.println("This is your first login. You must change your password.");
            changePassword(user);
        }
    }

    public void changePassword(User user) {
        while (true) {
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            System.out.print("Confirm new password: ");
            String confirmPassword = scanner.nextLine();

            if (newPassword.equals(confirmPassword)) {
                if (loginSystem.changePassword(user.getHospitalId(), "password", newPassword)) {
                    System.out.println("Password changed successfully.");
                    break;
                } else {
                    System.out.println("Failed to change password. Please try again.");
                }
            } else {
                System.out.println("Passwords do not match. Please try again.");
            }
        }
    }
}