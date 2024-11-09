package Administrator;

import User.*;
import java.util.*;
import Appointment.*;

public class Administrator extends User {

	private IInventory invHandler;
	private Collection<IRequestManager> reqHandler;
	private ArrayList<Appointment> appointments;
	private IManageStaff staffHandler;

	/**
	 * 
	 * @param hospitalID
	 * @param password
	 * @param name
	 * @param gender
	 * @param age
	 */
	
	public Administrator(String hospitalID, String password, String name, String gender, int age) {
		// TODO - implement Administrator.Administrator
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param invHandler
	 */
	public Administrator(IInventory invHandler) {
		// TODO - implement Administrator.Administrator
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param reqHandler
	 */
	public Administrator(IRequestManager reqHandler) {
		// TODO - implement Administrator.Administrator
		throw new UnsupportedOperationException();
	}

	public void viewScheduledAppointments() {
		// TODO - implement Administrator.viewScheduledAppointments
		throw new UnsupportedOperationException();
	}

}