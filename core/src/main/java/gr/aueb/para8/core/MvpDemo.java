package gr.aueb.para8.core;

import java.util.List;

import gr.aueb.para8.db.Elevator;
import gr.aueb.para8.db.RideRequest;

public class MvpDemo {
    
    public static void main(String[] args) {
        Elevator a = new Elevator("A", 8);
        Elevator b = new Elevator("B", 8);
        b.setCurrentFloor(4);
        Elevator c = new Elevator("C", 8);
        c.setCurrentFloor(9);

        int origin = 5;
        int destination =1;

        System.out.println("A at 0, B at 4, C at 9.");
        System.out.println("Call from " + origin + " to " + destination + ".");

        RideRequest ride = new RideRequest(1L, origin, destination, RideRequest.NORMAL);
        Elevator chosen = new DispatchAlgorithm().chooseElevator(ride, List.of(a, b, c));

        if (chosen == null) {
            System.out.println("nobody is free.");
        } else {
            System.out.println("Elevator " + chosen.getId());
        }

        a.setStatus("EMERGENCY");
        b.setStatus("EMERGENCY");
        c.setStatus("EMERGENCY");

       Elevator afterAlarm = new DispatchAlgorithm().chooseElevator(ride, List.of(a, b, c));
        if (afterAlarm == null) {
            System.out.println("Alarm: nobody is free.");
        }
    }
}
