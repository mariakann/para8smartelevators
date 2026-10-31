package api;

public class EvaluationSystemCLI {
    public static void main(String[] args) {
        System.out.println("ELEVATE evaluation System starting");

        runLoadTest();
        double average = calculateAverageWaitTime();
        System.out.println("Average Wait Time:" + average);
        generateReport();
    }

    public static double calculateAverageWaitTime() {
        return 0.0;
    }

    public static void generateReport() {
        System.out.println("Report not implemented yet.");
    }

    public static void runLoadTest() {
        System.out.println("Load test not implemented yet.");
    }
}
