package controller;

import model.Board;
import model.Entities;
import view.UI;

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

	/**
	 * Constructor for the {@link GameController} class
	 */
	public GameController() {
		UI = new UI();
		ENTITIES = new Entities(); /* TODO init all entities and assign position */
		this.BOARD = ENTITIES.getBoard();

		/* Assign board to movement controller */
		MOVEMENT_CONTROLLER = new MovementController(BOARD);
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
		System.out.println(playerCount);
	}

	/**
	 * Rolls the dice for a player, returning a value between 1 - 6
	 *
	 * @return integer 1 - 6
	 */
	private int rollDice() {
		return (int) (Math.random() * 6 + 1);
	}

}
