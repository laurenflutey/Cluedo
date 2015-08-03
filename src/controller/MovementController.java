package controller;

import model.*;

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

    private final Tile[][] TILES;

    /**
     * Constructor
     *
     * Assigns the board passed to be the board for the movement controller to parse
     *
     * @param board Board passed into the constructor
     */
    public MovementController(Board board) {
        this.BOARD = board;
        this.TILES = BOARD.getTiles();
    }

    /**
     * Recursive handler for the path searching algorithm that checks if a players proposed move is valid or not
     *
     * @param move Proposed {@link Move} by the player
     * @return Is the move valid?
     */
    public boolean isValidMove(Move move, Player player, int roll) {
        if (isValidDistance(move, player, roll)) {
            return pathSearch(move, TILES[player.getxPos()][player.getyPos()], roll);
        } else {
            return false;
        }
    }

    /**
     * Checks whether it is possible for a {@link Tile} to be reached with a given roll
     *
     * @param move Move the player is trying to make
     * @param player Player that is trying to make the move
     * @param roll Roll that the player has made
     *
     * @return Is the coordinate within a valid distance.
     */
    private boolean isValidDistance(Move move, Player player, int roll) {
        int changeX = Math.abs(move.getX() - player.getxPos());
        int changeY = Math.abs(move.getY() - player.getyPos());

        if ((changeX + changeY) <= roll) {
            return true;
        }
        return false;
    }

    /**
     * Recursive path searching algorithm to check whether there is a valid path to the players proposed move.
     *
     * @param target Target Tile (x:y) coordinates
     * @param currentTile The current {@link Tile} that that the search is at
     * @param remaining The remaining number of tile moves available from the roll
     *
     * @return Is there a valid path to the specified {@link Move}
     */
    private boolean pathSearch(Move target, Tile currentTile, int remaining) {
        if (currentTile.isRoomTile()){
            if (currentTile.isWallTile()) return false;
        }
        if (target.getX() == currentTile.getX() && target.getY() == currentTile.getY()) return true;
        if (remaining == 0) return false;

        int currentX = currentTile.getX();
        int currentY = currentTile.getY();

        if (currentX > 1) if(pathSearch(target, TILES[currentX - 1][currentY], remaining - 1)) return true;
        if (currentY > 1) if(pathSearch(target, TILES[currentX][currentY - 1], remaining - 1)) return true;
        if (currentX < 25) if(pathSearch(target, TILES[currentX + 1][currentY], remaining - 1)) return true;
        if (currentY < 25) if(pathSearch(target, TILES[currentX][currentY + 1], remaining - 1)) return true;

        return false;
    }

}
