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
	private ArrayList<RoomTile> tiles;
	private Room connectingRoom;

	public Room(String name) {
		this.name = name;
		this.tiles = new ArrayList<RoomTile>();
	}

	/**
	 * @return the tiles
	 */
	public ArrayList<RoomTile> getTiles() {
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
	 *            the connectingRoom to set
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

}
