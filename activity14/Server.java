/**
 * The Server class acts as the central hub for the chat application.
 * It listens for new client connections and maintains a list of active
 * ClientHandler instances to facilitate message broadcasting.
 * * @author ayrinhaha
 */
public class Server {

    /**
     * The port number the server binds to.
     */
    private static final int PORT = 8000;

    /**
     * A thread-safe list of active client handlers.
     */
    private static final List<ClientHandler> clients = new CopyOnWriteArrayList<>();

    /**
     * Entry point for the server application. Continuously accepts new
     * socket connections and assigns them to individual ClientHandler threads.
     * * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
    }

    /**
     * Sends a message to all connected clients except for the original sender.
     * * @param message The text message to be broadcasted.
     * 
     * @param sender The ClientHandler instance that initiated the message.
     */
    public static void broadcast(String message, ClientHandler sender) {
    }

    /**
     * Removes a client handler from the active clients list.
     * Usually called during client disconnection or cleanup.
     * * @param handler The ClientHandler to be removed.
     */
    public static void removeClient(ClientHandler handler) {
    }
}