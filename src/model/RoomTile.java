package model;

public class RoomTile extends Tile {

	private Room belongsTo;
	private boolean isWall;

	public RoomTile(int x, int y, boolean isRoomTile) {
		super(x, y, isRoomTile);
	}

	/**
	 * @return the belongsTo
	 */
	public Room getBelongsTo() {
		return belongsTo;
	}

	/**
	 * @param belongsTo
	 *            the belongsTo to set
	 */
	public void setBelongsTo(Room belongsTo) {
		this.belongsTo = belongsTo;
	}

	/**
	 * @return the isWall
	 */
	public boolean isWall() {
		return isWall;
	}

	/**
	 * @param isWall
	 *            the isWall to set
	 */
	public void setWall(boolean isWall) {
		this.isWall = isWall;
	}

}
