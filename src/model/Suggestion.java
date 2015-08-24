package model;

/**
 * Class to represent a Suggestion that a player can make. The class can also represent an accusation as well.
 *
 * A suggestion consists of a {@link Room} where the murder was committed, a {@link Weapon} with which the murder
 * was committed, and a {@link Character} that committed the murder.
 *
 * @author Marcel
 * @author Reuben
 */
public class Suggestion {

	private Player player;
	private Weapon weapon;
	private Room room;

	/**
	 * Constructor
	 *
	 * @param player Player who committed the murder
	 * @param weapon Weapon with which the murder was committed
	 * @param room Room in which the murder was committed
	 */
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
