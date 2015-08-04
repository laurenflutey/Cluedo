package controller;

import model.*;
import view.UI;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		initPlayers();
		isGameOver = false;
		chooseSolutionCards();
		dealCards();
		// Board.parseBoard("Board.txt", ENTITIES);
		doGame();
	}

	/**
	 * Game loop
	 *
	 * Continuously performs the games logic until the game is ended
	 */
	private void doGame() {

		int playerTurn = 0;
		while (!isGameOver) {

			Player currentPlayer = ENTITIES.getPlayer(playerTurn % playerCount);
			currentPlayer.setIsCurrentPlayer(true);

			BOARD.printBoard();

			System.out.println(currentPlayer.getName() + ", what would you like to do?");

			int choice = -1;
			while (choice == -1) {
				choice = displayOptions(currentPlayer);
				if (choice == 2) {
					UI.doDisplayInformation(currentPlayer);
					choice = -1;
				}
			}

			if (choice == 1) {
				doMove(currentPlayer);
			}

			else if (choice == 4) {
				makeSuggestion(currentPlayer);
			}

			// playerTurn++; //TODO set to never increment player for testing
			// purposes
		}
	}

	/**
	 * Method to handle the choice when the player wants to move around the
	 * board.
	 *
	 * Rolls the dice for the player and then delegates to {@link UI} to get the
	 * move coordinates
	 *
	 * @param currentPlayer
	 *            The player trying to make the move
	 */
	private void doMove(Player currentPlayer) {
		int roll = 15;

		// TODO GAME LOGIC
		System.out.println("x: " + currentPlayer.getxPos() + " y: " + currentPlayer.getyPos());
		System.out.println("currentPlayerNumber = " + currentPlayer.getPlayerNumber());
		System.out.println("roll = " + roll);
		Move proposedMove;

		boolean validTurn = false;

		while (!validTurn) {
			proposedMove = UI.getPlayerMove(currentPlayer);
			if (MOVEMENT_CONTROLLER.isValidMove(proposedMove, currentPlayer, roll)) {
				tiles[currentPlayer.getxPos()][currentPlayer.getyPos()].setPlayer(null);
				currentPlayer.setxPos(proposedMove.getX());
				currentPlayer.setyPos(proposedMove.getY());
				Tile currentTile = tiles[currentPlayer.getxPos()][currentPlayer.getyPos()];
				currentTile.setPlayer(currentPlayer);
				currentPlayer.setRoom(currentTile.getRoom());
				validTurn = true;
			} else {
				System.out.println("Please enter a valid coordinate");
			}
		}

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

	private void makeSuggestion(Player player) {

		Suggestion suggestion = UI.getSuggestion(ENTITIES.getPlayers(), ENTITIES.getWeapons(), player);

		int count = 0;
		int index = player.getPlayerNumber();
		boolean found = false;
		while (count < playerCount && !found) {
			Player nextPlayer = ENTITIES.getPlayer((index + count) % playerCount);

			if (nextPlayer.getCards().contains(suggestion.getCharacter())) {
				player.getSuggestions().add(new Card(suggestion.getCharacter().getName(), "Character"));
				found = true;
			} else if (nextPlayer.getCards().contains(suggestion.getRoom())) {
				player.getSuggestions().add(new Card(suggestion.getRoom().getName(), "Room"));
				found = true;
			} else if (nextPlayer.getCards().contains(suggestion.getWeapon())) {
				player.getSuggestions().add(new Card(suggestion.getCharacter().getName(), "Weapon"));
				found = true;
			} else {
				count++;
			}
		}

	}
}
