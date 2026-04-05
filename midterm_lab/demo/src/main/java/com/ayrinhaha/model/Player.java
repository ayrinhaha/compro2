package com.ayrinhaha.model;

/**
 * Represents a player in the game.
 *
 * @author ayrinhaha
 */
public class Player {

    private String name;
    private int score;
    private GameMove currentMove;

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public GameMove getCurrentMove() {
        return currentMove;
    }

    public void setCurrentMove(GameMove move) {
        this.currentMove = move;
    }

    public void incrementScore() {
        score++;
    }
}