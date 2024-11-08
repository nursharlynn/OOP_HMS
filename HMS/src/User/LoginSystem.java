package User;

import java.util.HashMap;
import java.util.Map;

public class LoginSystem {
    private Map<String, User> users;

    public LoginSystem() {
        users = new HashMap<>();
    }

    public void addUser(User user) {
        users.put(user.getHospitalId(), user);  // Changed from getUserId to getHospitalId
    }

    public User login(String userId, String password) {
        User user = users.get(userId);
        if (user != null && user.checkPassword(password)) {
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
}