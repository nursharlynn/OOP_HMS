package Pharmacist;

import java.util.List;
import java.util.Scanner;

public interface IInventoryManager {
    void viewInventory();

    void submitReplenishmentRequest(Scanner scanner, String pharmacistId, String pharmacistName);

    void processMedications(List<String> prescribedMedications, boolean isDispensing);
}