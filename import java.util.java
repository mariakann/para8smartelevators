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

            System.out.println("1. Προβολή ορόφων (" + minFloor + "-" + maxFloor + ")");
            System.out.println("2. Προβολή ανελκυστήρων (1-" + totalElevators + ")");
            System.out.println("3. Αίτημα μετακίνησης (με επιλογή meeting)");
            System.out.println("0. Έξοδος");
            System.out.println("Παρακαλώ επιλέξτε ενέργεια: ");

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
                    System.out.println("Έξοδος από το ELEVATE. Ευχαριστούμε!");
                    break;

                default:
                    System.out.println("Κάτι έγινε λάθος. Παρακαλούμε δοκιμάστε ξανά.");
            }
        }
        
        scanner.close();
    }

    private static void showFloors() {
        System.out.println("\n Λίστα Ορόφων (" + minFloor + "-" + maxFloor + ")");
        for (int i = minFloor; i <= maxFloor; i++) {

            //Όροφος 0 => ΙΣΟΓΕΙΟ
            String floorName = (i == 0) ? "ΙΣΟΓΕΙΟ (Ground)" : "Όροφος " + i;
            String status = (i == minFloor || i == maxFloor) ? "ΠΕΡΙΟΡΙΣΜΕΝΗ ΠΡΟΣΒΑΣΗ" : "ΚΑΝΟΝΙΚΗ ΠΡΟΣΒΑΣΗ";
            System.out.println("Floor ID: " + i | + "| Όνομα: " + floorName + " | " + status);
        }
    }

    public static void showElevators() {
        System.out.println("\n Λίστα Ανελκυστήρων (1-" + totalElevators + ")");
        for (int i = 1; i <= totalElevators; i++) {
            System.out.println("Elevator ID: " + i + " | Χωρητικότητα: 8 άτομα | Τρέχων Όροφος: 0");
        }
    }

    public static void createRideRequest() {
        System.out.println("\n Νέο Αίτημα Μετακίνησης");

        System.out.println("Employee ID: ");
        String employeeId = scanner.nextLine().trim();

        int originFloor = readFloor("Origin floor ID (" + minFloor + "-" maxFloor + "): ");
        int destinationFloor = readFloor("Destination floor ID (" + minFloor + "-" maxFloor + "): ");

        while (destinationFloor == originFloor) {
            System.out.println("ΣΦΑΛΜΑ: Ο όροφος προορισμού πρέπει να διαφέρει από τον όροφο αφετηρίας.");
            destinationFloor = readFloor("Destination floor ID (" + minFloor + "-" maxFloor + "): ");
        }

        System.out.println("Priority Level (ακέραιος πχ 1): ");
        int priority = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Meeting Code (Optional; Πατήστε Enter για Skip): ");
        String meetingCode = scanner.nextLine().trim();

        //Εμφάνιση Αποτελέσματος
        System.out.println("\n Το αίτημα καταχωρήθηκε ΕΠΙΤΥΧΩΣ!");
        System.out.println("ID εργαζομένου: " employeeId);
        System.out.println("Διαδρομή: Όροφος " + originFloor + "-> Όροφος " + destinationFloor);
        System.out.println("Προτεραιότητα: " + priority);

        if (meetingCode.isEmpty()) {
            System.out.println("Τύπος Αιτήματος: Κανονικό");
        } else {
            System.out.println("Τύπος Αιτήματος: Συνάντηση");
            System.out.println("Κωδικός Συνάντησης: " + meetingCode);
        }
    }

    private static int readFloor(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try{
                int floor = Integer.parseInt(input);
                if (floor >= minFloor && floor <= maxFloor) {
                    return floor;
                }
                System.out.println("Παρακαλώ εισάγετε όροφο μεταξύ " + minFloor + "και " + maxFloor + ".");
            } catch (NumberFormatException e) {
                System.out.println("Μη έγκυρη είσοδος! Παρακαλώ δώστε ακέραιο αριθμό.");
            }
        }
    }
}
