package model;

/**
 * Class representing a character within the Cluedo game.
 *
 * @author Marcel van Workum
 * @author Reuben Puketapu
 */
public class Character {
	
	private String name;
	
	private int xPos;
	private int yPos;
	
	/**
	 * Constructor for Character class
	 * @param name the name of the Character
	 */
	public Character(String name, int xOrigin, int yOrigin){
		this.name = name;
		this.xPos = xOrigin;
		this.yPos = yOrigin;
	}
	
	
}
