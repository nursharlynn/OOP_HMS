package User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginSystem {
    private Map<String, User> users;
    private Map<String, String> userCredentials;

    public LoginSystem() {
        users = new HashMap<>();
        userCredentials = new HashMap<>();
        loadUserCredentials(); 
    }

    public void addUser(User user) {
        users.put(user.getHospitalId(), user); 
    }

    public User login(String userId, String password) {
        User user = users.get(userId);
        String storedPassword = userCredentials.get(userId); 

        if (user != null && storedPassword != null && storedPassword.equals(password)) {
            return user;
        }
        return null;
    }

    public boolean changePassword(String userId, String oldPassword, String newPassword) {
        User user = users.get(userId);
        if (user != null && user.checkPassword(oldPassword)) {
            user.changePassword(newPassword);
            return true;
        }
        return false;
    }

    public void loadUserCredentials() {
        String credentialsFilePath = "data/UserCredentials.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(credentialsFilePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    String hospitalId = data[0].trim();
                    String password = data[1].trim();
                    userCredentials.put(hospitalId, password);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user credentials: " + e.getMessage());
        }
    }
}