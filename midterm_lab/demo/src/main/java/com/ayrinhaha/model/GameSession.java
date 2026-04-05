package com.ayrinhaha.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles a game session between two players and stores round results.
 *
 * @author ayrinhaha
 */
public class GameSession {

    private Player p1;
    private Player p2;

    private List<GameResult> results = new ArrayList<>();

    public GameSession(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public GameResult playRound(int round) {

        int result = p1.getCurrentMove().compare(p2.getCurrentMove());

        String winner = "Tie";

        if (result == 1) {
            p1.incrementScore();
            winner = p1.getName();
        } else if (result == -1) {
            p2.incrementScore();
            winner = p2.getName();
        }

        GameResult gr = new GameResult(
                round,
                p1.getName(),
                p2.getName(),
                p1.getCurrentMove().getMoveName(),
                p2.getCurrentMove().getMoveName(),
                winner,
                p1.getScore(),
                p2.getScore());

        results.add(gr);
        return gr;
    }

    public List<GameResult> getResults() {
        return results;
    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }
}