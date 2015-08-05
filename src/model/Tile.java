package model;

/**
 * Class to represent a tile on the board. A Tile is parsed by the {@link Board#parseBoard(String)} method
 *
 * A tile can have a number of properties including if it is a room or wall tile, or if it is a door to a room.
 *
 * It can also have a {@link Player} or {@link Weapon} associated with it.
 *
 * @author Marcel
 * @author Reuben
 */
public class Tile {
	private char name;
	private Room room;
	private boolean isWallTile;
	private boolean isDoor;
	private int x, y;

	private Player player;

	private Weapon weapon;

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
		return player != null && weapon != null;
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

	/**
	 * @return the weapon
	 */
	public Weapon getWeapon() {
		return weapon;
	}

	/**
	 * @param weapon
	 *            the weapon to set
	 */
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

}
