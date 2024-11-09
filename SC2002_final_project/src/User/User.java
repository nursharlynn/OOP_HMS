package User;

public class User {

	private String hospitalId;
	private String password;
	private String name;
	private String gender;
	private int age;
	private int pharmacistId;
	private int doctorId;
	private String role;

	/**
	 * 
	 * @param hospitalId
	 * @param password
	 * @param name
	 * @param gender
	 * @param age
	 */


	public User(String hospitalId, String password, String name, String gender, int age) {
		this.hospitalId = hospitalId;
		this.password = "password";
		this.name = name;
		this.gender = gender;
		this.age = age;
		
		// TODO - implement User.User
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param enteredId
	 * @param enteredPass
	 */
	
	public boolean login(String enteredId, String enteredPass) {
		
		if (this.hospitalId.equals(enteredId) && this.password.equals(enteredPass)) {
            System.out.println("Login successful. Welcome, " + name + "!");
            // Role-specific access would be checked elsewhere in the system
            return true;
        } else {
            System.out.println("Invalid credentials.");
            return false;
        }
	
	}

	public void logout() {
		System.out.println(name + " has logged out.");
	}

	/**
	 * 
	 * @param newPassword
	 */
	public void changePassword(String newPassword) {
		this.password = newPassword;
        System.out.println("Password updated successfully.");
	}
	
	//All Getters and Setters
	
	public String getRole() {
		return this.role;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getPharmacistId() {
		return this.pharmacistId;
	}

	public int getDoctorId() {
		return this.doctorId;
	}

	public String getHospitalId() {
		// TODO Auto-generated method stub
		return getHospitalId();
	}

}