package controller;

import model.Board;
import model.Card;
import model.Entities;
import model.Player;
import view.UI;

import java.util.HashSet;
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
		//Board.parseBoard("Board.txt", ENTITIES);
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
		ENTITIES.setPlayers(UI.getPlayers(ENTITIES.getCharacters(), playerCount));
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

	public static void main(String[] args) {
		GameController gc = new GameController();
		gc.initGame();

		gc.ENTITIES.addPlayer(new Player("reuben", 'r', 2, 2));
		gc.ENTITIES.addPlayer(new Player("marcel", 'm', 4, 4));
		gc.ENTITIES.addPlayer(new Player("djp", 'd', 6, 6));
		gc.ENTITIES.addPlayer(new Player("djp", 'd', 6, 6));
		gc.chooseSolutionCards();
		gc.dealCards();
		System.out.println("HELLO");

		for (Card card : gc.ENTITIES.getWinningCards()) {
			System.out.println(card.getName());
		}
		System.out.println();

		for (Player player : gc.ENTITIES.getPlayers()) {
			for (Card card : player.getCards()) {
				System.out.println(card.getName());
			}
			System.out.println();
		}
	}
}
