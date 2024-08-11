package rmi;

import activemqexample.ActiveMQExample;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

public class LoyaltyPointsServiceImpl extends UnicastRemoteObject implements LoyaltyPointsService {

    // Constructor used for the rmi setup
    public LoyaltyPointsServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String addPoints(String customerId, int points) throws RemoteException {
        // Creates the result message for adding points
        String result = "Added " + points + " points for customer " + customerId;
        System.out.println(result);

        // Sends the addition of points to information to JMS with action "EARNED"
        try {
            sendToJMS(customerId, points, "EARNED");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public String redeemPoints(String customerId, int points) throws RemoteException {
        // Creates the result message for redeeming points
        String result = "Redeemed " + points + " points for customer " + customerId;

        // Generate a 5-digit redemption code
        String redeemCode = generateRedeemCode();
        result += ". Your redeem code is: " + redeemCode;
        System.out.println(result);

        // Sends the points redemption information to JMS with actions "REDEEMED"
        try {
            sendToJMS(customerId, points, "REDEEMED");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // Used to Generates a 5-digit random code for redeeming code
    private String generateRedeemCode() {
        Random random = new Random();
        int code = 10000 + random.nextInt(90000); // Ensures a 5-digit code
        return Integer.toString(code);
    }

    // Sends the message to JMS based on the actions either (EARNED or REDEEMED)
    private void sendToJMS(String customerId, int points, String action) throws Exception {
        // Call for the method in ActiveMQExample to send the JMS message
        ActiveMQExample.sendPointsMessage(customerId, points, action);
    }
}
