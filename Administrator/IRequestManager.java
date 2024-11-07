package Administrator;

import Pharmacist.*;

public interface IRequestManager extends IViewRequest {

	/**
	 * 
	 * @param requests
	 */
	void viewRequests(ArrayList<ReplenishmentRequest> requests);

	/**
	 * 
	 * @param requestId
	 */
	void approveRequests(int requestId);

}