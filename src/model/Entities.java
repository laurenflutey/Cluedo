package model;

import java.util.HashSet;
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
	private Set<Character> characters;

	/**
	 * Set of all weapons in the game, a random weapon will be selected on
	 * gameStart to be the specified murder weapon.
	 *
	 * @see Weapon
	 */
	private Set<Weapon> weapons;

	/**
	 * Set of all rooms in the game. A random room will be selected as the room
	 * where the murder was carried out.
	 *
	 * @see Room
	 */
	private Set<Room> rooms;

	private Set<Card> cards;

	private Set<Player> players;

	private Board board;

	public Entities() {
		init();
	}

	private void init() {

		board = new Board(26, 26);

		characters = new HashSet<Character>();
		weapons = new HashSet<Weapon>();
		rooms = new HashSet<Room>();
		cards = new HashSet<Card>();
		players = new HashSet<Player>();

		characters.add(new Character("Mrs Peacock", 'p', 6, 25));
		characters.add(new Character("Proffesor Plum", 'l', 25, 20));
		characters.add(new Character("Miss Scarlet", 's', 7, 25));
		characters.add(new Character("Colonel Mustard", 'm', 0, 18));
		characters.add(new Character("Mrs White", 'w', 9, 0));
		characters.add(new Character("Reverend Green", 'g', 14, 0));

		weapons.add(new Weapon("Candlestick"));
		weapons.add(new Weapon("Dagger"));
		weapons.add(new Weapon("Lead Pipe"));
		weapons.add(new Weapon("Revolver"));
		weapons.add(new Weapon("Rope"));
		weapons.add(new Weapon("Spanner"));

		Room kitchen = new Room("Kitchen");
		Room study = new Room("Study");
		Room lounge = new Room("Lounge");
		Room conservatory = new Room("Conservatory");
		kitchen.setConnectingRoom(study);
		study.setConnectingRoom(kitchen);
		lounge.setConnectingRoom(conservatory);
		conservatory.setConnectingRoom(lounge);

		rooms.add(conservatory);
		rooms.add(lounge);
		rooms.add(kitchen);
		rooms.add(study);
		rooms.add(new Room("Ball Room"));
		rooms.add(new Room("Billiard Room"));
		rooms.add(new Room("Library"));
		rooms.add(new Room("Hall"));
		rooms.add(new Room("Dining Room"));

		cards.add(new Card("Mrs Peacock", "Character"));
		cards.add(new Card("Mrs White", "Character"));
		cards.add(new Card("Miss Scarlet", "Character"));
		cards.add(new Card("Proffesor Plum", "Character"));
		cards.add(new Card("Reverend Green", "Character"));
		cards.add(new Card("Colonel Mustard", "Character"));

		cards.add(new Card("Candlestick", "Weapon"));
		cards.add(new Card("Dagger", "Weapon"));
		cards.add(new Card("Lead Pipe", "Weapon"));
		cards.add(new Card("Revolver", "Weapon"));
		cards.add(new Card("Rope", "Weapon"));
		cards.add(new Card("Spanner", "Weapon"));

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

	public Set<Character> getCharacters() {
		return characters;
	}

	public Set<Weapon> getWeapons() {
		return weapons;
	}

	public Set<Room> getRooms() {
		return rooms;
	}

	public Set<Card> getCards() {
		return cards;
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	/**
	 * Getter
	 *
	 * @return Gets the board
	 */
	public Board getBoard() {
		return board;
	}
}
