import java.io.*;
import java.net.Socket;

/**
 * ClientHandler is responsible for managing communication
 * between the server and a single client.
 * 
 * Implements Runnable - follows best practice over extending Thread.
 * Each instance runs in its own thread.
 * 
 * 
 * @author ayrinhaha
 */
public class ClientHandler implements Runnable {

    /** Reference to the client's socket connection. */
    private Socket socket;

    /** Input stream from client */
    private BufferedReader reader;

    /** Output stream to client */
    private PrintWriter writer;

    /** Client username */
    private String clientName;

    /**
     * Constructs a new ClientHandler for a specific socket.
     * 
     * @param var1 The socket object representing the client connection.
     */
    public ClientHandler(Socket var1) {
        this.socket = var1;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            cleanup();
        }
    }

    /**
     * The main execution logic for the client handler thread.
     * 
     * Thread Lifecycle:
     * - RUNNING: starts when Thread.start() is called
     * - BLOCKED: waiting for input (readLine)
     * - TERMINATED: when method exits
     * 
     * Handles:
     * - Username registration
     * - Receiving messages
     * - Broadcasting messages
     */
    @Override
    public void run() {
        try {
            writer.println("Enter your name:");
            clientName = reader.readLine();

            Server.broadcast(clientName + " has joined the chat!", this);

            String message;

            while ((message = reader.readLine()) != null) {

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                Server.broadcast(clientName + ": " + message, this);
            }

        } catch (IOException e) {
            System.out.println("Connection error with client.");
        } finally {
            cleanup();
        }
    }

    /**
     * Performs cleanup operations when a client disconnects.
     */
    private void cleanup() {
        try {
            if (clientName != null) {
                Server.broadcast(clientName + " has left the chat.", this);
            }

            Server.removeClient(this);

            if (socket != null)
                socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a specific message string to this handler's client.
     * 
     * @param var1 The message to be sent.
     */
    public void sendMessage(String var1) {
        writer.println(var1);
    }
}