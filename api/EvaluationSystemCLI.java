package api;

import java.util.List;

import gr.aueb.para8.core.WaitTimeEvaluator;
import gr.aueb.para8.db.Elevator;
import gr.aueb.para8.db.RideRequest;

public class EvaluationSystemCLI {

    static final int SECONDS_PER_FLOOR = 2;

    public static void main(String[] args) {
        System.out.println("ELEVATE evaluation System starting");

        int originFloor = 7;
        int destinationFloor = 12;
        int elevatorFloor = 2;

        System.out.println("Call: from floor " + originFloor + " to floor " + destinationFloor);

        int waitTime = calculateWaitTime(elevatorFloor, originFloor);
        System.out.println("Estimated wait time: " + waitTime + " seconds");

        runLoadTest();

        Elevator a = new Elevator("A", 8);
        a.setCurrentFloor(1);
        Elevator b = new Elevator("B", 8);
        b.setCurrentFloor(3);
        Elevator c = new Elevator("C", 8);
        c.setCurrentFloor(12);

        List<RideRequest> rides = List.of(
                new RideRequest(1L, 1, 2, RideRequest.NORMAL),
                new RideRequest(2L, 7, 3, RideRequest.NORMAL),
                new RideRequest(3L, 0, 5, RideRequest.NORMAL),
                new RideRequest(4L, 10, 2, RideRequest.NORMAL),
                new RideRequest(5L, 4, 8, RideRequest.NORMAL),
                new RideRequest(6L, 15, 0, RideRequest.NORMAL),
                new RideRequest(7L, 2, 9, RideRequest.NORMAL),
                new RideRequest(8L, 12, 1, RideRequest.NORMAL),
                new RideRequest(9L, 6, 11, RideRequest.NORMAL),
                new RideRequest(10L, 20, 5, RideRequest.NORMAL));

        WaitTimeEvaluator evaluator = new WaitTimeEvaluator();
        double average = evaluator.averageWaitTime(rides, List.of(a, b, c));
        System.out.println("Calls: " + rides.size());
        System.out.println("Average Wait Time: " + average + " seconds");

        generateReport();
    }

    public static int calculateWaitTime(int elevatorFloor, int originFloor) {
        int distance = Math.abs(elevatorFloor - originFloor);
        return distance * SECONDS_PER_FLOOR;
    }

    public static void runLoadTest() {
        System.out.println("Load test not implemented yet.");
    }

    public static void generateReport() {
        System.out.println("Report not implemented yet.");
    }
}