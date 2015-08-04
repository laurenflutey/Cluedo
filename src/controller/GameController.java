package controller;

import model.*;
import view.UI;

import java.util.*;
import java.util.Map.Entry;

/**
 * MVC Controller class to handle the logic of the Cluedo game.
 *
 * @author Marcel van Workum
 * @author Reuben Puketapu
 */
public class GameController {

	/**
	 * UI Class used to interact with the players in the game
	 */
	private final UI UI;

	/**
	 * Collection of entities within the game {@link model.Entities}
	 */
	private final Entities ENTITIES;

	/**
	 * Board that the game is played on
	 */
	private final Board BOARD;

	/**
	 * Movement controller used to handle all of the players movement throughout
	 * the board.
	 *
	 * Movement is controlled by a recursive brute force(maybe greedy?)
	 * algorithm
	 */
	private final MovementController MOVEMENT_CONTROLLER;

	/**
	 * 2D array of Tile objects, representing the board
	 */
	private Tile[][] tiles;

	/**
	 * Number of players in the Cluedo game. Must be between 3 - 6. Initialised
	 * in {@link #GameController()}
	 */
	private int playerCount;
	private boolean isGameOver;

	/**
	 * Constructor for the {@link GameController} class
	 */
	public GameController() {
		this.UI = new UI();
		this.ENTITIES = new Entities();
		this.BOARD = ENTITIES.getBoard();

		tiles = ENTITIES.getBoard().getTiles();

		/* Assign board to movement controller */
		this.MOVEMENT_CONTROLLER = new MovementController(BOARD);
	}

	/**
	 * Performs all of the initialisation of the game.
	 *
	 * @see UI
	 * @see Entities
	 */
	public void initGame() {
		// Delegates player count parsing to UI class
		playerCount = UI.getPlayerCount();

		// Initialises all the players and sets the game state
		initPlayers();
		isGameOver = false;

		/* Creates a final game solution, deals the cards to the players,
		   and distributes the weapons throughout the board */
		chooseSolutionCards();
		dealCards();
		distributeWeapons();

		// Begin the game loop
		doGame();
	}

	/**
	 * Game loop
	 *
	 * Continuously performs the games logic until the game is ended
	 */
	private void doGame() {
		// assign player turn to 0, so that the player 1 goes first
		int playerTurn = 0;
		Player currentPlayer = null;

		// Begin game loop and continue until the game state changes
		while (!isGameOver) {

			// Gets the current player and sets that player as the current player so can be coloured on board
			currentPlayer = ENTITIES.getPlayer(playerTurn % playerCount);
			currentPlayer.setIsCurrentPlayer(true);

			// Print the board, displaying players as red colour, and the current player as green
			BOARD.printBoard();

			// Begin the parsing of a players choice for their turn
			System.out.println(currentPlayer.getName() + ", what would you like to do?\n");
			int choice = -1;

			// Continually loop until a player chooses a valid option for their turn
			while (choice == -1) {
				// Delegate to the UI to parse the int for the players choice
				choice = displayOptions(currentPlayer);

				// case where the player chooses to simply display their set of cards, and current information.
				// This shouldn't end the players turn and so once complete, just sets the state back to -1 to continue
				// the loop.
				if (choice == 2) {
					UI.doDisplayInformation(currentPlayer);
					choice = -1;
				}
			}

			// Player chooses to perform a move for their turn
			if (choice == 1) {
				doMove(currentPlayer);
			} else if (choice == 3) {
				// Player attempts to make an accusation. If they guess correctly, the game is over and they win
				// Otherwise that player is removed from the game
				if (makeAccusation(currentPlayer)) {
					isGameOver = true;
				} else {
					currentPlayer.setAlive(false);
				}
			} else if (choice == 4) {
				// Case where the player has chosen to make a suggestion, and therefore must be in a room to do so.
				makeSuggestion(currentPlayer);
			}

			// Increment the playerTurn so that on the next loop through, the player virtually clockwise will take
			// their turn.
			playerTurn++;
		}

		// The game is now over and the current player is the winner. Do endGame method
		endGame(currentPlayer);
	}

	private void endGame(Player currentPlayer) {
		UI.doEndGame(currentPlayer);
	}

	/**
	 * Method to handle the choice when the player wants to move around the
	 * board.
	 *
	 * Rolls the dice for the player and then delegates to {@link UI} to get the
	 * move coordinates
	 *
	 * @param currentPlayer The player trying to make the move
	 */
	private void doMove(Player currentPlayer) {
		// Gets the players roll for their turn
		int roll = rollDice();
		Move proposedMove;

		// Clears the console output and then re-displays the board in its current state
		UI.doClearOutput();
		BOARD.printBoard();

		// Loops until the user proposes a valid turn to make
		boolean validTurn = false;
		while (!validTurn) {
			// Delegate to the UI to parse the players proposed move
			proposedMove = UI.getPlayerMove(currentPlayer, roll);

			// If the proposed move returns a valid path when passed through the MovementController,
			// performing the pathing algorithm, then the players current position is updated
			// and the corresponding tiles are also updated

			if (MOVEMENT_CONTROLLER.isValidMove(proposedMove, currentPlayer, roll)) {
				// Disassociate old tile with player
				tiles[currentPlayer.getxPos()][currentPlayer.getyPos()].setPlayer(null);

				// update xy position
				currentPlayer.setxPos(proposedMove.getX());
				currentPlayer.setyPos(proposedMove.getY());
				Tile currentTile = tiles[currentPlayer.getxPos()][currentPlayer.getyPos()];

				// Associate new tile with the player and update if the player is in a room or not
				currentTile.setPlayer(currentPlayer);
				currentPlayer.setRoom(currentTile.getRoom());
				validTurn = true;

				// finally clear the output
				UI.doClearOutput();
			} else {
				System.out.println("Please enter a valid coordinate");
			}
		}

		// finally disassociate the player with being the current player, so that the player is no longer printed as
		// green when the board is printed, and is instead printed as red.
		currentPlayer.setIsCurrentPlayer(false);
	}

