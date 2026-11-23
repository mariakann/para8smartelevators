package gr.aueb.para8.core;

import java.util.List;

import gr.aueb.para8.db.Elevator;
import gr.aueb.para8.db.RideRequest;

public class WaitTimeEvaluator {
    static final int SECONDS_PER_FLOOR = 2;

    public double averageWaitTime(List<RideRequest> rides, List<Elevator> elevators) {
        DispatchAlgorithm algorithm = new DispatchAlgorithm();
        double totalWait = 0;
        int served = 0;

        for (RideRequest ride : rides) {
            Elevator chosen = algorithm.chooseElevator(ride, elevators);
            if (chosen == null) {
                continue;
            }
            int distance = chosen.distanceTo(ride.getOriginalFloor());
            totalWait = totalWait + distance * SECONDS_PER_FLOOR;
            served = served + 1;
        }

        if (served == 0) {
            return 0;
        }
        return totalWait / served;
    }
}