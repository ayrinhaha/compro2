package com.ayrinhaha.model;

/**
 * Paper move in the game.
 *
 * @author ayrinhaha
 */
public class Paper extends GameMove {

    public Paper() {
        super("Paper");
    }

    /**
     * Compares Paper with another move.
     */
    @Override
    public int compare(GameMove other) {
        if (other instanceof Paper)
            return 0;
        if (other instanceof Rock)
            return 1;
        return -1;
    }
}
