package User;

public class User {

	private String hospitalId;
	private String password;
	private String name;
	private String gender;
	private int age;
	private int pharmacistId;
	private int doctorId;

	/**
	 * 
	 * @param hospitalId
	 * @param password
	 * @param name
	 * @param gender
	 * @param age
	 */
	public User(String hospitalId, String password, String name, String gender, int age) {
		// TODO - implement User.User
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param enteredId
	 * @param enteredPass
	 */
	public void login(String enteredId, String enteredPass) {
		// TODO - implement User.login
		throw new UnsupportedOperationException();
	}

	public void logout() {
		// TODO - implement User.logout
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param newPassword
	 */
	public void changePassword(String newPassword) {
		// TODO - implement User.changePassword
		throw new UnsupportedOperationException();
	}

	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return this.gender;
	}

	/**
	 * 
	 * @param gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return this.age;
	}

	/**
	 * 
	 * @param age
	 */
	public void setAge(int age) {
		this.age = age;
	}

	public int getPharmacistId() {
		return this.pharmacistId;
	}

	public int getDoctorId() {
		return this.doctorId;
	}

}