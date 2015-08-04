package model;

public class Tile {
	private char name;
	private Room room;
	private boolean isWallTile;
	private boolean isDoor;
	private int x, y;

	private Player player;

	public Tile(int x, int y, Room room, boolean isWallTile, char name) {
		this.x = x;
		this.y = y;
		this.room = room;
		this.isWallTile = isWallTile;
		this.name = name;
	}
	
	public Tile(int x, int y, Room room, boolean isWallTile, boolean isDoor, char name) {
		this.x = x;
		this.y = y;
		this.room = room;
		this.isDoor = isDoor;
		this.isWallTile = isWallTile;
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	 * @return the name
	 */
	public char getName() {
		return name;
	}

	public void setName(char name) {
		this.name = name;
	}

	/**
	 * @return the Room
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Checks whether there is a player on the tile or not
	 *
	 * @return Is there a player currently on this tile
	 */
	public boolean isOccupied() {
		return player != null;
	}

	/**
	 * @param player
	 *            the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return the isWallTile
	 */
	public boolean isWallTile() {
		return isWallTile;
	}

	public boolean isRoomTile() {
		return room != null;
	}

	/**
	 * @return the isDoor
	 */
	public boolean isDoor() {
		return isDoor;
	}

}
