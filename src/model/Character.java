package model;

/**
 * Class representing a character within the Cluedo game.
 *
 * @author Marcel van Workum
 * @author Reuben Puketapu
 */
public class Character {

	private String name;

	private char ch;

	private int xPos;
	private int yPos;

	private boolean isUsed;

	/**
	 * Constructor for Character class
	 * 
	 * @param name
	 *            the name of the Character
	 */
	public Character(String name, char ch, int xOrigin, int yOrigin) {
		this.name = name;
		this.ch = ch;
		this.xPos = xOrigin;
		this.yPos = yOrigin;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public String getName() {
		return name;
	}

	public char getCh() {
		return ch;
	}

	/**
	 * @return the isUsed
	 */
	public boolean isUsed() {
		return isUsed;
	}

	/**
	 * @param isUsed
	 *            the isUsed to set
	 */
	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

}
