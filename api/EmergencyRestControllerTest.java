import java.util.List;

public class EmergencyRestControllerTest {

    public static void main(String[] args) {
        EmergencyRestController controller = new EmergencyRestController();

        String response = controller.triggerEmergency("FIRE");

        if (!"FIRE".equals(controller.getLastEventType())) {
            throw new AssertionError("Expected eventType FIRE");
        }

        List<String> statuses = controller.getElevatorStatuses();
        for (int i = 0; i < statuses.size(); i++) {
            if ("IDLE".equals(statuses.get(i))) {
                throw new AssertionError("Elevator should not remain IDLE during emergency");
            }
            if (controller.isAvailable(i)) {
                throw new AssertionError("Expected isAvailable(" + i + ") to be false during emergency");
            }
        }

        if (!response.contains("\"availableElevators\":0") || !response.contains("No available elevator")) {
            throw new AssertionError("Expected no available elevator JSON response, got: " + response);
        }

        controller.resetSystem();

        if (controller.getLastEventType() != null) {
            throw new AssertionError("Expected lastEventType to be null after resetSystem");
        }

        List<String> resetStatuses = controller.getElevatorStatuses();
        for (int i = 0; i < resetStatuses.size(); i++) {
            if (!"IDLE".equals(resetStatuses.get(i))) {
                throw new AssertionError("Expected status IDLE after resetSystem at index " + i);
            }
            if (!controller.isAvailable(i)) {
                throw new AssertionError("Expected isAvailable(" + i + ") to be true after resetSystem");
            }
        }

        System.out.println("Test passed! Response: " + response);
    }
}