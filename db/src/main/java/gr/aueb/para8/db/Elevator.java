package gr.aueb.para8.db;
import java.time.OffsetDateTime;

public class Elevator {

    public static final String IDLE = "IDLE";

    private String id;
    private OffsetDateTime createdAt;
    private int capacity;
    private int currentFloor;
    private String status;

    public Elevator(String id, int capacity) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Elevator id is required");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive: " + capacity);
        }
        this.id = id;
        this.capacity = capacity;
        this.currentFloor = 0;
        this.status = IDLE;
    }

    public boolean isAvailable() {
        return IDLE.equals(status);
    }

    public int distanceTo(int floor) {
        return Math.abs(currentFloor - floor);
    }

    public String getId() {
        return id;
    }
    
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override 
    public String toString() {
        return "Elevator{id=" + id + ", floor=" + currentFloor + ", capacity=" + capacity + ", status=" + status + "}";
    }
}
