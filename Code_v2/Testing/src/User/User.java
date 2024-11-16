package User;

public abstract class User {
    private String hospitalId; 
    private String password;
    private String name;
    private String role;
    private String gender;
    private int age;
    private boolean isFirstLogin = true;

    public User(String hospitalId, String password, String name, String gender, String role, int age) {
        this.hospitalId = hospitalId;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.role = role; 
        this.age = age;
    }

    // Getters
    public String getHospitalId() {
        return hospitalId;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public boolean isFirstLogin() {
        return isFirstLogin;
    }

    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
        this.isFirstLogin = false;
    }

    public abstract void displayMenu();

    public abstract void handleMenuChoice(int choice);

    public void setFirstLogin(boolean firstLogin) {
        this.isFirstLogin = firstLogin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("Staff ID: %s, Name: %s, Role: %s, Gender: %s, Age: %d",
                hospitalId, name, role, gender, age);
    }

}