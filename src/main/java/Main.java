import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner commandLine = new Scanner(System.in);
            System.out.print("Enter your username: ");
            String name = commandLine.nextLine();
            Chat chat = new Chat("TopicCF", "topicChat", name);
            chat.writeMessage(" joined the chat!");
            while (true) {

                String s = commandLine.nextLine();

                if (s.equalsIgnoreCase("exit")) {
                    chat.close();
                    System.exit(0);
                } else {
                    chat.writeMessage(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
