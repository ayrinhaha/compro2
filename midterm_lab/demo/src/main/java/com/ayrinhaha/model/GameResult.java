package com.ayrinhaha.model;

/**
 * Stores a single round result including both players.
 *
 * @author ayrinhaha
 */
public class GameResult {

    private int roundNumber;

    private String p1Name;
    private String p2Name;

    private String p1Move;
    private String p2Move;

    private String winnerName;

    private int p1Score;
    private int p2Score;

    public GameResult(int roundNumber,
                      String p1Name, String p2Name,
                      String p1Move, String p2Move,
                      String winnerName,
                      int p1Score, int p2Score) {

        this.roundNumber = roundNumber;
        this.p1Name = p1Name;
        this.p2Name = p2Name;
        this.p1Move = p1Move;
        this.p2Move = p2Move;
        this.winnerName = winnerName;
        this.p1Score = p1Score;
        this.p2Score = p2Score;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public String getP1Name() {
        return p1Name;
    }

    public String getP2Name() {
        return p2Name;
    }

    public String getP1Move() {
        return p1Move;
    }

    public String getP2Move() {
        return p2Move;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public int getP1Score() {
        return p1Score;
    }

    public int getP2Score() {
        return p2Score;
    }
}