package User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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
            System.out.print("Enter new password (at least 8 characters): ");
            String newPassword = scanner.nextLine();

            if (newPassword.length() < 8) {
                System.out.println("Password must be at least 8 characters long. Please try again.");
                continue;
            }

            System.out.print("Confirm new password: ");
            String confirmPassword = scanner.nextLine();

            if (newPassword.equals(confirmPassword)) {
                if (loginSystem.changePassword(user.getHospitalId(), "password", newPassword)) {
                    System.out.println("Password changed successfully.");
                    updateUserCredentials(user.getHospitalId(), newPassword);
                    break;
                } else {
                    System.out.println("Failed to change password. Please try again.");
                }
            } else {
                System.out.println("Passwords do not match. Please try again.");
            }
        }
    }

    private void updateUserCredentials(String hospitalId, String newPassword) {
        String credentialsFilePath = "data/UserCredentials.csv";
        try {
            List<String> lines = Files.readAllLines(Paths.get(credentialsFilePath));
            for (int i = 1; i < lines.size(); i++) {
                String[] data = lines.get(i).split(",");
                if (data[0].trim().equals(hospitalId)) {
                    lines.set(i, String.format("%s,%s", hospitalId, newPassword));
                    break;
                }
            }
            Files.write(Paths.get(credentialsFilePath), lines);
        } catch (IOException e) {
            System.err.println("Error updating user credentials: " + e.getMessage());
        }
    }
}