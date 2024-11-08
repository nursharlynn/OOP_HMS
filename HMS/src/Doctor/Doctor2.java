package project;
import java.util.List;
import java.util.ArrayList;

public class Doctor {
    private String id;
    private String name;
    private List<String> availableSlots = new ArrayList<>(); // Ensure it's initialized

    public void setAvailability(String slot) {
        availableSlots.add(slot); 
    }

    public List<String> getAvailableSlots() {
        return availableSlots;
    }

    @Override
    public String toString() {
        return "Doctor ID: " + id + ", Name: " + name;
    }
    
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
