package gr.aueb.para8.db;
import java.time.OffsetDateTime;

public class RideRequest {

    public static final int EMERGENCY = 1;
    public static final int MEETING = 2;
    public static final int NORMAL = 3;

    public static final String PENDING = "PENDING";
    public static final String ASSIGNED = "ASSIGNED";

    private long id;
    private OffsetDateTime createdAt;
    private Long employeeId;
    private String elevatorId;
    private int originFloor;
    private int destinationFloor;
    private Long meetingId;
    private int priorityLevel;
    private String status;

    public RideRequest(Long employeeId, int originFloor, int destinationFloor, int priorityLevel) {
        if (originFloor == destinationFloor) {
            throw new IllegalArgumentException("Origin and destination must differ");
        }
if (priorityLevel != EMERGENCY && priorityLevel != MEETING && priorityLevel != NORMAL) {
            throw new IllegalArgumentException("Priority lrvel must be 1, 2 or 3: " + priorityLevel);
        }
        this.employeeId = employeeId;
        this.originFloor = originFloor;
        this.destinationFloor = destinationFloor;
        this.priorityLevel = priorityLevel;
        this.status = PENDING;
    }
    
    public boolean isGoingUp() {
        return destinationFloor > originFloor;
    }

    public void assignTo(String elevatorId) {
        if (elevatorId == null || elevatorId.isBlank()) {
            throw new IllegalArgumentException("Elevator id is required");
        }
        this.elevatorId = elevatorId;
        this.status = ASSIGNED;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getElevatorId() {
        return elevatorId;
    }

    public int getOriginalFloor() {
        return originFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public Long getMeetingId() {
        return meetingId;
    }
    
    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public String getStatus() {
        return status;
    }

    @Override 
    public  String toString() {
        return "RideRequest{id=" + id + ", form=" + originFloor + ", to=" + destinationFloor + ", priority=" + ", status=" + status + "}";
    }
}
