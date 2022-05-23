import javax.jms.JMSException;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicConnectionFactory;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.MessageListener;
import javax.naming.InitialContext;
import java.util.Scanner;
public class Chat implements MessageListener {
    private TopicSession pubSession;
    private TopicPublisher publisher;
    private TopicConnection connection;
    private String username;
    public Chat(String topicFactory, String topicName, String username) throws
            Exception {
        InitialContext ctx = new InitialContext();

        TopicConnectionFactory conFactory = (TopicConnectionFactory)
                ctx.lookup(topicFactory);

        TopicConnection connection = conFactory.createTopicConnection();

        TopicSession pubSession = connection.createTopicSession(false,
                Session.AUTO_ACKNOWLEDGE);

        TopicSession subSession = connection.createTopicSession(false,
                Session.AUTO_ACKNOWLEDGE);

        Topic chatTopic = (Topic) ctx.lookup(topicName);

        TopicPublisher publisher = pubSession.createPublisher(chatTopic);

        TopicSubscriber subscriber = subSession.createSubscriber(chatTopic);
        subscriber.setMessageListener(this);
        this.connection = connection;
        this.pubSession = pubSession;
        this.publisher = publisher;
        this.username = username;
        connection.start();
    }
    public void onMessage(Message message) {
        try {

            TextMessage textMessage = (TextMessage) message;
            System.out.println(textMessage.getText());
        } catch (JMSException jmse) {
            jmse.printStackTrace();
        }
    }

    public void writeMessage(String text) throws JMSException {
        TextMessage message = pubSession.createTextMessage();
        message.setText(username + ": " + text);
        publisher.publish(message);
    }
    public void close() throws JMSException {
        connection.close();
    }
}
