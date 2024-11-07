package Appointment;

public class AppointmentOutcome {

	private String serviceType;
	private List<Medication> prescribedMedications;
	private String consultationNotes;
	private PrescriptionStatus status;

	/**
	 * 
	 * @param AppointmentsId
	 * @param date
	 * @param serviceType
	 * @param prescribedMedications
	 * @param consultationNotes
	 * @param prescriptionStatus
	 */
	public AppointmentOutcome(int AppointmentsId, LocalDateTime date, String serviceType, ArrayList<Medication> prescribedMedications, String consultationNotes, String prescriptionStatus) {
		// TODO - implement AppointmentOutcome.AppointmentOutcome
		throw new UnsupportedOperationException();
	}

	public String getServiceType() {
		return this.serviceType;
	}

	/**
	 * 
	 * @param serviceType
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public List<Medication> getPrescribedMedications() {
		return this.prescribedMedications;
	}

	/**
	 * 
	 * @param prescribedMedications
	 */
	public void setPrescribedMedications(List<Medication> prescribedMedications) {
		this.prescribedMedications = prescribedMedications;
	}

	public String getConsultationNotes() {
		return this.consultationNotes;
	}

	/**
	 * 
	 * @param consultationNotes
	 */
	public void setConsultationNotes(String consultationNotes) {
		this.consultationNotes = consultationNotes;
	}

	public PrescriptionStatus getStatus() {
		return this.status;
	}

	/**
	 * 
	 * @param status
	 */
	public void setStatus(PrescriptionStatus status) {
		this.status = status;
	}

}