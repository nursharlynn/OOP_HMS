package Administrator;

public class Medicine {
    private String medicineName;
    private int stock;
    private int lowStockLevelAlert;

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

    public String getName() {
        return this.medicineName;
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

}