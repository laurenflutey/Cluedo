package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class to store the collections of entities within the Cluedo game.
 *
 * @author Marcel van Workum
 * @author Reuben Puketapu
 */
public class Entities {

	/**
	 * Set of all characters in the game, a random character will be selected to
	 * be the murder
	 *
	 * @see Character
	 */
	private List<Character> characters = new ArrayList<>();

	/**
	 * Set of all weapons in the game, a random weapon will be selected on
	 * gameStart to be the specified murder weapon.
	 *
	 * @see Weapon
	 */
	private List<Weapon> weapons = new ArrayList<>();

	/**
	 * Set of all rooms in the game. A random room will be selected as the room
	 * where the murder was carried out.
	 *
	 * @see Room
	 */
	private Map<String, Room> rooms = new HashMap<>();

	/**
	 * List of cards in the game. A card represents a Weapon, Room or Character
	 */
	private List<Card> cards = new ArrayList<>();

	/**
	 * List of players in the game. A player is associated with a {@link Character}
	 */
	private List<Player> players = new ArrayList<>();

	/**
	 * A complete list of the players that never changes.
	 */
	private List<Player> finalPlayers = new ArrayList<>();

	/**
	 * The solution to the game
	 */
	private Set<Card> winningCards = new HashSet<>();

	/**
	 * The cluedo game board
	 */
	private Board board;

	/**
	 * Constructor
	 */
	public Entities() {
		init();
	}

	/**
	 * Method to initialise all the data structures for the entities.
	 */
	private void init() {
		// initialises character
		initCharacters();

		// initialises weapons
		initWeapons();

		// initialises rooms
		initRooms();

		// initialises cards
		initCards();

		// shuffle the cards and weapons
		Collections.shuffle(cards);
		Collections.shuffle(weapons);

		// finally create the game board
		board = new Board(24, 26, rooms);
	}

	/**
	 * Init method
	 *
	 * Assigns all of the cards to the collection of cards
	 */
	private void initCards() {
		// Characters
		cards.add(new Card("Mrs Peacock", "Character"));
		cards.add(new Card("Mrs White", "Character"));
		cards.add(new Card("Miss Scarlet", "Character"));
		cards.add(new Card("Proffesor Plum", "Character"));
		cards.add(new Card("Reverend Green", "Character"));
		cards.add(new Card("Colonel Mustard", "Character"));

		// Weapons
		cards.add(new Card("Candlestick", "Weapon"));
		cards.add(new Card("Dagger", "Weapon"));
		cards.add(new Card("Lead Pipe", "Weapon"));
		cards.add(new Card("Revolver", "Weapon"));
		cards.add(new Card("Rope", "Weapon"));
		cards.add(new Card("Spanner", "Weapon"));

		// Rooms
		cards.add(new Card("Kitchen", "Room"));
		cards.add(new Card("Study", "Room"));
		cards.add(new Card("Lounge", "Room"));
		cards.add(new Card("Conservatory", "Room"));
		cards.add(new Card("Ball Room", "Room"));
		cards.add(new Card("Billiard Room", "Room"));
		cards.add(new Card("Library", "Room"));
		cards.add(new Card("Hall", "Room"));
		cards.add(new Card("Dining Room", "Room"));
	}

	/**
	 * Init method
	 *
	 * Creates the rooms, sets the connected rooms and then stores the rooms in the collection of rooms
	 */
	private void initRooms() {
		// Create rooms
		Room kitchen = new Room("Kitchen", 1, 'K');
		Room study = new Room("Study", 2, 'S');
		Room lounge = new Room("Lounge", 3, 'L');
		Room conservatory = new Room("Conservatory", 4, 'C');
		Room ballRoom = new Room("Ball Room", 5, 'B');
		Room billiardRoom = new Room("Billiard Room", 6, 'I');
		Room library = new Room("Library", 7, 'Y');
		Room hall = new Room("Hall", 8, 'H');
		Room diningRoom = new Room("Dining Room", 9, 'D');
		Room pool = new Room("Pool", 10, 'X');

		// sets connected rooms
		kitchen.setConnectingRoom(study);
		study.setConnectingRoom(kitchen);
		lounge.setConnectingRoom(conservatory);
		conservatory.setConnectingRoom(lounge);

		// store in data structure
		rooms.put(conservatory.getName(), conservatory);
		rooms.put(lounge.getName(), lounge);
		rooms.put(kitchen.getName(), kitchen);
		rooms.put(study.getName(), study);
		rooms.put(hall.getName(), hall);
		rooms.put(ballRoom.getName(), ballRoom);
		rooms.put(billiardRoom.getName(), billiardRoom);
		rooms.put(library.getName(), library);
		rooms.put(diningRoom.getName(), diningRoom);
		rooms.put(pool.getName(), pool);
	}

	/**
	 * Init method
	 *
	 * Creates the weapons and adds them to the weapons collection
	 */
	private void initWeapons() {
		weapons.add(new Weapon("Candlestick", '!'));
		weapons.add(new Weapon("Dagger", '%'));
		weapons.add(new Weapon("Lead Pipe", '?'));
		weapons.add(new Weapon("Revolver", '&'));
		weapons.add(new Weapon("Rope", '#'));
		weapons.add(new Weapon("Spanner", '*'));
	}

	/**
	 * Init method
	 *
	 * Creates the characters and adds them to the characters collection
	 */
	private void initCharacters() {
		characters.add(new Character("Mrs Peacock", 'p', 23, 6));
		characters.add(new Character("Proffesor Plum", 'r', 23, 20));
		characters.add(new Character("Miss Scarlet", 's', 7, 25));
		characters.add(new Character("Colonel Mustard", 'm', 0, 18));
		characters.add(new Character("Mrs White", 'w', 9, 0));
		characters.add(new Character("Reverend Green", 'g', 14, 0));
	}

	/**
	 * Getter
	 *
	 * @param index
	 *            index of player to get from players list
	 *
	 * @return {@link Player}
	 */
	public Player getPlayer(int index) {
		return players.get(index);
	}

	/**
	 * Gets characters.
	 *
	 * @return the characters
     */
	public List<Character> getCharacters() {
		return characters;
	}

	/**
	 * Gets weapons.
	 *
	 * @return the weapons
     */
	public List<Weapon> getWeapons() {
		return weapons;
	}

	/**
	 * Gets rooms.
	 *
	 * @return the rooms
     */
	public Map<String, Room> getRooms() {
		return rooms;
	}

	/**
	 * Gets cards.
	 *
	 * @return the cards
     */
	public List<Card> getCards() {
		return cards;
	}

	/**
	 * Gets players.
	 *
	 * @return the players
     */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Sets players.
	 *
	 * @param players the players
     */
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	/**
	 * Getter
	 *
	 * @return Gets the board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * @return the winningCards
	 */
	public Set<Card> getWinningCards() {
		return winningCards;
	}

	/**
	 * @return the finalCharacters
	 */
	public List<Player> getFinalPlayers() {
		return finalPlayers;
	}

	/**
	 * @param finalPlayers List of final Players in the game
	 */
	public void setFinalPlayers(List<Player> finalPlayers) {
		this.finalPlayers = finalPlayers;
	}


}
