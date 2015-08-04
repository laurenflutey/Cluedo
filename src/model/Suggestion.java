package model;

public class Suggestion {

	private Player player;
	private Weapon weapon;
	private Room room;

	public Suggestion(Player player, Weapon weapon, Room room) {
		this.player = player;
		this.weapon = weapon;
		this.room = room;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
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
