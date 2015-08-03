package model;

/**
 * Created by Marcel on 3/08/15.
 */
public class Player extends Character{

	private Character character;

    /**
     * Constructor for Character class
     *
     * @param name    the name of the Character
     * @param ch
     * @param xOrigin
     * @param yOrigin
     */
    public Player(String name, char ch, int xOrigin, int yOrigin) {
        super(name, ch, xOrigin, yOrigin);
    }

    public Character getCharacter() {
		return character;
	}

	public String getName() {
		return character.getName();
	}

}
