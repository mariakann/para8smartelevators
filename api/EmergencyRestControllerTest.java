
import java.util.List;

public class EmergencyRestControllerTest {

    public static void main(String[] args) {
        EmergencyRestController controller = new EmergencyRestController();

        String response = controller.triggerEmergency("FIRE");

        if (!"FIRE".equals(controller.getLastEventType())) {
            throw new AssertionError("Expected eventType FIRE");
        }

        List<String> statuses = controller.getElevatorStatuses();
        for (String status : statuses) {
            if ("IDLE".equals(status)) {
                throw new AssertionError("Elevator should not remain IDLE during emergency");
            }
        }

        if (!response.contains("\"availableElevators\":0") || !response.contains("No available elevator")) {
            throw new AssertionError("Expected no available elevator JSON response, got: " + response);
        }

        System.out.println("Test passed! Response: " + response);
    }
}