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
	private boolean isConnection;
	private boolean isDoor;
	private int x, y;
	private Player player;
	private Weapon weapon;

	/**
	 * Protected so that is accessible from the {@link BoundaryTile}
	 */
	protected boolean isBoundary;

	/**
	 * Constructor
	 *
	 * @param x x position of the tile
	 * @param y y position of the tile
	 * @param room The room the tile is associated with
	 * @param isWallTile Is the tile a wall
	 * @param name The name of the tile?
	 */
	public Tile(int x, int y, Room room, boolean isWallTile, char name) {
		this.x = x;
		this.y = y;
		this.room = room;
		this.isWallTile = isWallTile;
		this.name = name;
		this.isBoundary = false;
	}

	/**
	 * Alternate constructor
	 *
	 * @param x x position of the tile
	 * @param y y position of the tile
	 * @param room The room the tile is associated with
	 * @param isWallTile Is the tile a wall
	 * @param isDoor Is the tile a door
	 * @param name The name of the tile?
	 */
	public Tile(int x, int y, Room room, boolean isWallTile, boolean isDoor, char name) {
		this.x = x;
		this.y = y;
		this.room = room;
		this.isDoor = isDoor;
		this.isWallTile = isWallTile;
		this.name = name;
	}

	/**
	 * Gets x.
	 *
	 * @return the x
     */
	public int getX() {
		return x;
	}

	/**
	 * Gets y.
	 *
	 * @return the y
     */
	public int getY() {
		return y;
	}

	/**
	 * Gets name.
	 *
	 * @return the name
     */
	public char getName() {
		return name;
	}

	/**
	 * Sets name.
	 *
	 * @param name the name
     */
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
		return player != null || weapon != null;
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

	/**
	 * Is room tile.
	 *
	 * @return the boolean
     */
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

	/**
	 * @return the isBoundary
	 */
	public boolean isBoundary() {
		return isBoundary;
	}

	/**
	 * @return the isConnection
	 */
	public boolean isConnection() {
		return isConnection;
	}

	/**
	 * @param isConnection the isConnection to set
	 */
	public void setConnection(boolean isConnection) {
		this.isConnection = isConnection;
	}


}
