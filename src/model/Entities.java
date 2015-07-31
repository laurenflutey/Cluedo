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

	private Set<Character> characters;
	private Set<Weapon> weapons;
	private Set<Room> rooms;

	public Entities() {
		init();
	}

	private void init() {

		characters = new HashSet<Character>();
		weapons = new HashSet<Weapon>();
		rooms = new HashSet<Room>();

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

	}

}
