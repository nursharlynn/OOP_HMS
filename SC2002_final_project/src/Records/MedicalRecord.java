package Records;

import Administrator.*;
import Patient.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {

    private ArrayList<String> diagnoses;
    private ArrayList<String> treatments;
    private List<Medicine> prescription; // Updated type to List<Medicine> based on prescription data
    //private ViewMedicalRecord viewRecord; // Assuming this is for displaying medical records
    private LocalDateTime date;

    // Constructor
    public MedicalRecord() {
        this.diagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
        this.prescription = new ArrayList<>();
        this.date = LocalDateTime.now(); // Initialize with the current date and time
    }

    // Getter for prescription
    public List<Medicine> getPrescription() {
        return prescription;
    }

    // Setter for prescription
    public void setPrescription(List<Medicine> prescription) {
        this.prescription = prescription;
    }

    // Getter for treatments
    public ArrayList<String> getTreatments() {
        return treatments;
    }

    // Setter for treatments
    public void setTreatments(ArrayList<String> treatments) {
        this.treatments = treatments;
    }

    // Getter for diagnoses
    public ArrayList<String> getDiagnoses() {
        return diagnoses;
    }

    // Setter for diagnoses
    public void setDiagnoses(ArrayList<String> diagnoses) {
        this.diagnoses = diagnoses;
    }

    // View medical record for a given patient
    public void viewMedicalRecord(Patient patient) {
        // For now, we print the record; this can be adapted to a GUI or CLI display
        System.out.println("Medical Record for Patient: " + patient.getName());
        System.out.println("Diagnoses: " + String.join(", ", diagnoses));
        System.out.println("Treatments: " + String.join(", ", treatments));
        System.out.println("Prescriptions: ");
        for (Medicine med : prescription) {
            System.out.println(" - " + getMedicineName());
        }
        System.out.println("Record Date: " + date);
    }

    private String getMedicineName() {
		return getMedicineName();
	}

	// Getter for date
    public LocalDateTime getDate() {
        return date;
    }

    // Setter for date
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

	public void updateRecord(String patientId, String newDiagnosis, String prescriptions, String newTreatment) {
		// TODO Auto-generated method stub
		
	}
}
