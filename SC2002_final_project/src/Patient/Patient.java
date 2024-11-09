package Patient;

import User.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import Appointment.*;
import Records.*;

public class Patient extends User {

  private LocalDateTime dateofBirth;
  private String gender;
  private String contact;
  private String emailAddress;
  private String bloodType;
  private IAppointmentsHandler apptHandler;
  private ArrayList<ViewScheduledAppointments> scheduledAppointments;
  private MedicalRecord medicalRecord;
  private Schedule schedule;
  private LocalDateTime dateOfBirth;

  /**
   * 
   * @param hospitalId
   * @param password
   * @param dateOfBirth
   * @param gender
   * @param emailAddress
   * @param bloodType
   */
  public Patient(String hospitalId, String password, LocalDateTime dateOfBirth, String gender, String emailAddress, String bloodType) {
    // TODO - implement Patient.Patient
    super(hospitalId, password, "", gender, 0); // Passing empty name and age as 0 since Patient doesn’t have them directly
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contact = "";
        this.emailAddress = emailAddress;
        this.bloodType = bloodType;
        this.scheduledAppointments = new ArrayList<>();
        this.medicalRecord = new MedicalRecord();
    throw new UnsupportedOperationException();
  }

  /**
   * 
   * @param schedule
   */
  public Patient(Schedule schedule) {
    // TODO - implement Patient.Patient
    //throw new UnsupportedOperationException();
     super("", "", "", "", 0); // Placeholder values for superclass constructor
       this.schedule = schedule;
       this.scheduledAppointments = new ArrayList<>();
  }

  /**
   * 
   * @param apptHandler
   */
  public Patient(IAppointmentsHandler apptHandler) {
    // TODO - implement Patient.Patient
    //throw new UnsupportedOperationException();
     super("", "", "", "", 0); // Placeholder values for superclass constructor
       this.apptHandler = apptHandler;
       this.scheduledAppointments = new ArrayList<>();
  
  }

  public String getName() {
    // TODO - implement Patient.getName
    //throw new UnsupportedOperationException();
    return super.getName(); 
  }

  public Date getDateofBirth() {
    // TODO - implement Patient.getDateofBirth
    //throw new UnsupportedOperationException();
     return java.util.Date.from(dateOfBirth.atZone(java.time.ZoneId.systemDefault()).toInstant());
  }

  public String getGender() {
    return this.gender;
  }

  public String getContact() {
    return this.contact;
  }

  /**
   * 
   * @param contact
   */
  public void setContact(String contact) {
    this.contact = contact;
  }

  public String getEmailAddress() {
    return this.emailAddress;
  }

  /**
   * 
   * @param emailAddress
   */
  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getPatientID() {
    // TODO - implement Patient.getPatientID
    //throw new UnsupportedOperationException();
    return super.getHospitalId(); 
  }

  public String getBloodType() {
    return this.bloodType;
  }

  public void viewAvailableSlots() {
    // TODO - implement Patient.viewAvailableSlots
    //throw new UnsupportedOperationException();
    if (schedule != null) {
            // Assuming Schedule class has a method to filter and display available slots
            schedule.displayAvailableSlots(); // Or any method that fetches available slots based on the patient's needs.
        } else {
            System.out.println("Schedule is not available for this patient.");
        }
    
  }

}