	/**
	 * Method to delegate the option select to the {@link UI} class, and gets
	 * the player's proposed move for their turn.
	 *
	 * @param currentPlayer
	 *            Player to select the move
	 *
	 * @return int representing the players choice for their move
	 */
	private int displayOptions(Player currentPlayer) {
		return UI.getTurnOptions(currentPlayer, BOARD);
	}

	/**
	 * Rolls the dice for a player, returning a value between 1 - 6
	 *
	 * @return integer 1 - 6
	 */
	private int rollDice() {
		return (int) (Math.random() * 6 + 1);
	}

	/**
	 * Delegates the creation of a Player list to the {@link UI} class
	 *
	 * The UI class returns a list of players and that is then stored in the
	 * {@link Entities} class
	 */
	private void initPlayers() {
		// Gets a list of player objects from the UI class and sets the entities
		// to hold it
		List<Player> players = UI.getPlayers(ENTITIES.getCharacters(), playerCount);
		ENTITIES.setPlayers(players);

		// Gets the list of tiles from the Entities class to set player
		// locations to tile
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				for (Player p : players) {

					// assigns a player to a tile location
					if (p.getxPos() == i && p.getyPos() == j) {
						tiles[i][j].setPlayer(p);
					}
				}
			}
		}
	}

	private void chooseSolutionCards() {
		boolean room = false;
		boolean character = false;
		boolean weapon = false;
		for (Card card : ENTITIES.getCards()) {
			if (!room && card.getType() == "Room") {
				ENTITIES.getWinningCards().add(card);
				room = true;
			} else if (!character && card.getType() == "Character") {
				ENTITIES.getWinningCards().add(card);
				character = true;
			} else if (!weapon && card.getType() == "Weapon") {
				ENTITIES.getWinningCards().add(card);
				weapon = true;
			}
		}

		ENTITIES.getCards().removeAll(ENTITIES.getWinningCards());

	}

	private void dealCards() {

		int size = ENTITIES.getCards().size();

		for (Player player : ENTITIES.getPlayers()) {
			int count = 0;
			Set<Card> cardsDealt = new HashSet<Card>();
			for (Card card : ENTITIES.getCards()) {
				if (count >= size / playerCount) {
					break;
				}
				player.getCards().add(card);
				cardsDealt.add(card);
				count++;

			}
			ENTITIES.getCards().removeAll(cardsDealt);
		}

	}

	private void distributeWeapons() {

		List<Weapon> remainingWeapons = new ArrayList<Weapon>();
		remainingWeapons.addAll(ENTITIES.getWeapons());

		List<Map.Entry<String, Room>> list = new ArrayList<Map.Entry<String, Room>>(ENTITIES.getRooms().entrySet());
		Collections.shuffle(list);
		for (Entry<String, Room> room : list) {
			if (remainingWeapons.isEmpty())
				break;
			// room.
			room.getValue().addWeaponToAvailableTile(remainingWeapons.get(0));
			remainingWeapons.remove(0);
		}

	}

	/**
	 * Method to handle the logic for a {@link Suggestion}. A player may make a suggest when they're in a room,
	 * and if their proposed suggestion contains invalid cards, players around the board will reveal that their
	 * suggestion was incorrect.
	 *
	 * @param suggestingPlayer The player making the suggestion
	 */
	private void makeSuggestion(Player suggestingPlayer) {

		Suggestion suggestion = UI.getSuggestion(ENTITIES.getFinalCharacters(), ENTITIES.getWeapons(), suggestingPlayer);

		int count = 0;
		int index = suggestingPlayer.getPlayerNumber();
		boolean found = false;

		// loops through all the players to see if any have a matching card to the player's suggestion
		while (count < playerCount && !found) {
			Player nextPlayer = ENTITIES.getPlayer((index + count) % playerCount);

			// Checks for matching characters, then rooms and finally weapons, this is not worth randomising
			if (nextPlayer.containsCardWithName(suggestion.getCharacter().getName())) {
				suggestingPlayer.getSuggestions().add(new Card(suggestion.getCharacter().getName(), "Character"));
				found = true;
			} else if (nextPlayer.containsCardWithName(suggestion.getRoom().getName())) {
				suggestingPlayer.getSuggestions().add(new Card(suggestion.getRoom().getName(), "Room"));
				found = true;
			} else if (nextPlayer.containsCardWithName(suggestion.getWeapon().getName())) {
				suggestingPlayer.getSuggestions().add(new Card(suggestion.getCharacter().getName(), "Weapon"));
				found = true;
			} else {
				count++;
			}
		}
	}

	private boolean makeAccusation(Player player) {

		UI.doClearOutput();

		Suggestion suggestion = UI.getAccusation(ENTITIES.getFinalCharacters(), ENTITIES.getWeapons(),
				ENTITIES.getRooms());

		for (Card card : ENTITIES.getWinningCards()) {
			if (card.getType().equals("Weapon")) {
				if (!card.getName().equals(suggestion.getWeapon().getName())) {
					return false;
				}
			} else if (card.getType().equals("Player")) {
				if (!card.getName().equals(suggestion.getCharacter().getName())) {
					return false;
				}
			} else if (card.getType().equals("Room")) {
				if (!card.getName().equals(suggestion.getRoom().getName())) {
					return false;
				}
			}
		}

		player.setAccusation(suggestion);

		return true;

	}

}
