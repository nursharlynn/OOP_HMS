package Administrator;

import HMS.DataLoader;
import java.util.List;
import java.util.Scanner;

public class StockManager {
    private DataLoader dataLoader;
    private Scanner scanner;

    public StockManager(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
        this.scanner = new Scanner(System.in);
    }

    public void manageStockLevels() {
        List<Medicine> medicines = dataLoader.getAllMedicines(); // Load current medicines

        // Check if there are any medicines
        if (medicines.isEmpty()) {
            System.out.println("No medicines available.");
            return;
        }

        // Display the list of medicines
        System.out.println("\n=== Select Medicine to Update Stock ===");
        for (int i = 0; i < medicines.size(); i++) {
            Medicine medicine = medicines.get(i);
            System.out.printf("%d. %s (Current Stock: %d)%n",
                    i + 1,
                    medicine.getMedicineName(),
                    medicine.getStock());
        }

        // Prompt for selection
        System.out.print("Select the number of the medicine to update: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > medicines.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Medicine selectedMedicine = medicines.get(choice - 1);

        // Prompt for the amount to add
        System.out.print("Enter the amount to add to stock: ");
        int amountToAdd = scanner.nextInt();
        scanner.nextLine();

        if (amountToAdd <= 0) {
            System.out.println("Invalid amount. Please enter a positive number.");
            return;
        }

        // Update the stock
        selectedMedicine.addStock(amountToAdd);
        dataLoader.saveMedicines(medicines);

        System.out.println("Stock updated successfully! New stock for " + selectedMedicine.getMedicineName() + ": "
                + selectedMedicine.getStock());
    }
}