package model;

/**
 * Class representing a room in the Cluedo game.
 *
 * @author Marcel van Workum
 * @author Reuben Puketapu
 */
public class Room {

	private String name;
	private Character character;
	private Weapon weapon;
	private Room connectingRoom;

	public Room(String name) {
		this.name = name;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public String getName() {
		return name;
	}

	public Room getConnectingRoom() {
		return connectingRoom;
	}

	public void setConnectingRoom(Room connectingRoom) {
		this.connectingRoom = connectingRoom;
	}

}
