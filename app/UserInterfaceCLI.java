import java.util.Scanner;

public class UserInterfaceCLI {

    //Σταθερές βασισμένες στη βάση δεδομένων (10 όροφοι/5 ανελκυστήρες)
    private static final int minFloor = 0;
    private static final int maxFloor = 9;
    private static final int totalElevators = 5;

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("--------------------------");
            System.out.println("----- SMART-ELEVATORS ----");
            System.out.println("--------------------------");

            System.out.println("1. Probolh Orofwn (" + minFloor + "-" + maxFloor + ")");
            System.out.println("2. Probolh Anelkusthrwn (1-" + totalElevators + ")");
            System.out.println("3. Aitima Metakinhshs (me epilogh meeting)");
            System.out.println("0. Exodos");
            System.out.println("Parakalw epilexte energeia: ");

            String option = scanner.nextLine().trim();

            switch (option) {
                case "1":
                    showFloors();
                    break;

                case "2":
                    showElevators();
                    break;

                case "3":
                    createRideRequest();
                    break;
                
                case "0":
                    running = false;
                    System.out.println("Exodos apo to ELEVATE. Euxaristoume!");
                    break;

                default:
                    System.out.println("Kati egine la8os. Parakaloume dokimaste jana.");
            }
        }
        
        scanner.close();
    }

    private static void showFloors() {
        System.out.println("\n Lista Orofwn (" + minFloor + "-" + maxFloor + ")");
        for (int i = minFloor; i <= maxFloor; i++) {

            //Όροφος 0 => ΙΣΟΓΕΙΟ
            String floorName = (i == 0) ? "ISOGEIO (Ground)" : "Orofos " + i;
            String status = (i == minFloor || i == maxFloor) ? "PERIORISMENH PROSBASH" : "KANONIKH PROSBASH";
            System.out.println("Floor ID: " + i + " | Onoma: "  + floorName + " | " + status);
        }
    }

    public static void showElevators() {
        System.out.println("\n Lista Anelkusthrwn (1-" + totalElevators + ")");
        for (int i = 1; i <= totalElevators; i++) {
            System.out.println("Elevator ID: " + i + " | Xwrhtikothta: 8 atoma | Trexwn Orofos: 0");
        }
    }

    /*
    AI-Tool: ChatGpt
     AI-Model: gpt-4o
     AI-Task: CLI UserUnterface implementation for creating ride requests
     AI-Prompt-1: Δημιούργησε μέθοδο createRideRequest για CLI σε java
     AI-Tokens: ~450
     AI-Role: Developer Assistant
    */
    
    public static void createRideRequest() {
        System.out.println("\n Neo Aithma Metakinhshs");

        System.out.println("Employee ID: ");
        String employeeId = scanner.nextLine().trim();

        int originFloor = readFloor("Origin floor ID (0-9): ");
        int destinationFloor = readFloor("Destination floor ID (0-9): ");

        while (destinationFloor == originFloor) {
            System.out.println("SFALMA: O orofos proorismou prepei na diaferei apo ton orofo afethrias.");
            destinationFloor = readFloor("Destination floor ID (0-9): ");
        }

        System.out.println("Priority Level (akeraios px 1): ");
        int priority = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Meeting Code (Optional; Pathste Enter gia Skip): ");
        String meetingCode = scanner.nextLine().trim();

        //Εμφάνιση Αποτελέσματος
        System.out.println("\n To aithma kataxwrh8hke EPITYXWS!");
        System.out.println("ID ergazomenou: " + employeeId);
        System.out.println("Diadromh: Orofos " + originFloor + "-> Orofos " + destinationFloor);
        System.out.println("Proteraiothta: " + priority);

        if (meetingCode.isEmpty()) {
            System.out.println("Tupos Aithmatos: Kanoniko");
        } else {
            System.out.println("Tupos Aithmatos: Sunanthsh");
            System.out.println("Kwdikos Synanthshs: " + meetingCode);
        }
    }

    /*
    AI-Tool: ChatGpt
     AI-Model: gpt-4o
     AI-Task: Implementation of CLI user Interface, ride requests, and floor input validation
     AI-Prompt-1: Πως να υλοποιήσω τη μέθοδο readFloor με έλεγχο ορίων και try-catsh σε java
     AI-Tokens: ~600
     AI-Role: Developer Assistant
    */
    
    private static int readFloor(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try{
                int floor = Integer.parseInt(input);
                if (floor >= minFloor && floor <= maxFloor) {
                    return floor;
                }
                System.out.println("Parakalw eisagete orofo metaju " + minFloor + "kai " + maxFloor + ".");
            } catch (NumberFormatException e) {
                System.out.println("Mh egkurh eisodos! Parakalw dwste akeraio ari8mo.");
            }
        }
    }
}
