package Appointment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Administrator.Medicine;

public class AppointmentOutcome {

	private String serviceType;
	private List<Medicine> prescribedMedications;
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
	public AppointmentOutcome(int AppointmentsId, LocalDateTime date, String serviceType, ArrayList<Medicine> prescribedMedications, String consultationNotes, String prescriptionStatus) {
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

	public List<Medicine> getPrescribedMedications() {
		return this.prescribedMedications;
	}

	/**
	 * 
	 * @param prescribedMedications
	 */
	public void setPrescribedMedications(List<Medicine> prescribedMedications) {
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