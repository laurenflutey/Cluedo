package model;

import controller.GameController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class to represent a Player within the game. The player extends a
 * {@link java.lang.Character} and stores all x/y coordinate information in the character
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
	private Set<Card> successfulSuggestions;

	/**
	 * Suggestion that the player made, which can be considered as an
	 * accusation, as the player may only made one accusation per game.
	 */
	private Suggestion accusation;

	/**
	 * Boolean to hold whether this is the current player or not and is used in
	 * the {@link controller.GameController} to perform game logic
	 *
	 * @see GameController#doGame()
	 */
	private boolean isCurrentPlayer = false;

	/**
	 * Holds the state of the player. If a player makes an accusation that isn't
	 * correct, the will be removed from the game and considered as isAlive =
	 * false;
	 */
	private boolean isAlive = true;

	/**
	 * Constructor for Character class
	 *
	 * @param name the name of the Character
	 * @param ch Character representing the player
	 * @param xOrigin x position of the player
	 * @param yOrigin y position of the player
	 */
	public Player(String name, char ch, int xOrigin, int yOrigin) {
		super(name, ch, xOrigin, yOrigin);
		this.cards = new HashSet<>();
		this.successfulSuggestions = new HashSet<>();
	}

	/**
	 * Gets character.
	 *
	 * @return the character
     */
	public Character getCharacter() {
		return character;
	}

	/**
	 *
	 * @return
	 */
	public String getName() {
		return character.getName();
	}

	/**
	 * @return the cards
	 */
	public Set<Card> getCards() {
		return cards;
	}

	/**
	 * Sets the character associated with the player
	 *
	 * @param character Character to associate
	 */
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

	/**
	 * Is current player.
	 *
	 * @return the boolean
     */
	public boolean isCurrentPlayer() {
		return isCurrentPlayer;
	}

	/**
	 * Sets is current player.
	 *
	 * @param isCurrentPlayer the is current player
     */
	public void setIsCurrentPlayer(boolean isCurrentPlayer) {
		this.isCurrentPlayer = isCurrentPlayer;
	}

	/**
	 * @return the suggestions
	 */
	public Set<Card> getSuggestions() {
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

	/**
	 * Is in room.
	 *
	 * @return the boolean
     */
	public boolean isInRoom() {
		return this.room != null;
	}

	/**
	 * Contains card with name.
	 *
	 * @param name the name
	 * @return the boolean
     */
	public boolean containsCardWithName(String name) {
		for (Card card : cards) {
			if (card.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the isAlive
	 */
	public boolean isAlive() {
		return isAlive;
	}

	/**
	 * @param isAlive
	 *            the isAlive to set
	 */
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	/**
	 * Gets accusation.
	 *
	 * @return the accusation
     */
	public Suggestion getAccusation() {
		return accusation;
	}

	/**
	 * Sets accusation.
	 *
	 * @param accusation the accusation
     */
	public void setAccusation(Suggestion accusation) {
		this.accusation = accusation;
	}
}
