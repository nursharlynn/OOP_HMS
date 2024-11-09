package Administrator;

import User.*;

public interface IManageStaff {

	/**
	 * 
	 * @param role
	 * @param gender
	 * @param age
	 */
	void viewStaff(String role, String gender, int age);

	/**
	 * 
	 * @param user
	 */
	void addStaff(User user);

	/**
	 * 
	 * @param oldUser
	 * @param newUser
	 */
	void updateStaff(User oldUser, User newUser);

	/**
	 * 
	 * @param user
	 */
	void removeStaff(User user);

}