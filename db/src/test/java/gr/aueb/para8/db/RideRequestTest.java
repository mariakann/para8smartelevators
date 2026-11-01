package gr.aueb.para8.db;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class RideRequestTest{

    @Test
    void sameFloorIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new RideRequest(1L, 4, 4, RideRequest.NORMAL));
    }

    @Test
    void priority42IsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new RideRequest(1L, 0, 5, 42));
    }

    @Test
    void directionFollowsFloors() {
        RideRequest up = new RideRequest(1L, 0, 5, RideRequest.NORMAL);
        RideRequest down = new RideRequest(1L, 5, 0, RideRequest.NORMAL);
        assertTrue(up.isGoingUp());
        assertFalse(down.isGoingUp());
    }

    @Test
    void assignToStoresTheElevator() {
        RideRequest ride = new RideRequest(1L, 0, 5, RideRequest.NORMAL);
        ride.assignTo("A");
        assertEquals("A", ride.getElevatorId());
        assertEquals(RideRequest.ASSIGNED, ride.getStatus());
    }

    @Test
    void sortingByPriorityPutsEmergenciesFirst() {
        RideRequest normal = new RideRequest(1L, 0, 5, RideRequest.NORMAL);
        RideRequest emergency = new RideRequest(2L, 14, 0, RideRequest.EMERGENCY);
        RideRequest meeting = new RideRequest(3L, 0, 22, RideRequest.MEETING);
        List<RideRequest> queue = new ArrayList<>(List.of(normal, emergency, meeting));
        queue.sort(Comparator.comparingInt(RideRequest::getPriorityLevel));
        assertEquals(List.of(emergency, meeting, normal), queue);
    }  
}