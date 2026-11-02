import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@RestController
public class EmergencyRestController {
    @GetMapping("/emergency/{elevatorId}")
    public String triggerEmergency(@PathVariable String elevatorId) {
        // Logic to trigger emergency for the specified elevator
        return "Alarm activated for elevator: " + elevatorId;
    }
}
