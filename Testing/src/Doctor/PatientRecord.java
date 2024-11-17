package Doctor;

public class PatientRecord {
    private String id;
    private String name;
    private String dateOfBirth;
    private String gender;
    private String bloodType;
    private String contact;
    private String diagnosis;
    private String treatment;

    public PatientRecord(String id, String name, String dateOfBirth, String gender, String bloodType, String contact,
            String diagnosis, String treatment) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        this.contact = contact;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getContact() {
        return contact;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }
}