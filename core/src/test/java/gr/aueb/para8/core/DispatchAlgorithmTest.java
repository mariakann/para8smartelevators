package gr.aueb.para8.core;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import  org.junit.jupiter.api.Test;

import gr.aueb.para8.db.Elevator;
import gr.aueb.para8.db.RideRequest;

class DispatchAlgorithmTest {
    
    private final DispatchAlgorithm algorithm = new DispatchAlgorithm();

    @Test
    void choosesTheOnlyIdleElevator() {
        RideRequest ride = new RideRequest(1L, 3, 7, RideRequest.NORMAL);
        Elevator elevator = new Elevator("A", 8);

        Elevator chosen = algorithm.chooseElevator(ride, List.of(elevator));

        assertSame(elevator, chosen);
    }

    @Test
    void choosesTheClosestIdleElevator() {
        RideRequest ride = new RideRequest(2L, 5, 1, RideRequest.NORMAL);
        Elevator far = new Elevator("A", 8);
        Elevator near = new Elevator("B", 8);
        near.setCurrentFloor(4);

        Elevator chosen = algorithm.chooseElevator(ride, List.of(far, near));

        assertSame(near, chosen);
    }

    @Test
    void skipAnElevatorThatIsNotAvailable() {
        RideRequest ride = new RideRequest(3L, 5, 1, RideRequest.NORMAL);
        Elevator busyAndClose = new Elevator("A", 8);
        busyAndClose.setCurrentFloor(5);
        busyAndClose.setStatus("MOVING");
        Elevator idleAndFar = new Elevator("B", 8);

        Elevator chosen = algorithm.chooseElevator(ride, List.of(busyAndClose, idleAndFar));

        assertSame(idleAndFar, chosen);
    }

    @Test
    void returnNullWhenEveryElevatorIsBusy() {
        RideRequest ride = new RideRequest(4L, 2, 6, RideRequest.NORMAL);
        Elevator busy = new Elevator("A", 8);
        busy.setCurrentFloor(2);
        busy.setStatus("MOVING");

        Elevator chosen = algorithm.chooseElevator(ride, List.of(busy));

        assertNull(chosen);
    }

    @Test
    void keepsTheFirstWhenDistanceIsTheSame() {
        RideRequest ride = new RideRequest(5L, 5, 0, RideRequest.NORMAL);
        Elevator first = new Elevator("A", 8);
        first.setCurrentFloor(4);
        Elevator second = new Elevator("B", 8);
        second.setCurrentFloor(6);

        Elevator chosen = algorithm.chooseElevator(ride, List.of(first, second));

        assertSame(first, chosen);
    }

    @Test
    void rejectsMissingArguments() {
        RideRequest ride = new RideRequest(6L, 1, 2, RideRequest.NORMAL);
        Elevator elevator = new Elevator("A", 8);

        assertThrows(IllegalArgumentException.class,
            () -> algorithm.chooseElevator(null, List.of(elevator)));
        assertThrows(IllegalArgumentException.class,
            () -> algorithm.chooseElevator(ride, null));
    }
}
