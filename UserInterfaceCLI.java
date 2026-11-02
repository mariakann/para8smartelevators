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
            System.out.println("3. Aitima Metakinhshs");
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

    public static boolean isRestrictedFloor(int floor) {
        return floor == 0 || floor == 9;
    }

    private static void showFloors() {
        System.out.println("\n Lista Orofwn (" + minFloor + "-" + maxFloor + ")");
        for (int i = minFloor; i <= maxFloor; i++) {

            //Όροφος 0 => ΙΣΟΓΕΙΟ
            String floorName = (i == 0) ? "ISOGEIO (Ground)" : "Orofos " + i;
            String status = isRestrictedFloor(i) ? "PERIORISMENH PROSBASH" : "KANONIKH PROSBASH";
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

        int priority = readPriority();

        int originFloor = readFloor("Origin floor ID (0-9): ");
        int destinationFloor = readFloor("Destination floor ID (0-9): ");

        while (destinationFloor == originFloor) {
            System.out.println("SFALMA: O orofos proorismou prepei na diaferei apo ton orofo afethrias.");
            destinationFloor = readFloor("Destination floor ID (0-9): ");
        }

        boolean restrictedFloor = isRestrictedFloor(originFloor) || isRestrictedFloor(destinationFloor);


        boolean employeeIdRequired = priority != 2 || restrictedFloor;

        String employeeId;
        
        while (true) {
            if (employeeIdRequired) {
                System.out.print("Employee ID (ypoxrewtiko): ");
            } else {
                System.out.print("Employee ID (proairetiko, ENTER gia Skip): ");
            }

            employeeId = scanner.nextLine().trim();

            if (!employeeId.isEmpty() || !employeeIdRequired) {
                break;
            }

            System.out.println("SFALMA: To Employee ID einai ypoxrewtiko gia ayto to aithma.");
        }

        String meetingId = "";

        if (priority == 2) {
            while (meetingId.isEmpty()) {
                System.out.print("Meeting ID (ypoxrewtiko): ");
                meetingId = scanner.nextLine().trim();

                if (meetingId.isEmpty()) {
                    System.out.println("SFALMA: To Meeting ID einai ypoxrewtiko.");
                }
            }
        }



        //Εμφάνιση Αποτελέσματος
        System.out.println("\n To aithma kataxwrh8hke EPITYXWS!");
        System.out.println("ID ergazomenou: " + (employeeId.isEmpty() ? "DEN DOTHKE" : employeeId));
        System.out.println("Diadromh: Orofos " + originFloor + "-> Orofos " + destinationFloor);
        System.out.println("Proteraiothta: " + priority);

        switch (priority) {
            case 1:
                System.out.println("Tupos Aithmatos: Emergency");
                break;
            case 2:
                System.out.println("Tupos Aithmatos: Meeting");
                System.out.println("Kwdikos Synanthshs: " + meetingId);
                break;
            case 3:
                System.out.println("Tupos Aithmatos: Normal");
                break;
        }

        if (restrictedFloor) {
            System.out.println("Prosoxh: To aithma periexe orofo me periorismenh prosbash.");
        }

        System.out.println("To aithma elegxthke epityxws!");
    }

    
    private static int readPriority() {
        while (true) {
            System.out.println("\n Proteraiothta:");
            System.out.println("1. Emergency (Epikindyno)");
            System.out.println("2. Meeting (Synanthsh)");
            System.out.println("3. Normal (Kanonikh xrhsh)");

            System.out.print("Parakalw epilexte proteraiothta (1-3): ");

            String input = scanner.nextLine().trim();

            try{
                int priority = Integer.parseInt(input);
                if (priority >= 1 && priority <= 3) {
                    return priority;
                }

                System.out.println("Parakalw epilexte proteraiothta metaksy 1 kai 3.");
            } catch (NumberFormatException e) {
                System.out.println("Mh egkurh eisodos! Parakalw dwste akeraio ari8mo.");
            }
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
   
    public static int readFloor(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                int floor = Integer.parseInt(input);
                if (floor >= minFloor && floor <= maxFloor) {
                    return floor;
                }
                System.out.println("Parakalw epilexte orofos metaksy " + minFloor + " kai " + maxFloor + ".");
            } catch (NumberFormatException e) {
                System.out.println("Mh egkurh eisodos! Parakalw dwste akeraio ari8mo.");
            }
        }
    }



    

}
