package controller;

import java.util.HashSet;
import java.util.Set;

import model.Board;
import model.Card;
import model.Entities;
import model.Player;
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
	}

	/**
	 * Rolls the dice for a player, returning a value between 1 - 6
	 *
	 * @return integer 1 - 6
	 */
	private int rollDice() {
		return (int) (Math.random() * 6 + 1);
	}

	private void dealCards() {

		for (Player player : ENTITIES.getPlayers()) {
			int count = 0;
			Set<Card> cardsDealt = new HashSet<Card>();
			for (Card card : ENTITIES.getCards()) {
				player.getCards().add(card);
				cardsDealt.add(card);
				count++;
				if (count > ENTITIES.getCards().size() / playerCount) {
					break;
				}
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
		gc.dealCards();
		System.out.println("HELLO");
		for(Player player : gc.ENTITIES.getPlayers()){
			for(Card card : player.getCards()){
				System.out.println(card.getName());
			}
			System.out.println();
		}
	}
}
