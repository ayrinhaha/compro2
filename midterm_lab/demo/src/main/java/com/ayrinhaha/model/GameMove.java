package com.ayrinhaha.model;

/**
 * Base class for a game move.
 * Defines name and comparison logic.
 *
 * @author ayrinhaha
 */
public abstract class GameMove {

    private String moveName;

    /**
     * Creates a move with a name.
     */
    public GameMove(String moveName) {
        this.moveName = moveName;
    }

    /**
     * Gets move name.
     */
    public String getMoveName() {
        return moveName;
    }

    /**
     * Compares this move to another.
     * Returns win/draw/lose result.
     */
    public abstract int compare(GameMove other);
}