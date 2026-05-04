import java.io.*;
import java.net.Socket;

/**
 * Client connects to the chat server and handles:
 * - Username input BEFORE connection
 * - Sending messages to server
 * - Receiving messages from server using a background thread
 */
public class Client {

    private static final String HOST = "localhost";
    private static final int PORT = 8000;

    public static void main(String[] args) {

        try {

            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your username: ");
            String username = console.readLine();

            Socket socket = new Socket(HOST, PORT);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            writer.println(username);

            System.out.println("Connected as " + username);
            System.out.println("Type 'exit' to leave the chat.");

            new Thread(() -> {
                try {
                    String message;
                    while ((message = reader.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            }).start();

            String input;
            while (true) {

                System.out.print("Enter message: ");
                input = console.readLine();

                if (input == null || input.equalsIgnoreCase("exit")) {
                    writer.println("exit");
                    break;
                }

                writer.println(input);
            }

            socket.close();

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}