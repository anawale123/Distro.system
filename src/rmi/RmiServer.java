package rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RmiServer {

    public static void main(String[] args) {
        try {
            // used to create and start the RMI registry on port 1079
            LocateRegistry.createRegistry(1079);
            
            // Create an instance of the service implementation class 
            LoyaltyPointsServiceImpl loyaltyService = new LoyaltyPointsServiceImpl();
            
            // Bind the service to the RMI registry with a name LoyaltyService
            Naming.rebind("rmi://192.168.1.169:1079/LoyaltyPointsService", loyaltyService);

            System.out.println("LoyaltyPoint Server is ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
