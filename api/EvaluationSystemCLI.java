package api;

public class EvaluationSystemCLI {

    static final int SECONDS_PER_FLOOR = 3;

    public static void main(String[] args) {
        System.out.println("ELEVATE evaluation System starting");

        int originFloor = 7;
        int destinationFloor = 12;
        int elevatorFloor = 2;

        System.out.println("Call: from floor " + originFloor + " to floor " + destinationFloor);

        int waitTime = calculateWaitTime(elevatorFloor, originFloor);
        System.out.println("Estimated wait time: " + waitTime + " seconds");

        runLoadTest();
        double average = calculateAverageWaitTime();
        System.out.println("Average Wait Time: " + average);
        generateReport();
    }

    public static int calculateWaitTime(int elevatorFloor, int originFloor) {
        int distance = Math.abs(elevatorFloor - originFloor);
        return distance * SECONDS_PER_FLOOR;
    }

    public static void runLoadTest() {
        System.out.println("Load test not implemented yet.");
    }

    public static double calculateAverageWaitTime() {
        return 0.0;
    }

    public static void generateReport() {
        System.out.println("Report not implemented yet.");
    }
}