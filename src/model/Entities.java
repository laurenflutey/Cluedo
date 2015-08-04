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
	private List<Character> characters;

	/**
	 * Set of all weapons in the game, a random weapon will be selected on
	 * gameStart to be the specified murder weapon.
	 *
	 * @see Weapon
	 */
	private List<Weapon> weapons;

	/**
	 * Set of all rooms in the game. A random room will be selected as the room
	 * where the murder was carried out.
	 *
	 * @see Room
	 */
	private Map<String, Room> rooms;

	private List<Card> cards;

	private List<Player> players;

	private List<Character> finalCharacters;

	private Set<Card> winningCards;

	private Board board;

	public Entities() {
		init();
	}

	private void init() {

		characters = new ArrayList<Character>();
		finalCharacters = new ArrayList<Character>();
		weapons = new ArrayList<Weapon>();
		rooms = new HashMap<String, Room>();
		cards = new ArrayList<Card>();
		players = new ArrayList<>();
		winningCards = new HashSet<Card>();

		characters.add(new Character("Mrs Peacock", 'p', 23, 6));
		characters.add(new Character("Proffesor Plum", 'r', 23, 20));
		characters.add(new Character("Miss Scarlet", 's', 7, 25));
		characters.add(new Character("Colonel Mustard", 'm', 0, 18));
		characters.add(new Character("Mrs White", 'w', 9, 0));
		characters.add(new Character("Reverend Green", 'g', 14, 0));

		finalCharacters.addAll(characters);

		weapons.add(new Weapon("Candlestick", '!'));
		weapons.add(new Weapon("Dagger", '%'));
		weapons.add(new Weapon("Lead Pipe", '|'));
		weapons.add(new Weapon("Revolver", '&'));
		weapons.add(new Weapon("Rope", '#'));
		weapons.add(new Weapon("Spanner", '*'));

		Room kitchen = new Room("Kitchen", 1);
		Room study = new Room("Study", 2);
		Room lounge = new Room("Lounge", 3);
		Room conservatory = new Room("Conservatory", 4);
		Room ballRoom = new Room("Ball Room", 5);
		Room billiardRoom = new Room("Billiard Room", 6);
		Room library = new Room("Library", 7);
		Room hall = new Room("Hall", 8);
		Room diningRoom = new Room("Dining Room", 9);
		Room pool = new Room("Pool", 10);

		kitchen.setConnectingRoom(study);
		study.setConnectingRoom(kitchen);
		lounge.setConnectingRoom(conservatory);
		conservatory.setConnectingRoom(lounge);

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

		Collections.shuffle(cards);
		Collections.shuffle(weapons);

		board = new Board(24, 26, rooms);
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

	public List<Character> getCharacters() {
		return characters;
	}

	public List<Weapon> getWeapons() {
		return weapons;
	}

	public Map<String, Room> getRooms() {
		return rooms;
	}

	public List<Card> getCards() {
		return cards;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
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

	/**
	 * @return the winningCards
	 */
	public Set<Card> getWinningCards() {
		return winningCards;
	}

	/**
	 * @return the finalCharacters
	 */
	public List<Character> getFinalCharacters() {
		return finalCharacters;
	}

	/**
	 * @param finalCharacters
	 *            the finalCharacters to set
	 */
	public void setFinalCharacters(List<Character> finalCharacters) {
		this.finalCharacters = finalCharacters;
	}

}
