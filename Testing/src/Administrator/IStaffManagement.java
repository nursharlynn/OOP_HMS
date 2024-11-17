package Administrator;

import HMS.DataLoader;
import User.User;
import java.util.List;

public interface IStaffManagement {
    // Method to view all staff members
    void viewStaff();

    // Method to add a new staff member
    void addStaffMember(DataLoader dataLoader);

    // Method to update an existing staff member
    void updateStaffMember(List<User> staffList, DataLoader dataLoader);

    // Method to remove a staff member by hospital ID
    void removeStaffMember(List<User> staffList, DataLoader dataLoader);

    // Method to save the staff list to a file
    void saveStaffList(List<User> staffList);
}