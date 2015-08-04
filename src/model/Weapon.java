package model;

/**
 * Class representing a weapon in the Cluedo game.
 *
 * @author Marcel van Workum
 * @author Reuben Puketapu
 */
public class Weapon {

	private String name;
	private char id;

	public Weapon(String name, char id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	/**
	 * @return the id
	 */
	public char getId() {
		return id;
	}

}
