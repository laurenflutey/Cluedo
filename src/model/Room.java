package model;

import java.util.ArrayList;

/**
 * Class representing a room in the Cluedo game.
 *
 * @author Marcel van Workum
 * @author Reuben Puketapu
 */
public class Room {

	private String name;
	private ArrayList<Tile> tiles;
	private Room connectingRoom;
	private int roomNumber;
	private char ID;

	private ArrayList<Tile> doors;

	public Room(String name, int number, char ID) {
		this.name = name;
		this.roomNumber = number;
		this.doors = new ArrayList<Tile>();
		this.tiles = new ArrayList<Tile>();
	}

	/**
	 * @return the tiles
	 */
	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	/**
	 * @return the connectingRoom
	 */
	public Room getConnectingRoom() {
		return connectingRoom;
	}

	/**
	 * @param connectingRoom
	 *            the connectingRoom to se
	 */
	public void setConnectingRoom(Room connectingRoom) {
		this.connectingRoom = connectingRoom;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the doors
	 */
	public ArrayList<Tile> getDoors() {
		return doors;
	}

	/**
	 * @param doors
	 *            the doors to set
	 */
	public void setDoors(ArrayList<Tile> doors) {
		this.doors = doors;
	}

	/**
	 * @return the roomNumber
	 */
	public int getRoomNumber() {
		return roomNumber;
	}

	/**
	 * @param roomNumber
	 *            the roomNumber to set
	 */
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public void addWeaponToAvailableTile(Weapon weapon) {
		for (Tile tile : tiles) {
			if (!tile.isDoor() && !tile.isWallTile() && tile.isRoomTile()) {
				tile.setWeapon(weapon);
				break;
			}
		}
	}

	public void addCharacterToAvailableTile(Character character) {

	}

	public char getID() {
		return ID;
	}

}
