package gr.aueb.para8.core;
import java.util.List;

import gr.aueb.para8.db.Elevator;
import gr.aueb.para8.db.RideRequest;

public class DispatchAlgorithm {
    public Elevator chooseElevator(RideRequest ride, List<Elevator> elevators) {
        if(ride == null || elevators == null) {
            throw new IllegalArgumentException("ride and elevators are required");

        }

    Elevator closest = null;
    for (Elevator elevator : elevators) {
        if (!elevator.isAvailable()) {
            continue;
        }
        if (closest == null || elevator.distanceTo(ride.getOriginalFloor()) < closest.distanceTo(ride.getOriginalFloor())) {
            closest = elevator;
        }
    }  
    return closest;
    }

}
