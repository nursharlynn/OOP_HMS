package User;

public abstract class User {
    private String hospitalId;  // Changed from userId to match system requirements
    private String password;
    private String name;
    private String role;
    private String gender;
    private int age;
    private boolean isFirstLogin;

    public User(String hospitalId, String password, String name, String gender, int age) {
        this.hospitalId = hospitalId;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.isFirstLogin = true;
    }

    // Getters
    public String getHospitalId() { return hospitalId; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public String getGender() { return gender; }
    public int getAge() { return age; }
    public boolean isFirstLogin() { return isFirstLogin; }

    // Setters
    public void setFirstLogin(boolean firstLogin) {
        this.isFirstLogin = firstLogin;
    }
    // Password methods
    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
        this.isFirstLogin = false;
    }
    

    // Abstract method that all user types must implement
    public abstract void displayMenu();

    public abstract void handleMenuChoice(int choice);

    @Override
    public String toString() {
        return "User: " + name + " (ID: " + hospitalId + ", Role: " + role + ")";
    }
}