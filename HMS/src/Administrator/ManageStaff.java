package Administrator;

import User.*;
import Doctor.*;
import Pharmacist.*;

public class ManageStaff extends User implements IManageStaff {

	private List<Doctor> doctors;
	private List<Pharmacist> Pharmacist;

	/**
	 * 
	 * @param role
	 * @param gender
	 * @param age
	 */
	public void viewStaff(String role, String gender, int age) {
		// TODO - implement ManageStaff.viewStaff
		throw new UnsupportedOperationException();
	}

	public ManageStaff() {
		// TODO - implement ManageStaff.ManageStaff
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param user
	 */
	public void addStaff(User user) {
		// TODO - implement ManageStaff.addStaff
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param oldUser
	 * @param newUser
	 */
	public void updateStaff(User oldUser, User newUser) {
		// TODO - implement ManageStaff.updateStaff
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param user
	 */
	public void removeStaff(User user) {
		// TODO - implement ManageStaff.removeStaff
		throw new UnsupportedOperationException();
	}

}