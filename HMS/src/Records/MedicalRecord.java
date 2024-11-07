package Records;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Administrator.*;
import Patient.*;

public class MedicalRecord {

	private ArrayList<String> diagnoses;
	private ArrayList<String> treatments;
	private List<String> prescription;
	private LocalDateTime date;

	public MedicalRecord() {
		// TODO - implement MedicalRecord.MedicalRecord
		throw new UnsupportedOperationException();
	}

	public ArrayList<Medicine> getPrescription() {
		// TODO - implement MedicalRecord.getPrescription
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param prescription
	 */
	public void setPrescription(List<Medicine> prescription) {
		// TODO - implement MedicalRecord.setPrescription
		throw new UnsupportedOperationException();
	}

	public ArrayList<String> getTreatments() {
		return this.treatments;
	}

	/**
	 * 
	 * @param treatments
	 */
	public void setTreatments(ArrayList<String> treatments) {
		this.treatments = treatments;
	}

	public ArrayList<String> getDiagnoses() {
		return this.diagnoses;
	}

	/**
	 * 
	 * @param diagnoses
	 */
	public void setDiagnoses(ArrayList<String> diagnoses) {
		this.diagnoses = diagnoses;
	}

	/**
	 * 
	 * @param patient
	 */
	public void viewMedicalRecord(Patient patient) {
		// TODO - implement MedicalRecord.viewMedicalRecord
		throw new UnsupportedOperationException();
	}

	public LocalDateTime getDate() {
		return this.date;
	}

	/**
	 * 
	 * @param date
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

}