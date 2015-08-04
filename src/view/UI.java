package view;

import model.*;
import model.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * MVC View class for interacting with the players in the Cluedo game.
 *
 * @author Marcel van Workum
 * @author Reuben Puketapu
 */
public class UI {

	/**
	 * Initial messaged displayed to players of the Cluedo game
	 */
	private String initMessage = "Welcome to Cluedo.\n" + "--------------------------------\n"
			+ "| A simple text based Java game.\n" + "| Created by: \n|\tReuben Puketapu\n|\tMarcel van Workum.\n"
			+ "--------------------------------\n\n";

	/**
	 * Input scanner used to handle all inputted information from the players
	 */
	private Scanner reader = new Scanner(System.in);

	private String singleCharRegex = "[a-zA-Z]";

	/**
	 * Constructor for the UI Class
	 */
	public UI() {
		System.out.println(initMessage);
	}

	/**
	 * Gets the user to input the number of players for the Cluedo game.
	 *
	 * Called by {@link controller.GameController} and stored in the
	 * {@link model} package.
	 *
	 * @return Number of players.
	 */
	public int getPlayerCount() {
		System.out.println("Enter the number of players (3-6):");
		String input;

		/* Continuously loops while valid input has not been found */
		while (true) {
			input = reader.nextLine();

			// Checks if number input is integer and between 3 - 6
			if (isValidInteger(input)) {
				break;
			}
			// Checks if number input is integer and between 3 - 6
			if (isValidInteger(input, 3, 6)) {
				break;
			}

			System.out.println("Please enter a valid integer between 3 - 6");
		}

		// Parses the valid number and returns to GameController
		return Integer.parseInt(input);
	}

