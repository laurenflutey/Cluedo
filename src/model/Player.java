package model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Marcel on 3/08/15.
 */
public class Player extends Character {

	private Character character;
	private Set<Card> cards;
	private int playerNumber;

	private boolean isCurrentPlayer = false;

	/**
	 * Constructor for Character class
	 *
	 * @param name
	 *            the name of the Character
	 * @param ch
	 * @param xOrigin
	 * @param yOrigin
	 */
	public Player(String name, char ch, int xOrigin, int yOrigin) {
		super(name, ch, xOrigin, yOrigin);
		this.cards = new HashSet<Card>();
	}

	public Character getCharacter() {
		return character;
	}

	public String getName() {
		return character.getName();
	}

	/**
	 * @return the cards
	 */
	public Set<Card> getCards() {
		return cards;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	/**
	 * @return the playerNumber
	 */
	public int getPlayerNumber() {
		return playerNumber;
	}

	/**
	 * @param playerNumber the playerNumber to set
	 */
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public boolean isCurrentPlayer() {
		return isCurrentPlayer;
	}

	public void setIsCurrentPlayer(boolean isCurrentPlayer) {
		this.isCurrentPlayer = isCurrentPlayer;
	}
}
