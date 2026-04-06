package com.ayrinhaha;

import com.ayrinhaha.model.*;
import com.ayrinhaha.service.PersistenceService;
import java.io.*;
import java.net.*;
import java.util.InputMismatchException;

/**
 * Game server for a 2-player Rock-Paper-Scissors session.
 *
 * Handles client connections, authentication, game rounds, and final results.
 * Maintains global leaderboard using {@link PersistenceService}.
 * 
 * 
 * 
 * Server listens on port 5000 and accepts exactly two clients per session.
 * Each client authenticates or registers, plays 3 rounds, and receives round
 * results
 * and the final leaderboard.
 * 
 * 
 * @author ayrinhaha
 */
public class Server {

    /**
     * Persistence service instance for user authentication and leaderboard
     * management.
     */
    private static final PersistenceService persistence = new PersistenceService();

    /**
     * Converts an integer input into a corresponding {@link GameMove}.
     *
     * @param val 0 = Rock, 1 = Paper, 2 = Scissors
     * @return GameMove instance corresponding to input
     * @throws InputMismatchException if input is invalid
     */
    public static GameMove mapMove(int val) {
        GameMove move;

        switch (val) {
            case 0:
                move = new Rock();
                break;
            case 1:
                move = new Paper();
                break;
            case 2:
                move = new Scissors();
                break;
            default:
                throw new InputMismatchException("Invalid move.");
        }

        return move;
    }

    /**
     * Authenticates a player through client-server communication.
     * 
     * Reads authentication requests from the client, supports both LOGIN and
     * REGISTER.
     * Sends SUCCESS or FAIL messages back to the client depending on outcome.
     * 
     *
     * @param in  input stream from client
     * @param out output stream to client
     * @return authenticated {@link Player} object
     * @throws IOException if client disconnects or sends invalid data
     */
    private static Player authenticatePlayer(BufferedReader in, PrintWriter out) throws IOException {
        while (true) {
            String request = in.readLine();

            if (request == null) {
                throw new IOException("Client disconnected during authentication.");
            }

            String[] parts = request.split("\\|");

            if (parts.length != 3) {
                out.println("FAIL|Invalid request format.");
                continue;
            }

            String action = parts[0].trim();
            String user = parts[1].trim();
            String pass = parts[2].trim();

            boolean isRegister = action.equals("REGISTER");

            boolean ok = persistence.authenticate(user, pass, isRegister);

            if (ok) {
                out.println("SUCCESS|" + user);
                return new Player(user);
            } else {
                out.println("FAIL|" + (isRegister ? "User exists" : "Auth failed"));
            }
        }
    }

    /**
     * Main entry point for the server application.
     * 
     * Starts a server on port 5000, accepts two clients, runs a 3-round game
     * session,
     * updates user stats, and sends final results and leaderboard to both clients.
     * 
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server Live. Waiting for 2 players...");

            // accept first client
            Socket s1 = serverSocket.accept();
            PrintWriter out1 = new PrintWriter(s1.getOutputStream(), true);
            BufferedReader in1 = new BufferedReader(new InputStreamReader(s1.getInputStream()));
            out1.println("START");

            // accept second client
            Socket s2 = serverSocket.accept();
            PrintWriter out2 = new PrintWriter(s2.getOutputStream(), true);
            BufferedReader in2 = new BufferedReader(new InputStreamReader(s2.getInputStream()));
            out2.println("START");

            // authenticate both players
            Player p1 = authenticatePlayer(in1, out1);
            Player p2 = authenticatePlayer(in2, out2);

            // send player names to both clients
            String namesMsg = "NAMES|" + p1.getName() + "|" + p2.getName();
            out1.println(namesMsg);
            out2.println(namesMsg);

            // create game session
            GameSession session = new GameSession(p1, p2);

            // play 3 rounds
            for (int i = 1; i <= 10; i++) {
                out1.println("YOUR_MOVE");
                out2.println("YOUR_MOVE");

                int m1 = Integer.parseInt(in1.readLine());
                int m2 = Integer.parseInt(in2.readLine());

                p1.setCurrentMove(mapMove(m1));
                p2.setCurrentMove(mapMove(m2));

                GameResult gr = session.playRound(i);

                // construct round message
                String msg = String.format(
                        "ROUND|%d|%s|%s|%s|%d|%d",
                        gr.getRoundNumber(),
                        gr.getP1Move(),
                        gr.getP2Move(),
                        gr.getWinnerName(),
                        gr.getP1Score(),
                        gr.getP2Score());

                // send round results to both clients
                out1.println(msg);
                out2.println(msg);
            }

            // update global leaderboard
            persistence.updateStats(session.getResults());

            // determine final winner
            String finalWinner = p1.getScore() > p2.getScore() ? p1.getName()
                    : p2.getScore() > p1.getScore() ? p2.getName()
                            : "Tie";

            // retrieve leaderboard
            String leaderboard = persistence.getLeaderboardData();

            // send final results to both clients
            String finalResponse = "FINAL|" + finalWinner + "|" + leaderboard;
            out1.flush();
            out2.flush();

            out1.println(finalResponse);
            out2.println(finalResponse);

            System.out.println("Game Over. Leaderboard sent.");

        } catch (Exception e) {
            System.out.println("Server Error: " + e.getMessage());
        }
    }
}