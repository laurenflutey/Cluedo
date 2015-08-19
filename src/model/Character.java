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
	 * @param name the name of the Character
	 */
	public Character(String name, char ch, int xOrigin, int yOrigin) {
		this.name = name;
		this.ch = ch;
		this.xPos = xOrigin;
		this.yPos = yOrigin;
	}

	/**
	 * Gets x pos.
	 *
	 * @return the x pos
     */
	public int getXPos() {
		return xPos;
	}

	/**
	 * Sets x pos.
	 *
	 * @param xPos the x pos
     */
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	/**
	 * Gets y pos.
	 *
	 * @return the y pos
     */
	public int getYPos() {
		return yPos;
	}

	/**
	 * Sets y pos.
	 *
	 * @param yPos the y pos
     */
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}

	/**
	 * Gets name.
	 *
	 * @return the name
     */
	public String getName() {
		return name;
	}

	/**
	 * Gets ch.
	 *
	 * @return the ch
     */
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
	 * @param isUsed the isUsed to set
	 */
	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
	
	@Override
	public String toString(){
		return name;
	}

}
