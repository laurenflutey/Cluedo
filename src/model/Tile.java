package model;

public class Tile {
	private char name;
	private boolean isRoomTile;
	private int x, y;

	public Tile(int x, int y, boolean isRoomTile) {
		this.x = x;
		this.y = y;
		this.isRoomTile = isRoomTile;
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

}
