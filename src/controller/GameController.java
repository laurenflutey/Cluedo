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

	private final Board BOARD;

	private final MovementController MOVEMENT_CONTROLLER;

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
		//Board.parseBoard("Board.txt", ENTITIES);

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
			BOARD.printBoard();
			
			int roll = rollDice();
			Player currentPlayer = ENTITIES.getPlayer(playerTurn % playerCount);
			//TODO GAME LOGIC
			Move proposedMove = UI.getPlayerMove(currentPlayer);

			System.out.println("x: " + currentPlayer.getxPos() + " y: " + currentPlayer.getyPos());

			System.out.println(MOVEMENT_CONTROLLER.isValidMove(proposedMove, currentPlayer, roll));
			playerTurn++;
		}
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
	 * The UI class returns a list of players and that is then stored in the {@link Entities} class
	 */
	private void initPlayers() {
		// Gets a list of player objects from the UI class and sets the entities to hold it
		List<Player> players = UI.getPlayers(ENTITIES.getCharacters(), playerCount);
		ENTITIES.setPlayers(players);

		// Gets the list of tiles from the Entities class to set player locations to tiles
		Tile[][] tiles = ENTITIES.getBoard().getTiles();
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
}
