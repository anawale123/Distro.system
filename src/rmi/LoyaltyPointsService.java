package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoyaltyPointsService extends Remote {
    // Method for adding points to a customer's account
    String addPoints(String customerId, int points) throws RemoteException;

    // Method for redeemubg points from a customer's account to use in store
    String redeemPoints(String customerId, int points) throws RemoteException;
}
