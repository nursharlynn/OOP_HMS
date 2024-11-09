package Pharmacist;

import Administrator.Medicine;
import java.util.concurrent.atomic.AtomicInteger;

public class ReplenishmentRequest {

    private static final AtomicInteger idCounter = new AtomicInteger(0);
    private int requestId;
    private String medicineName;
    private Pharmacist requester;
    private int quantity;

    public ReplenishmentRequest(String medicineName, Pharmacist requester, int quantity) {
        this.requestId = generateId();
        this.medicineName = medicineName;
        this.requester = requester;
        this.quantity = quantity;
    }

    /**
     * Generate a unique ID for the request.
     * @return Unique ID.
     */
    private int generateId() {
        return idCounter.incrementAndGet();
    }

    public int getRequestId() {
        return requestId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public Pharmacist getRequester() {
        return requester;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "ReplenishmentRequest[ID: " + requestId + ", Medicine: " + medicineName + ", Quantity: " + quantity + "]";
    }
}
