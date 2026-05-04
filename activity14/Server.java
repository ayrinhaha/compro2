import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The Server class acts as the central hub for the chat application.
 * 
 * @author ayrinhaha
 */
public class Server {

    /** The port number the server binds to. */
    private static final int PORT = 8000;

    /**
     * A thread-safe list of active client handlers.
     * CopyOnWriteArrayList prevents ConcurrentModificationException
     * when multiple threads access/modify the list.
     */
    private static final List<ClientHandler> clients = new CopyOnWriteArrayList<>();

    /**
     * Entry point for the server application.
     * 
     */
    public static void main(String[] args) {
        System.out.println("Server started on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {
                Socket socket = serverSocket.accept(); // BLOCKING CALL
                System.out.println("Client connected: " + socket.getInetAddress());

                ClientHandler handler = new ClientHandler(socket);
                clients.add(handler);

                new Thread(handler).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a message to all connected clients except for the sender.
     * 
     * @param message The text message to be broadcasted.
     * @param sender  The ClientHandler instance that initiated the message.
     */
    public static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    /**
     * Removes a client handler from the active clients list.
     * Called when a client disconnects.
     * 
     * @param handler The ClientHandler to be removed.
     */
    public static void removeClient(ClientHandler handler) {
        clients.remove(handler);
        System.out.println("Client removed. Active clients: " + clients.size());
    }
}