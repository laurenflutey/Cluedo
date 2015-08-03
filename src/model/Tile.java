package model;

public class Tile {
	private char name;
	private boolean isRoomTile;
	private boolean isWallTile;
	private int x, y;

	private Player player;

	public Tile(int x, int y, boolean isRoomTile, boolean isWallTile, char name) {
		this.x = x;
		this.y = y;
		this.isRoomTile = isRoomTile;
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
	 * @return the isRoomTile
	 */
	public boolean isRoomTile() {
		return isRoomTile;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
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

}
