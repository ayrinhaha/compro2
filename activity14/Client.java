/**
 * The Client class facilitates a connection to a multi-threaded chat server.
 * It manages two concurrent tasks: listening for incoming messages from the 
 * server via a background thread and reading user input from the console to 
 * send outgoing messages.
 * * @author ayrinhaha
 */
public class Client {

    /**
     * The host address of the chat server.
     */
    private static final String HOST = "localhost";

    /**
     * The port number the chat server is listening on.
     */
    private static final int PORT = 8000;

    /**
     * Entry point for the client application. Sets up the socket connection,
     * initializes I/O streams, and manages the communication loop.
     * * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {}
}