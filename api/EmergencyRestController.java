

import java.util.ArrayList;
import java.util.List;

public class EmergencyRestController {

    private final List<String> elevatorIds;
    private final List<String> elevatorStatuses;
    private String lastEventType;

    public EmergencyRestController() {
        this.elevatorIds = new ArrayList<>(List.of("A", "B", "C"));
        this.elevatorStatuses = new ArrayList<>(List.of("IDLE", "IDLE", "IDLE"));
    }

    public String triggerEmergency(String eventType) {
        String event = (eventType == null || eventType.isBlank())
                ? "GENERAL_EMERGENCY"
                : eventType.trim();
        this.lastEventType = event;

        for (int i = 0; i < elevatorStatuses.size(); i++) {
            elevatorStatuses.set(i, "EMERGENCY");
        }

        return "{\"eventType\":\"" + event + "\",\"availableElevators\":0,\"message\":\"No available elevator\"}";
    }

    public List<String> getElevatorIds() {
        return elevatorIds;
    }

    public List<String> getElevatorStatuses() {
        return elevatorStatuses;
    }

    public String getLastEventType() {
        return lastEventType;
    }

    public void resetSystem() {
        for (int i = 0; i < elevatorStatuses.size(); i++) {
            elevatorStatuses.set(i, "IDLE");
        }
        this.lastEventType = null;
    }

    public boolean isAvailable(int index) {
        if (index < 0 || index >= elevatorStatuses.size()) {
            return false;
        }
        return "IDLE".equals(elevatorStatuses.get(index));
    }
}