	/**
	 * Helper method for {@link #getPlayerCount()}. Checks the validity of a
	 * users input to see if it is first a valid integer and then if it is
	 * between 3 - 6.
	 *
	 * @param input
	 *            input from user via System.In
	 *
	 * @return The result of the validity check
	 */
	private boolean isValidInteger(String input) {
		try {
			int valid = Integer.parseInt(input);
			return valid >= 3 && valid <= 6;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Gets the proposed move from the player in the form of an x y {@link Move}
	 * object
	 *
	 * @param player
	 *            Player making the move
	 * @return Returns the players move
	 */
	public Move getPlayerMove(Player player, int roll) {
		System.out.println(player.getName() + ", you rolled a " + roll + ". You're currently at x: "
				+ parseIntToCharacter(player.getxPos())
				+ " y: " + player.getyPos());

		System.out.println("\nPlease enter the x:y coordinate for your move (e.g F 12)");

		Move move = null;
		int x, y;

		// Continually loops until the player enters a valid move
		while (move == null) {
			if (reader.hasNext()) {
				String character = reader.next();

				// If the player entered a single character matching the single character regex
				if (Pattern.matches(singleCharRegex, character)) {

					// convert the players character to a valid int representing a x position on the board
					x = parseCharacterToInt(character);

					// Then parse the users y position on the board
					if (reader.hasNextInt()) {
						y = reader.nextInt();
						move = new Move(x, y);
					}
				}
			}
		}

		return move;
	}

	/**
	 * Method to parse the integer value from a char
	 *
	 * @param character String representing a single char
	 *
	 * @return int value representing the a-z grid value of the board
	 */
	private int parseCharacterToInt(String character) {
		character = character.toLowerCase();
		return (int)character.charAt(0) - 97;
	}

	/**
	 * Parses the int x position back to a char to be displayed to the user
	 *
	 * @param xPosition X position of the player on the board
	 *
	 * @return character value representing the players position on the board
	 */
	private char parseIntToCharacter(int xPosition) {
		return java.lang.Character.toUpperCase((char) (xPosition + 97));
	}

	/**
	 * Method to handle the creation of the list of {@link Player}. Allows a
	 * player to choose a unique character
	 *
	 * @param characters
	 *            List of {@link Character} available to choose
	 * @param playerCount
	 *            Number of players in the game
	 *
	 * @return List of player objects
	 */
	public List<Player> getPlayers(List<Character> characters, int playerCount) {

		doClearOutput();

		// List of player objects created
		List<Player> players = new ArrayList<>();

		/*
		 * Loops through playerCount number of times to create Player Objects
		 * for each player
		 */
		for (int i = 0; i < playerCount; i++) {

			System.out.println(
					"\nPlayer " + (i + 1) + ", please select a character:\n" + "------------------------------------");

			// Displays the list of all remaining characters that can be chosen
			for (int j = 0; j < characters.size(); j++) {
				System.out.println((j + 1) + ": " + characters.get(j).getName());
			}

			boolean found = false;
			int choice = -1;

			// Continually loop until a valid choice is found
			while (!found) {

				// Gets the input number from the user
				if (reader.hasNextInt()) {
					choice = reader.nextInt();
				}

				// Checks that the choice falls within the bounds of the
				// remaining list
				if (choice <= characters.size() && choice > 0) {
					// Gets the players chosen character
					Character character = characters.get(choice - 1);

					// Creates a new player object, sets it associated character
					// and adds to the players list
					Player player = new Player(character.getName(), character.getCh(), character.getxPos(),
							character.getyPos());

					player.setCharacter(character);
					// sets the player number to print to the board
					player.setPlayerNumber(i + 1);
					players.add(player);

					// finally removes the Character from the list of available
					// characters
					characters.remove(choice - 1);

					found = true;

					doClearOutput();
				}
			}
		}

		return players;
	}

	/**
	 * Helper method for {@link #getPlayerCount()}. Checks the validity of a
	 * users input to see if it is first a valid integer and then if it is
	 * between 3 - 6.
	 *
	 * @param input
	 *            input from user via System.In
	 *
	 * @return The result of the validity check
	 */
	private boolean isValidInteger(String input, int min, int max) {
		try {
			int valid = Integer.parseInt(input);
			return valid >= min && valid <= max;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Method to handle when a player wants to make a suggestion. The method
	 * gets the suggested character and the suggested weapon. It then create a
	 * {@link Suggestion} object which is stored in the {@link Player}.
	 *
	 * @param characters
	 *            List of characters in the game
	 * @param weapons
	 *            List of weapons in the game
	 * @param currentPlayer
	 *            The current player making the suggestion
	 *
	 * @return A Suggest that the player has picked
	 */
	public Suggestion getSuggestion(List<Character> characters, List<Weapon> weapons, Player currentPlayer) {
		// Set the character and weapon to null initially, but assign the room
		// to the current room
		Character suggestedCharacter = null;
		Weapon suggestedWeapon = null;
		Room suggestedRoom = currentPlayer.getRoom();

		// Displays the list of available characters that the user can select as
		// a suggestion
		System.out.println("Which character would you like to suggest: ");
		for (int i = 0; i < characters.size(); i++) {
			System.out.println((i + 1) + ": " + characters.get(i).getName());
		}

		// loop until the user selects a valid character to suggest
		while (suggestedCharacter == null) {
			if (reader.hasNextInt()) {
				suggestedCharacter = characters.get(reader.nextInt() - 1);
			}
		}

		// Displays the list of available weapons that the user can select as a
		// suggestion
		System.out.println("Which weapon would you like to suggest: ");
		for (int i = 0; i < weapons.size(); i++) {
			System.out.println((i + 1) + ": " + weapons.get(i).getName());
		}

		// loop until the user selects a valid weapon to suggest
		while (suggestedWeapon == null) {
			if (reader.hasNextInt()) {
				suggestedWeapon = weapons.get(reader.nextInt() - 1);
			}
		}

		// The suggestion is then return and stored in the Player
		return new Suggestion(suggestedCharacter, suggestedWeapon, suggestedRoom);

	}

	/**
	 * Delegate method to handle the parsing of a players proposed accusation.
	 * If a player incorrectly makes an accusation, they're removed from the
	 * game.
	 *
	 * @param players
	 *            List of characters in the game
	 * @param weapons
	 *            List of weapons in the game
	 * @param rooms
	 *            List of rooms in the game
	 *
	 * @return Returns the proposed accusation in the form of a Suggestion
	 *         object
	 */
	public Suggestion getAccusation(List<Character> players, List<Weapon> weapons, Map<String, Room> rooms) {
		// Sets the suggestion components to null initially
		Character suggestedCharacter = null;
		Weapon suggestedWeapon = null;
		Room suggestedRoom = null;

		// Display the list of characters that the user can accuse
		System.out.println("\nWhich character would you like to accuse: ");
		for (int i = 0; i < players.size(); i++) {
			System.out.println((i + 1) + ": " + players.get(i).getName());
		}

		// continually loops until the user selects a valid choice
		boolean found = false;
		while (!found) {
			if (reader.hasNextInt()) {
				suggestedCharacter = players.get(reader.nextInt() - 1);
				found = true;
			}
		}

		// Display the list of weapons that the user can use as the accused
		// murder weapon
		System.out.println("Which weapon would you like to accuse: ");
		for (int i = 0; i < weapons.size(); i++) {
			System.out.println((i + 1) + ": " + weapons.get(i).getName());
		}

		// Continually loops until a valid choice is chosen
		found = false;
		while (!found) {
			if (reader.hasNextInt()) {
				suggestedWeapon = weapons.get(reader.nextInt() - 1);
				found = true;
			}
		}

		// Displays all the rooms that the player can accuse as the murder room
		System.out.println("Which room would you like to accuse: ");
		for (int i = 0; i < rooms.size(); i++) {
			for (Entry<String, Room> room : rooms.entrySet()) {
				if (room.getValue().getRoomNumber() == i + 1) {
					System.out.println(room.getValue().getRoomNumber() + ": " + room.getKey());
				}
			}
		}

		// Continually loops until the user selects a valid room
		found = false;
		while (!found) {
			if (reader.hasNextInt()) {
				int index = reader.nextInt();
				for (Entry<String, Room> room : rooms.entrySet()) {
					if (room.getValue().getRoomNumber() == index) {
						suggestedRoom = room.getValue();
					}
				}
				found = true;
			}
		}

		// Finally returns the accusation back to the game controller, where it
		// will be parsed to be valid or not.
		return new Suggestion(suggestedCharacter, suggestedWeapon, suggestedRoom);

	}

	/**
	 * Method that displays the current options to the player and gets their
	 * choice for their turn
	 *
	 * @param currentPlayer
	 *            The player making the move
	 * @param BOARD
	 *            board that the player is playing on
	 *
	 * @return returns the player's choice for their turn
	 */
	public int getTurnOptions(Player currentPlayer, final Board BOARD) {
		// loop until choice is found
		while (true) {
			int choice;

			// the range of valid choice depends on whether a player is in a
			// room or not.
			int range = 3;

			// display options to the user
			System.out.println("Select a option using the number beside it.");
			System.out.println("-----------------------------\n");
			System.out.println("#: Enter '#' to display the key for the board");
			System.out.println("1: Roll dice and make a move.");
			System.out.println("2: Look at your collected information.");
			System.out.println("3: Make an accusation");

			// Checks if the player is currently in a room or not
			Tile currentTile = BOARD.getTiles()[currentPlayer.getxPos()][currentPlayer.getyPos()];
			if (currentTile.isRoomTile()) {
				System.out.println("4: Make an suggestion");
				// increments the range of valid choices so that a user can now
				// select 4
				range++;
				if (currentTile.getRoom().getConnectingRoom() != null) {
					range++;
					System.out.println("5: Take secret passage to connecting room: "
							+ currentTile.getRoom().getConnectingRoom().getName());
				}
			}

			// parse the users input
			if (reader.hasNextInt()) {
				choice = reader.nextInt();

				// Check to see if the input falls within a valid range and then
				// return it
				if (choice <= range && choice > 0) {
					return choice;
				}
			}

			// Parse the special case where the user enters a #
			if (reader.hasNext()) {
				char ch = reader.next().charAt(0);
				if (ch == '#') {
					return -10;
				}
			}
		}
	}

	/**
	 * Displays all the keys on the baord
	 * 
	 * @param entities
	 *            all the entities on the board to be displayed
	 */
	public void displayKeys(Entities entities) {

		System.out.println("Key displayer: ");
		System.out.println("---------------");
		System.out.println("Weapons: \n");
		System.out.println("---------------");
		for (Weapon weapon : entities.getWeapons()) {
			System.out.println("\u001B[33m" + weapon.getId() + " = " + weapon.getName() + "\u001B[0m");
		}
		System.out.println("\nCharacters: ");
		System.out.println("---------------\n");
		for (Player player : entities.getPlayers()) {
			if (player.isCurrentPlayer()) {
				System.out.println("\u001B[32m" + player.getPlayerNumber() + " = " + player.getName() + "\u001B[0m");
			} else {
				System.out.println("\u001B[31m" + player.getPlayerNumber() + " = " + player.getName() + "\u001B[0m");
			}
		}
		System.out.println("\nRooms: ");
		System.out.println("---------------\n");
		for (Entry<String, Room> room : entities.getRooms().entrySet()) {
			System.out.println(room.getValue().getID() + " = " + room.getValue().getName());
		}

		System.out.println("\n");

	}

	/**
	 * Displays all the cards in the players hand
	 *
	 * @param currentPlayer
	 *            Player to display their cards
	 */
	public void doDisplayInformation(Player currentPlayer) {

		doClearOutput();

		System.out.println("\nCurrent Cards\n-----------------\n");
		for (Card c : currentPlayer.getCards()) {
			System.out.println(c.getName());
		}
		System.out.println("\n");

		System.out.println("\nCorrectly Suggested Cards\n-----------------\n");
		for (Card c : currentPlayer.getSuggestions()) {
			System.out.println(c.getName());
		}
		System.out.println("\n");
	}

	/**
	 * Simple method that simulates the output of the console being cleared by
	 * pushing the previous content way up by printing 50 new lines
	 */
	public void doClearOutput() {
		for (int i = 0; i < 50; ++i)
			System.out.println();
	}

	/**
	 * Method to handle the end game display
	 *
	 * @param winningPlayer The Player who won the game
	 */
	public void doEndGame(Player winningPlayer) {
		System.out.println("Congratulations " + winningPlayer.getName() + ", you accused correctly");
		System.out.println("You guessed : ");
		System.out.println("\t" + winningPlayer.getAccusation().getCharacter().getName());
		System.out.println("\t" + winningPlayer.getAccusation().getRoom().getName());
		System.out.println("\t" + winningPlayer.getAccusation().getWeapon().getName());
	}
}
