package model;

import controller.GameController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class to represent a Player within the game. The player extends a
 * {@link Character} and stores all x/y coordinate information in the character
 * super class.
 *
 * @author Marcel
 * @author Reuben
 */
public class Player extends Character {

	/**
	 * Character associated with the Player. This is chosen by the user when the
	 * game is starting using {@link view.UI#getPlayers(List, int)}
	 */
	private Character character;

	/**
	 * Holds information of the players location and whether they're in a room
	 * or not. This affects the players ability to make suggestions or use
	 * secret passages to other rooms.
	 */
	private Room room;

	/**
	 * Collection of Cards that the player current has in their hand. A card
	 * represents either a {@link Weapon}, {@link Room} or {@link Character} and
	 * allows the user to make a guess at the correct solution to the murder.
	 */
	private Set<Card> cards;

	/**
	 * Player number which is used to display the player on the board, with
	 * their corresponding number
	 *
	 * @see Board#printBoard()
	 */
	private int playerNumber;

	/**
	 * A collection of suggestions that the player has made whilst in a room
	 */
	private List<Card> successfulSuggestions;

	/**
	 * Boolean to hold whether this is the current player or not and is used in
	 * the {@link controller.GameController} to perform game logic
	 *
	 * @see GameController#doGame()
	 */
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
		this.successfulSuggestions = new ArrayList<Card>();
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
	public List<Card> getSuggestions() {
		return successfulSuggestions;
	}

	/**
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * @param room
	 *            the room to set
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

	public boolean containsCardWithName(String name) {
		for (Card card : cards) {
			if (card.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
}
