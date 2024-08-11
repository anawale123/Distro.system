package activemqexample;

public class ActiveMQExample {

    public static void main(String[] args) {
        try {
            ActiveMQExample app = new ActiveMQExample();
            
            // Starts the consumers to listen for JMS messages
            app.receive();
            
            // Keeps the application running for to listen to messages
            
            System.out.println("ActiveMQExample is running. Press Ctrl+C to stop.");
            Thread.sleep(Long.MAX_VALUE); // Keeps the application running indefinitely
            
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
// Sends  points message to the JMS queue
    public static void sendPointsMessage(String customerId, int points, String action) throws Exception {
        thread(new LoyaltyPointsProducer(action, customerId, points), false);
    }
// Starts the JMS consumer threads to listen for incoming message
    private void receive() throws Exception {
        thread(new LoyaltyPointsConsumer(), false);
    }
// Creates and starts a new thread for the given Runnable task
    private static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }
}

  
