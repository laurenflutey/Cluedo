package model;

/**
 * Class to represent a card in the Cluedo game. A card represents a {@link Weapon}, {@link Character} or {@link Room}
 * and are used when a {@link Player} makes a suggestion or accusation.
 *
 * @author Marcel
 * @author Reuben
 */
public class Card {

	/**
	 * Name of the card
	 */
	private String name;

	/**
	 * A card may be of type weapon, character or room
	 */
	private String type;

	/**
	 * Constructor
	 *
	 * @param name Name of the card
	 * @param type Type of the card
	 */
	public Card(String name, String type) {
		this.name = name;
		this.setType(type);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	

}
