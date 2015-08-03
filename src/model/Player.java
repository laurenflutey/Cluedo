package model;

public class Player {

	private Character character;

	public Player(Character character) {
		this.character = character;
	}

	/**
	 * @return the character
	 */
	public Character getCharacter() {
		return character;
	}

	public String getName() {
		return character.getName();
	}

}
