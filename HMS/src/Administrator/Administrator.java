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
     * @param hospitalId
     * @param password
     * @param name
     * @param gender
     * @param age
     * @param invHandler
     * @param reqHandler
     */
    public Administrator(String hospitalId, String password, String name, String gender, int age) {
        super(hospitalId, password, name, gender, age);
        throw new UnsupportedOperationException();
    }
    

    public void setInvHandler(IInventory invHandler) {
        this.invHandler = invHandler;
    }

    public void setReqHandler(Collection<IRequestManager> reqHandler) {
        this.reqHandler = reqHandler;
    }

    public void setStaffHandler(IManageStaff staffHandler) {
        this.staffHandler = staffHandler;
    }

	public void viewScheduledAppointments() {
		// TODO - implement Administrator.viewScheduledAppointments
		throw new UnsupportedOperationException();
	}

}