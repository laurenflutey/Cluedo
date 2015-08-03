package model;

/**
 * Class to represent a proposed move by a player
 *
 * @author Marcel
 */
public class Move {

    private int x, y;

    /**
     * Constructor
     *
     * Makes a move object which is used to check if a players proposed move is valid or not.
     *
     * @param x x position of move
     * @param y y position of move
     */
    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter
     *
     * @return Returns x position of move
     */
    public int getX() {
        return x;
    }

    /**
     * Getter
     *
     * @return Returns y position of move
     */
    public int getY() {
        return y;
    }

    /**
     * Getter
     *
     * @return Returns the actual move object
     */
    public Move getMove() {
        return this;
    }
}
