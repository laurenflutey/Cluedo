package model;

public class Suggestion {

	private Character character;
	private Weapon weapon;
	private Room room;

	public Suggestion(Character character, Weapon weapon, Room room) {
		this.character = character;
		this.weapon = weapon;
		this.room = room;
	}

	/**
	 * @return the character
	 */
	public Character getCharacter() {
		return character;
	}

	/**
	 * @return the weapon
	 */
	public Weapon getWeapon() {
		return weapon;
	}

	/**
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}

}
