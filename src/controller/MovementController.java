package controller;

import model.*;

import java.util.ArrayList;

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
     * 2D Array of tiles uses to represent the board
     */
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
        if (player.isInRoom()) {
            Room room = player.getRoom();
            ArrayList<Tile> exits = room.getDoors();
            for (Tile exit : exits) {
                if (isValidDistance(move, exit, roll)) {
                    if (!isTileOccupied(move)) {
                        if(pathSearch(move, exit, roll)) return true;
                    }
                }
            }
            return false;
        } else {
            if (isValidDistance(move, TILES[player.getxPos()][player.getyPos()], roll)) {
                if (!isTileOccupied(move)) {
                    return pathSearch(move, TILES[player.getxPos()][player.getyPos()], roll);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Method to get whether the current tile is occupied or not by a player
     *
     * @param target Target location for the move
     *
     * @return Is the tile occupied by a player or not
     */
    private boolean isTileOccupied(Move target) {
        if (target.getX() < 24 && target.getX() > 0 && target.getY() < 25 && target.getY() > 0) {
            return TILES[target.getX()][target.getY()].isOccupied();
        }
        return false;
    }

    /**
     * Checks whether it is possible for a {@link Tile} to be reached with a given roll
     *
     * @param move Move the player is trying to make
     * @param playerTile Player that is trying to make the move
     * @param roll Roll that the player has made
     *
     * @return Is the coordinate within a valid distance.
     */
    private boolean isValidDistance(Move move, Tile playerTile, int roll) {
        int changeX = Math.abs(move.getX() - playerTile.getX());
        int changeY = Math.abs(move.getY() - playerTile.getY());

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
        // checks if the current tile is a room tile and if its a wall, this path is invalid
        if (currentTile.isRoomTile()){
            if (currentTile.isWallTile()) return false;
        }

        // cause where the pathing algorithm has actually found a valid path
        if (target.getX() == currentTile.getX() && target.getY() == currentTile.getY()) {
            return true;
        }

        // if the remaining number of steps has been reached but a valid path was not found
        if (remaining == 0) return false;

        int currentX = currentTile.getX();
        int currentY = currentTile.getY();

        // Recursively search for a valid path in all directions
        if (currentX > 1) if(pathSearch(target, TILES[currentX - 1][currentY], remaining - 1)) return true;
        if (currentY > 1) if(pathSearch(target, TILES[currentX][currentY - 1], remaining - 1)) return true;
        if (currentX < 23) if(pathSearch(target, TILES[currentX + 1][currentY], remaining - 1)) return true;
        if (currentY < 24) if(pathSearch(target, TILES[currentX][currentY + 1], remaining - 1)) return true;

        return false;
    }

}
