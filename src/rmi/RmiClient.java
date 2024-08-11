package rmi;

import java.rmi.Naming;
import java.util.Scanner;

public class RmiClient {

    public static void main(String[] args) {
        try {
            // Looks up the rmi services for the server
            LoyaltyPointsService service = (LoyaltyPointsService) Naming.lookup("rmi://192.168.1.169:1079/LoyaltyPointsService");
            Scanner scanner = new Scanner(System.in);

            while (true) {
                // option menu is displayed for the user to choose from
                System.out.println("Choose an option:");
                System.out.println("1. Add Points (Press 'a')");
                System.out.println("2. Redeem Pointss (Press 'b')");
                System.out.println("3. Exit (Press 'q')");
                System.out.print("Enter your choice: ");
                String choice = scanner.nextLine();
                
                // Handles the exit route
                if (choice.equalsIgnoreCase("q")) {
                    System.out.println("Exiting...");
                    break;
                }

                // Input for the customer ID and points
                System.out.print("Enter customer ID: ");
                String customerId = scanner.nextLine();

                System.out.print("Enter points: ");
                int points = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                // Calls for the appropriate service method based on user's option
                String result;
                if (choice.equalsIgnoreCase("a")) {
                    result = service.addPoints(customerId, points);
                } else if (choice.equalsIgnoreCase("b")) {
                    result = service.redeemPoints(customerId, points);
                } else {
                    System.out.println("Invalid choice");
                    continue;
                }

                // Displays the users results and prints thank you 
                System.out.println(result);
                System.out.println("Thank you for using our service!");

                // Waits for user input to continue or exit the service
                System.out.println("Press Enter to continue or Ctrl+C to exit...");
                scanner.nextLine();  // Wait for user to press Enter
            }

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
