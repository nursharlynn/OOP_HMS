package Administrator;

public class Medicine {
    private String medicineName;
    private int stock;
    private int lowStockLevelAlert;

    /**
     * Constructor for Medicine
     * 
     * @param name Medicine name
     * @param stock Initial stock quantity
     * @param stockAlert Low stock level alert threshold
     */
    public Medicine(String name, int stock, int stockAlert) {
        this.medicineName = name;
        this.stock = stock;
        this.lowStockLevelAlert = stockAlert;
    }

    public String getMedicineName() {
        return this.medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getStock() {
        return this.stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getLowStockLevelAlert() {
        return this.lowStockLevelAlert;
    }

    public void setLowStockLevelAlert(int lowStockLevelAlert) {
        this.lowStockLevelAlert = lowStockLevelAlert;
    }

    @Override
    public String toString() {
        return "Medicine{" +
               "name='" + medicineName + '\'' +
               ", stock=" + stock +
               ", lowStockAlert=" + lowStockLevelAlert +
               '}';
    }

    // Check if stock is low
    public boolean isLowStock() {
        return this.stock <= this.lowStockLevelAlert;
    }

    // Reduce stock when medicine is used
    public void reduceStock(int quantity) {
    if (quantity > 0 && quantity <= this.stock) {
        this.stock -= quantity;
        }
    }

    // Add stock when replenishing
    public void addStock(int quantity) {
        if (quantity > 0) {
            this.stock += quantity;
        }
    }

    public String getName(){
        return this.medicineName;
    }
}