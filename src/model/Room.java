package model;

import java.util.ArrayList;

/**
 * Class representing a room in the Cluedo game. A room may have a connecting room which a player can take as a
 * shortcut to that room. The room also has a list of tiles and a list of doors in that room. The list of doors is
 * used for the pathing algorithm, and so a player can leave the room from any door.
 *
 * @author Marcel van Workum
 * @author Reuben Puketapu
 */
public class Room {

	private ArrayList<Tile> tiles;
	private ArrayList<Tile> doors;
	private Room connectingRoom;

	private String name;
	private int roomNumber;
	private char ID;

	/**
	 * Constructor
	 *
	 * @param name Name of the room
	 * @param number Number of the room
	 * @param ID ID of the room
	 */
	public Room(String name, int number, char ID) {
		this.name = name;
		this.roomNumber = number;
		this.doors = new ArrayList<>();
		this.tiles = new ArrayList<>();
		this.ID = ID;
	}

	/**
	 * Method to assign a weapon to a room, by iterating through all the tiles in the room and
	 * assigning the weapon to a empty tile.
	 *
	 * @param allTiles Tiles in the room
	 * @param weapon Weapon to assign
	 */
	public void addWeaponToAvailableTile(Tile[][] allTiles, Weapon weapon) {
		// Checks if the weapon is already on the same tile
		for (Tile tile : tiles) {
			if (tile.getWeapon() != null) {
				if (tile.getWeapon().equals(weapon)) {
					return;
				}
			}
		}

		// Removes any instance of the weapon on the board
		for (Tile[] tileCollection : allTiles) {
			for (Tile tile : tileCollection) {
				if (tile.getWeapon() != null) {
					if (tile.getWeapon().equals(weapon)) {
						tile.setWeapon(null);
					}
				}
			}
		}

		// Adds the tile to an available tile in the room
		for (Tile tile : tiles) {
			if (!tile.isDoor() && !tile.isWallTile() && tile.isRoomTile() && !tile.isOccupied()) {
				tile.setWeapon(weapon);
				break;
			}
		}
	}

	/**
	 * Method to assign a Player to a room, by iterating through all the tiles in the room and
	 * assigning the player to a empty tile.
	 *
	 * @param allTiles All the tiles on the board
	 * @param player Player to be assigned
	 */
	public void addPlayerToAvailableTile(Tile[][] allTiles, Player player) {
		// Checks if the weapon is already on the same tile
		for (Tile tile : tiles) {
			if (tile.getPlayer() != null) {
				if (tile.getPlayer().equals(player)) {
					return;
				}
			}
		}

		// Removes any instance of the weapon on the board
		for (Tile[] tileCollection : allTiles) {
			for (Tile tile : tileCollection) {
				if (tile.getPlayer() != null) {
					if (tile.getPlayer().equals(player)) {
						tile.setPlayer(null);
					}
				}
			}
		}

		// Adds the tile to an available tile in the room
		for (Tile tile : tiles) {
			if (!tile.isDoor() && !tile.isWallTile() && tile.isRoomTile() && !tile.isOccupied()) {
				tile.setPlayer(player);
				player.setXPos(tile.getX());
				player.setYPos(tile.getY());
				player.setRoom(tile.getRoom());
				break;
			}
		}
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
	 * @param connectingRoom the connectingRoom to se
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
	 * @return the roomNumber
	 */
	public int getRoomNumber() {
		return roomNumber;
	}

	/**
	 * Getter
	 *
	 * @return The ID of the Room
	 */
	public char getID() {
		return ID;
	}
	
	@Override
	public String toString(){
		return name;
	}

}
