package activemqexample;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;


public class LoyaltyPointsConsumer implements Runnable {

    
     
     // runs the consumer to listen to messages that come the JMS queues
    @Override
    public void run() {
        try {
            // Creates a connection factory for ActiveMQ and establish a connection
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Creates a session and a queue destination
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("LoyaltyPointsQueue");

            // Creates a consumer to receive messages from the queue
            MessageConsumer consumer = session.createConsumer(destination);

            // Sets a message listener to handle incoming messages
            consumer.setMessageListener(message -> {
                if (message instanceof TextMessage) {
                    try {
                        String text = ((TextMessage) message).getText();
                        System.out.println("Received: " + text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });

          
            Thread.sleep(Long.MAX_VALUE);

          
            consumer.close();
            session.close();
            connection.close();
        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
