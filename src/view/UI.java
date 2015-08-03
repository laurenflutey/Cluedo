package view;

import model.*;
import model.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	 * Gets the proposed move from the player in the form of an x y {@link Move} object
	 *
	 * @param player Player making the move
	 * @return Returns the players move
	 */
    public Move getPlayerMove(Player player) {
        System.out.println("It's your turn now " + player.getName());
        System.out.println("Please enter the x:y coordinate for your move (e.g 10 12)");

        Move move = null;
        int x, y;

        while (move == null) {
            if (reader.hasNextInt()) {
                x = reader.nextInt();
                if (reader.hasNextInt()) {
                    y = reader.nextInt();
                    move = new Move(x, y);
                }
            }
        }

        return move;
    }

	/**
	 * Method to handle the creation of the list of {@link Player}. Allows a player to choose a unique character
	 *
	 * @param characters List of {@link Character} available to choose
	 * @param playerCount Number of players in the game
	 *
	 * @return List of player objects
	 */
	public List<Player> getPlayers(List<Character> characters, int playerCount) {

		// List of player objects created
		List<Player> players = new ArrayList<>();

		/* Loops through playerCount number of times to create Player Objects for each player */
		for (int i = 0; i < playerCount; i++) {
			System.out.println("Player " + (i+1) + ", please select a character:");

			// Displays the list of all remaining characters that can be chosen
			for (int j = 0; j < characters.size(); j++) {
				System.out.println((j+1) + ": " + characters.get(j).getName());
			}

			boolean found = false;
			int choice = -1;

			// Continually loop until a valid choice is found
			while (!found) {

				// Gets the input number from the user
				if (reader.hasNextInt()) {
					choice = reader.nextInt();
				}

				// Checks that the choice falls within the bounds of the remaining list
				if (choice <= characters.size() && choice > 0) {
					// Gets the players chosen character
					Character character = characters.get(choice - 1);

					//Creates a new player object, sets it associated character and adds to the players list
					Player player = new Player(character.getName(), character.getCh(),
							character.getxPos(), character.getyPos());

					player.setCharacter(character);
					// sets the player number to print to the board
					player.setPlayerNumber(i + 1);
					players.add(player);

					// finally removes the Character from the list of available characters
					characters.remove(choice - 1);

					found = true;
				}
			}
		}

		return players;
	}

    /**
     * Helper method for {@link #getPlayerCount()}. Checks the validity of a users input to see
     * if it is first a valid integer and then if it is between 3 - 6.
     *
     * @param input input from user via System.In
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
	 * Method that displays the current options to the player and gets their choice for their turn
	 *
	 * @param currentPlayer The player making the move
	 * @param BOARD board that the player is playing on
	 *
	 * @return returns the player's choice for their turn
	 */
	public int getTurnOptions(Player currentPlayer, final Board BOARD) {
		while (true) {
			int choice;
			int range = 3;
			System.out.println("1: Roll dice and make a move.");
			System.out.println("2: Look at your collected information.");
			System.out.println("3: Make an accusation");
			Tile currentTile = BOARD.getTiles()[currentPlayer.getxPos()][currentPlayer.getyPos()];
			if (currentTile.isRoomTile()) {
				System.out.println("4: Make an suggestion");
				range++;
				if (currentTile.getRoom().getConnectingRoom() != null) {
					range++;
					System.out.println("5: Take secret passage to connection room: " +
							currentTile.getRoom().getConnectingRoom().getName());
				}
			}
			if (reader.hasNextInt()) {
				choice = reader.nextInt();
				if (choice <= range && choice > 0) {
					return choice;
				}
			}
		}
	}
}
