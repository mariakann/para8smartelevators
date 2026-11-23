package gr.aueb.para8.db;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class ElevatorTest {
    
    @Test
    void newElevatorIsIdleOnFloorZero() {
        Elevator elevator = new Elevator("A", 12);
        assertTrue(elevator.isAvailable());
        assertEquals(0, elevator.getCurrentFloor());
        assertEquals(12, elevator.getCapacity());
    }

    @Test
    void capacityZeroIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Elevator("A", 0));
    }

    @Test
    void  distanceWorksUpAndDown() {
        Elevator elevator = new Elevator("A", 12);
        elevator.setCurrentFloor(5);
        assertEquals(3, elevator.distanceTo(8));
        assertEquals(4, elevator.distanceTo(1));
    }

    @Test
    void maintenanceIsNotAvailable() {
        Elevator elevator = new Elevator("A", 12);
        elevator.setStatus("MAINTENANCE");
        assertFalse(elevator.isAvailable());
    }
}
