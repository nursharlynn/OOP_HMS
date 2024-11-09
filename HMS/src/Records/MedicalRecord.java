package Records;

import Administrator.Medicine;
import Patient.Patient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {
    private ArrayList<String> diagnoses;
    private List<String> treatments;
    private List<Medicine> prescription;
    private LocalDateTime date;

    // Constructor
    public MedicalRecord() {
        this.diagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
        this.prescription = new ArrayList<>();
    }

    // Method to view medical record
    public void viewMedicalRecord(Patient patient) {
        System.out.println("--- Patient Medical Record ---");
        System.out.println("Patient ID: " + patient.getPatientID());
        System.out.println("Name: " + patient.getName());
        System.out.println("Date of Birth: " + patient.getDateofBirth());
        System.out.println("Gender: " + patient.getGender());
        System.out.println("Contact Information: " + patient.getContact());
        System.out.println("Blood Type: " + patient.getBloodType());
        
        // Display current diagnosis and treatment
        System.out.println("\nCurrent Diagnosis: " + 
            (patient.getDiagnosis().isEmpty() ? "No current diagnosis" : patient.getDiagnosis()));
        System.out.println("Current Treatment: " + 
            (patient.getTreatment().isEmpty() ? "No current treatment" : patient.getTreatment()));
    }

    // Getters and setters
    public void addDiagnosis(String diagnosis) {
        this.diagnoses.add(diagnosis);
    }

    public void addTreatment(String treatment) {
        this.treatments.add(treatment);
    }

    public void setPrescription(List<Medicine> prescription) {
        this.prescription = prescription;
    }
}