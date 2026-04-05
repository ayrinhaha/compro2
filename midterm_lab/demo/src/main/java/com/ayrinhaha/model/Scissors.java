package com.ayrinhaha.model;

/**
 * Scissors move in the game.
 *
 * @author ayrinhaha
 */
public class Scissors extends GameMove {

    public Scissors() {
        super("Scissors");
    }

    @Override
    public int compare(GameMove other) {
        if (other instanceof Scissors) return 0;
        if (other instanceof Paper) return 1;
        return -1;
    }
}