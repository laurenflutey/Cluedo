package controller;

import model.Board;

/**
 * Class to handle the movement of a player throughout the board
 *
 * @author Marcel van Workum
 */
public class MovementController {

    /**
     * Game board that the Movement controller will search on.
     */
    private final Board BOARD;

    /**
     * Constructor
     *
     * Assigns the board passed to be the board for the movement controller to parse
     *
     * @param board Board passed into the constructor
     */
    public MovementController(Board board) {
        this.BOARD = board;
    }



}
