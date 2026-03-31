package com.ayrinhaha.model;

public class GameResult {
    private int p1Move;
    private int p2Move;
    private String winnerName;
    private int roundNumber;

    public int getP1Move() {
        return p1Move;
    }

    public void setP1Move(int p1Move) {
        this.p1Move = p1Move;
    }

    public int getP2Move() {
        return p2Move;
    }

    public void setP2Move(int p2Move) {
        this.p2Move = p2Move;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public GameResult(int p1Move, int p2Move, String winnerName, int roundNumber) {
        this.p1Move = p1Move;
        this.p2Move = p2Move;
        this.winnerName = winnerName;
        this.roundNumber = roundNumber;
    }

    @Override
    public String toString() {
        return String.format("""
                ====== MATCH RESULTS ======
                    Player 1 move:
                    Player 2 move:
                    Winner:
                    Round:
                       """, p1Move, p2Move, winnerName, roundNumber);
    }

}
