package model;

public class BoundaryTile extends Tile {

	public BoundaryTile(int x, int y, Room room, boolean isWallTile) {
		super(x, y, room, isWallTile, '0');
	}

}
