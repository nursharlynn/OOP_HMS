package Patient;

import Appointment.*;
import Doctor.Doctor;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PatientAppointmentManager implements IAppointmentsHandler {

  private PastAppointmentOutcomeRecord pastOutcomes;
  private ArrayList<Appointment> Appointments;
  
  public PatientAppointmentManager() {
        this.Appointments = new ArrayList<>();
        this.pastOutcomes = new PastAppointmentOutcomeRecord();
  }

  /**
   * 
   * @param patient
   * @param doctorName
   * @param date
   * @param time
   */
  
  @Override
   public void scheduleAppointments(Patient patient, Doctor doctor, LocalDateTime date, LocalDateTime time) {
          if (doctor == null) {
              System.out.println("Doctor not found.");
              return;
          }

          Appointment newAppointment = new Appointment(date, time);
          newAppointment.setDoctor(doctor);
          newAppointment.setDate(date);
          newAppointment.generateAppointmentId();

          Appointments.add(newAppointment);
          System.out.println("Appointment scheduled for " + patient.getName() + " with Dr. " + doctor.getName() +
                  " on " + date.toLocalDate() + " at " + time.toLocalTime());
      }

  /**
   * 
   * @param AppointmentsID
   * @param scheduledAppointments
   */
   @Override
   public void rescheduleAppointments(int appointmentID, ArrayList<ViewScheduledAppointments> scheduledAppointments) {
       for (Appointment appointment : Appointments) {
           if (appointment.getId() == appointmentID) {
               // Find an available slot (this is placeholder logic; actual slot selection should involve checking availability)
               LocalDateTime newDate = appointment.getDate().plusDays(1); // Placeholder for new available date
               LocalDateTime newDateTime = newDate.withHour(appointment.getDate().getHour() + 1); // Placeholder for new time

               // Update appointment date and time
               appointment.setDate(newDateTime);
               
               // Update schedule and appointment status
               System.out.println("Appointment " + appointmentID + " rescheduled to " + newDateTime);
               return;
           }
       }
       System.out.println("Appointment ID " + appointmentID + " not found.");
   }



      /**
       * Cancels an existing appointment, updating the slot status to available.
       *
       * @param appointmentID The ID of the appointment to cancel.
       * @return true if the appointment was successfully canceled, false otherwise.
       */
      @Override
      public boolean cancelAppointments(int appointmentID) {
          for (Appointment appointment : Appointments) {
              if (appointment.getId() == appointmentID) {
                  Appointments.remove(appointment);
                  System.out.println("Appointment " + appointmentID + " canceled. Slot is now available.");
                  // Update the slot in the schedule to reflect that it is available
                  return true;
              }
          }
          System.out.println("Appointment ID " + appointmentID + " not found.");
          return false;
      }

      /**
       * Retrieves and displays past appointment outcomes for a patient by calling a method in PastAppointmentOutcomeRecord.
       *
       * @param patientId The ID of the patient whose past appointment outcomes are to be retrieved.
       */
      @Override
      public void getPastOutcomes(String patientId) {
          // Assuming `appointments` is a list of Appointment objects that you can pass to viewPastAppointmentOutcomeRecord
          ArrayList<CompletedAppointment> outcomes = pastOutcomes.viewPastAppointmentOutcomeRecord(Appointments, patientId); 
          System.out.println("Past appointment outcomes for patient ID " + patientId + ":");
          for (CompletedAppointment outcome : outcomes) {
              System.out.println("- " + outcome);  // Assuming CompletedAppointment has a meaningful toString method
          }
      }

	

      
  }
