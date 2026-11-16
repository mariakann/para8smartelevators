package gr.aueb.para8.core;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import gr.aueb.para8.core.DispatchAlgorithm;
import gr.aueb.para8.db.Elevator;
import gr.aueb.para8.db.RideRequest;

public class DispatchAlgorithmTest {
    @Test 
    void choosesClosestAvailableElevator(){
        Elevator a = new Elevator("A",8);
        a.setCurrentFloor(5);

        Elevator c = new Elevator ("C",8);
        c.setCurrentFloor(11);

        RideRequest ride = new RideRequest(1L, 5, 10, RideRequest.NORMAL);

        DispatchAlgorithm algorithm = new DispatchAlgorithm();
        Elevator chosen = algorithm.chooseElevator(ride, List.of(a,c));

        assertEquals("A", chosen.getId());
    }

    @Test
    void skipsUnavailableElevators() {
        Elevator a = new Elevator("A", 8);
        a.setCurrentFloor(2);

        Elevator b = new Elevator("B", 6);
        b.setCurrentFloor(3);
        b.setStatus("MAINTENANCE");

        RideRequest ride = new RideRequest(1L, 5, 10, RideRequest.NORMAL);

        DispatchAlgorithm algorithm = new DispatchAlgorithm();
        Elevator chosen = algorithm.chooseElevator(ride, List.of(a, b));

        assertEquals("A", chosen.getId());
    }

    @Test
    void returnsNullWhenNoElevatorAvailable() {
        Elevator d = new Elevator("D", 8);
        d.setCurrentFloor(2);
        d.setStatus("WORKING");

        Elevator e = new Elevator("E", 6);
        e.setCurrentFloor(3);
        e.setStatus("MAINTENANCE");

        RideRequest ride = new RideRequest(1L, 5, 10, RideRequest.NORMAL);

        DispatchAlgorithm algorithm = new DispatchAlgorithm();
        Elevator chosen = algorithm.chooseElevator(ride, List.of(d, e));

        assertNull(chosen);
    }
}