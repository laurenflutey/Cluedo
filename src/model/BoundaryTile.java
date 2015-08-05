package model;

/**
 * Class representing a boundary tile on the board. A boundary tile is a wall and is not able to be pathed through.
 *
 * @author Marcel
 * @author Reuben
 */
public class BoundaryTile extends Tile {
	

	/**
	 * Constructor
	 *
	 * @param x x position of the boundary tile
	 * @param y y position of the boundary tile
	 * @param room is the boundary tile associated with a room
	 * @param isWallTile is the boundary tile a wall
	 */
	public BoundaryTile(int x, int y, Room room, boolean isWallTile) {
		super(x, y, room, isWallTile, '0');
		super.isBoundary = true;
	}

}
