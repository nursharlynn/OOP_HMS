package Appointment;

import Doctor.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Schedule {

    private List<TimeSlot> schedule;
    private LocalDateTime date;

    public Schedule() {
        // Initialize an empty list of time slots
        this.schedule = new ArrayList<>();
        // Set the date to current date and time
        this.date = LocalDateTime.now();
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public List<TimeSlot> generateTimeslots(LocalDateTime startOfDay, int hoursOpen) {
        // Basic implementation to generate time slots
        List<TimeSlot> timeSlots = new ArrayList<>();
        LocalDateTime currentTime = startOfDay;
        
        for (int i = 0; i < hoursOpen; i++) {
            // Assume each time slot is 1 hour long
            LocalDateTime endTime = currentTime.plusHours(1);
            TimeSlot slot = new TimeSlot(currentTime, endTime);
            timeSlots.add(slot);
            currentTime = endTime;
        }
        
        return timeSlots;
    }

    public List<TimeSlot> generateDefaultSchedule() {
        // Generate a default schedule for the current day
        LocalDateTime startOfDay = LocalDateTime.now().withHour(9).withMinute(0).withSecond(0);
        return generateTimeslots(startOfDay, 8); // 8-hour workday
    }

    public List<TimeSlot> generateWeeklySchedule() {
        // Placeholder for generating a weekly schedule
        List<TimeSlot> weeklySchedule = new ArrayList<>();
        LocalDateTime startOfWeek = LocalDateTime.now().withHour(9).withMinute(0).withSecond(0);
        
        for (int day = 0; day < 5; day++) { // Assuming 5-day work week
            weeklySchedule.addAll(generateTimeslots(startOfWeek.plusDays(day), 8));
        }
        
        return weeklySchedule;
    }

    public List<TimeSlot> getDailySchedule() {
        // Return the current day's schedule
        return generateDefaultSchedule();
    }

    public List<TimeSlot> getWeeklySchedule() {
        // Return the weekly schedule
        return generateWeeklySchedule();
    }

    public boolean updateTimeslot(LocalDateTime start, Doctor doctor) {
        // Find the time slot and mark it as unavailable or assign to a doctor
        for (TimeSlot slot : schedule) {
            if (slot.getStartTime().equals(start)) {
                // Update slot status or assign doctor
                return true;
            }
        }
        return false;
    }
}