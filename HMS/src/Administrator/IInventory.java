package Administrator;

public interface IInventory extends IViewInventory {

	/**
	 * 
	 * @param medicine
	 */
	void addMedicine(Medicine medicine);

	/**
	 * 
	 * @param medicine
	 */
	void removeMedicine(Medicine medicine);

	/**
	 * 
	 * @param medicine
	 * @param quantity
	 */
	void updateStockLevel(Medicine medicine, int quantity);

	/**
	 * 
	 * @param medicine
	 * @param quantity
	 */
	void updateLowStockLevelAlert(Medicine medicine, int quantity);

	/**
	 * 
	 * @param inventory
	 */
	void display(Inventory inventory);

}