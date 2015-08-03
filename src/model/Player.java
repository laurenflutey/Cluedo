package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Marcel on 3/08/15.
 */
public class Player extends Character {

	private Character character;
	private Room room;
	private Set<Card> cards;
	private int playerNumber;

	private List<Suggestion> suggestions;

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
		this.suggestions = new ArrayList<Suggestion>();
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
	 * @param playerNumber
	 *            the playerNumber to set
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

	public void printCards() {
		for (Card card : cards) {
			System.out.println(card.getName());
		}
	}

	/**
	 * @return the suggestions
	 */
	public List<Suggestion> getSuggestions() {
		return suggestions;
	}

	/**
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * @param room the room to set
	 */
	public void setRoom(Room room) {
		this.room = room;
	}
}
