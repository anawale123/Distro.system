package activemqexample;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class LoyaltyPointsProducer implements Runnable {

    private final String action;       
    private final String customerId;   
    private final int points;         

     // constructs the loyaltyPointProducer with a specified actions such as the customer id and points
    public LoyaltyPointsProducer(String action, String customerId, int points) {
        this.action = action;
        this.customerId = customerId;
        this.points = points;
    }

     //used for running the producer to send message to jms queue
    @Override
    public void run() {
        try {
            // Create a connection factory for the activemq and establishes connections 
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a session and a queues the destination
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("LoyaltyPointsQueue");

            // Create a producer to send messages to the queue
            MessageProducer producer = session.createProducer(destination);

            // Construct the message text based on the user actions
            String messageText = action + " " + points + " points for customer " + customerId;
            if (action.equals("EARNED")) {
                messageText += ". Thank you for adding points!";
            } else if (action.equals("REDEEMED")) {
                messageText += ". You can use this code in-store now.";
            }

            // Create and send the message
            TextMessage message = session.createTextMessage(messageText);
            producer.send(message);
            System.out.println("Sent message: " + messageText);

           
            producer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
