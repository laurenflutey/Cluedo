package controller;

import model.*;
import view.textui.UI;

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
	 * boolean value representing whether the game should be coloured or not.
	 * This is set in the {@link GameController#initGame()} method, and asks the
	 * user if they want the game to be coloured, and it they choose to colour
	 * the game, will test to see if the colouring is working correctly for
	 * them.
	 */
	public static boolean IS_GAME_COLOURED;

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
	private boolean everyoneLost = false;

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

		// Delegate to the UI to check if the user wants to use colouring or not
		initGameOptions();

		// Initialises all the players and sets the game state
		initPlayers();
		isGameOver = false;

		/*
		 * Creates a final game solution, deals the cards to the players, and
		 * distributes the weapons throughout the board
		 */
		chooseSolutionCards();
		dealCards();
		distributeWeapons();

		// Begin the game loop
		doGame();
	}

	/**
	 * Method that delegates to the {@link UI} to see if the user wants to use
	 * colouring in the game and if they do, tests to see if the colouring is
	 * working for them
	 */
	private void initGameOptions() {
		GameController.IS_GAME_COLOURED = UI.doInitialGameSetup();
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

			// checks that everyone isn't dead
			if (!checkForAlivePlayers()) {
				isGameOver = true;
				everyoneLost = true;
				break;
			}

			// Gets the current player and sets that player as the current
			// player so can be coloured on board
			currentPlayer = ENTITIES.getPlayer(playerTurn % playerCount);
			if (currentPlayer.isAlive()) {
				currentPlayer.setIsCurrentPlayer(true);

				// Print the board, displaying players as red colour, and the
				// current player as green
				BOARD.printBoard();

				// Begin the parsing of a players choice for their turn
				if (IS_GAME_COLOURED) {
					System.out.println(
							"\u001B[32m" + currentPlayer.getName() + "\u001B[0m" + ", what would you like to do?\n");
				} else {
					System.out.println(currentPlayer.getName() + ", what would you like to do?\n");
				}
				int choice = -1;

				// Continually loop until a player chooses a valid option for
				// their
				// turn
				while (choice == -1) {
					// Delegate to the UI to parse the int for the players
					// choice
					choice = displayOptions(currentPlayer);

					if (choice == -10) {
						UI.displayKeys(ENTITIES);
						choice = -1;
					}

					// case where the player chooses to simply DISPLAY their set
					// of
					// cards, and current information.
					// This shouldn't end the players turn and so once complete,
					// just sets the state back to -1 to continue
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
					// Player attempts to make an accusation. If they guess
					// correctly, the game is over and they win
					// Otherwise that player is removed from the game
					if (makeAccusation(currentPlayer)) {
						isGameOver = true;
					} else {
						currentPlayer.setAlive(false);
					}
				} else if (choice == 4) {
					// Case where the player has chosen to make a suggestion,
					// and
					// therefore must be in a room to do so.
					makeSuggestion(currentPlayer);
				} else if (choice == 5) {
					// Case where the player is in a room that has a secret
					// passage
					doSecretPassage(currentPlayer);
				}

				// Increment the playerTurn so that on the next loop through,
				// the
				// player virtually clockwise will take
				// their turn.

				currentPlayer.setIsCurrentPlayer(false);
			}
			playerTurn++;
		}

		if (!everyoneLost) {
			// The game is now over and the current player is the winner. Do
			// endGame
			// method
			endGame(currentPlayer);
		} else {
			// case where no one has won the game...
			endGame();
		}
	}

	/**
	 * Method to check if there are still players alive in the game
	 *
	 * @return is anyone still here???...
	 */
	private boolean checkForAlivePlayers() {
		for (Player p : ENTITIES.getPlayers()) {
			if (p.isAlive())
				return true;
		}
		return false;
	}

	/**
	 * Method to handle the secret passage movement in the game, just puts a
	 * player in the first available place in the room if they has chosen to
	 * move
	 *
	 * @param currentPlayer
	 *            The player moving to the room
	 */
	private void doSecretPassage(Player currentPlayer) {
		if (UI.doSecretPassageConfirm(currentPlayer)) {

			// Gets the current room, and the connection room and assigns it to
			// the player
			Room current = currentPlayer.getRoom();
			Room connectingRoom = current.getConnectingRoom();

			randomAssignToRoom(currentPlayer, connectingRoom);
		}
	}

	/**
	 * Method to randomly assign a {@link Player} to a room
	 *
	 * @param player
	 *            Player being randomly placed in the room
	 * @param assignedRoom
	 *            Room that the player is being randomly assigned to
	 */
	private void randomAssignToRoom(Player player, Room assignedRoom) {
		ArrayList<Tile> connectingRoomTiles = assignedRoom.getTiles();
		Collections.shuffle(connectingRoomTiles);

		for (Tile t : connectingRoomTiles) {
			if (t.isRoomTile() && !t.isWallTile() && !t.isOccupied()) {

				// Disassociate old tile with player
				tiles[player.getXPos()][player.getYPos()].setPlayer(null);

				// update xy position
				player.setXPos(t.getX());
				player.setYPos(t.getY());
				Tile currentTile = tiles[player.getXPos()][player.getYPos()];

				// Associate new tile with the player and update if the player
				// is in a room or not
				currentTile.setPlayer(player);
				player.setRoom(assignedRoom);

				break;
			}
		}
	}

	/**
	 * Delegate method to pass the call to end the game to the {@link UI}
	 *
	 * @param winningPlayer
	 *            The Player that has won the game
	 */
	private void endGame(Player winningPlayer) {
		UI.doEndGame(winningPlayer);
	}

	private void endGame() {
		UI.doEndGame();
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
		// Gets the players roll for their turn
		int roll = rollDice();
		Move proposedMove;

		// Clears the console output and then re-displays the board in its
		// current state
		UI.doClearOutput();
		BOARD.printBoard();

		// Loops until the user proposes a valid turn to make
		boolean validTurn = false;
		while (!validTurn) {
			// Delegate to the UI to parse the players proposed move
			proposedMove = UI.getPlayerMove(currentPlayer, roll);

			// If the proposed move returns a valid path when passed through the
			// MovementController,
			// performing the pathing algorithm, then the players current
			// position is updated
			// and the corresponding tiles are also updated

			if (MOVEMENT_CONTROLLER.isValidMove(proposedMove, currentPlayer, roll)) {
				// Disassociate old tile with player
				tiles[currentPlayer.getXPos()][currentPlayer.getYPos()].setPlayer(null);

				// update xy position
				currentPlayer.setXPos(proposedMove.getX());
				currentPlayer.setYPos(proposedMove.getY());
				Tile currentTile = tiles[currentPlayer.getXPos()][currentPlayer.getYPos()];

				// Associate new tile with the player and update if the player
				// is in a room or not
				currentTile.setPlayer(currentPlayer);
				currentPlayer.setRoom(currentTile.getRoom());
				validTurn = true;

				// finally clear the output
				UI.doClearOutput();
			} else {
				System.out.println("Please enter a valid coordinate");
			}
		}

		// finally disassociate the player with being the current player, so
		// that the player is no longer printed as
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
	public int rollDice() {
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
		ENTITIES.setFinalPlayers(players);

		// Gets the list of tiles from the Entities class to set player
		// locations to tile
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				for (Player p : players) {

					// assigns a player to a tile location
					if (p.getXPos() == i && p.getYPos() == j) {
						tiles[i][j].setPlayer(p);
					}
				}
			}
		}
	}

	/**
	 * Method that randomly creates a solution to the game. A solution consists
	 * of three {@link Card}. A {@link Weapon}, a {@link Room} and a
	 * {@link model.Character}.
	 */
	private void chooseSolutionCards() {
		boolean room = false;
		boolean character = false;
		boolean weapon = false;

		// Randomly select cards from the collection of cards until we have a
		// valid solution
		for (Card card : ENTITIES.getCards()) {
			if (!room && card.getType().equals("Room")) {
				ENTITIES.getWinningCards().add(card);
				room = true;
			} else if (!character && card.getType().equals("Character")) {
				ENTITIES.getWinningCards().add(card);
				character = true;
			} else if (!weapon && card.getType().equals("Weapon")) {
				ENTITIES.getWinningCards().add(card);
				weapon = true;
			}
		}

		// remove the solution cards from the collection of cards, so that
		// they're not dealt to players
		ENTITIES.getCards().removeAll(ENTITIES.getWinningCards());

	}

	/**
	 * Method to deal the cards to the players in the game.
	 */
	private void dealCards() {

		int size = ENTITIES.getCards().size();
		for (Player player : ENTITIES.getPlayers()) {
			int count = 0;
			Set<Card> cardsDealt = new HashSet<>();
			for (Card card : ENTITIES.getCards()) {
				if (count >= size / playerCount) {
					break;
				}
				player.getCards().add(card);
				cardsDealt.add(card);
				count++;

			}

			// removes dealt cards
			ENTITIES.getCards().removeAll(cardsDealt);
		}
	}

	/**
	 * Randomly distributes the weapons throughout the board to a list of
	 * available tiles
	 */
	private void distributeWeapons() {
		List<Weapon> remainingWeapons = new ArrayList<Weapon>();

		remainingWeapons.addAll(ENTITIES.getWeapons());

		List<Map.Entry<String, Room>> list = new ArrayList<>(ENTITIES.getRooms().entrySet());
		Collections.shuffle(list);
		for (Entry<String, Room> room : list) {
			if (remainingWeapons.isEmpty())
				break;
			if (!room.getKey().equals("Pool")) {
				room.getValue().addWeaponToAvailableTile(ENTITIES.getBoard().getTiles(), remainingWeapons.get(0));
				remainingWeapons.remove(0);
			}
		}
	}

	/**
	 * Method to handle the logic for a {@link Suggestion}. A player may make a
	 * suggest when they're in a room, and if their proposed suggestion contains
	 * invalid cards, players around the board will reveal that their suggestion
	 * was incorrect.
	 *
	 * @param suggestingPlayer
	 *            The player making the suggestion
	 */
	private void makeSuggestion(Player suggestingPlayer) {

		Suggestion suggestion = UI.getSuggestion(ENTITIES.getFinalPlayers(), ENTITIES.getWeapons(), suggestingPlayer);

		Room playerRoom = suggestingPlayer.getRoom();
		int count = 0;
		int index = suggestingPlayer.getPlayerNumber();
		boolean found = false;

		suggestion.getPlayer().setIsCurrentPlayer(false);

		// loops through all the players to see if any have a matching card to
		// the player's suggestion
		while (count < playerCount && !found) {
			Player nextPlayer = ENTITIES.getPlayer((index + count) % playerCount);

			// Checks for matching characters, then rooms and finally weapons,
			// this is not worth randomising
			if (nextPlayer.containsCardWithName(suggestion.getPlayer().getName())) {
				suggestingPlayer.getSuggestions().add(new Card(suggestion.getPlayer().getName(), "Character"));
				playerRoom.addPlayerToAvailableTile(ENTITIES.getBoard().getTiles(), suggestion.getPlayer());
				found = true;
			} else if (nextPlayer.containsCardWithName(suggestion.getRoom().getName())) {
				suggestingPlayer.getSuggestions().add(new Card(suggestion.getRoom().getName(), "Room"));
				found = true;
			} else if (nextPlayer.containsCardWithName(suggestion.getWeapon().getName())) {
				suggestingPlayer.getSuggestions().add(new Card(suggestion.getWeapon().getName(), "Weapon"));
				found = true;
				playerRoom.addWeaponToAvailableTile(ENTITIES.getBoard().getTiles(), suggestion.getWeapon());
			} else {
				count++;
			}
		}
	}

	/**
	 * Method to handle the logic of when a player makes an accusation.
	 * Delegates the parsing of the accusation to the {@link UI} class, and then
	 * checks if the players accusation was correct or not.
	 *
	 * If the player guessed correctly, the game ends and they're the winner.
	 * However if they failed to guess correctly, they're removed from the game.
	 *
	 * @param accusingPlayer
	 *            The player making the accusation
	 *
	 * @return The result of their accusation
	 */
	private boolean makeAccusation(Player accusingPlayer) {

		// first we want to clear the text output
		UI.doClearOutput();

		// Delegate to the UI to parse the players accusation.
		// The accusation comes in the form of a Suggestion, but they're
		// essentially the same thing.
		// The only difference is the way they're treated.

		Suggestion suggestion = UI.getAccusation(ENTITIES.getFinalPlayers(), ENTITIES.getWeapons(),
				ENTITIES.getRooms());

		// Now iterate through the collection of entities to check if the
		// players accusation was valid or not
		for (Card card : ENTITIES.getWinningCards()) {
			// Used a switch statement, as these are just distinct switch
			// cases... why the heck not?
			switch (card.getType()) {
			case "Weapon":
				if (!card.getName().equals(suggestion.getWeapon().getName())) {
					UI.doLose();
					return false;
				}
				break;
			case "Player":
				if (!card.getName().equals(suggestion.getPlayer().getName())) {
					UI.doLose();
					return false;
				}
				break;
			case "Room":
				if (!card.getName().equals(suggestion.getRoom().getName())) {
					UI.doLose();
					return false;
				}
				break;
			}
		}

		// Sets the accusation to be stored in the Player object. This is used
		// if the player is the winner
		// and their accusation needs to be displayed to the UI
		accusingPlayer.setAccusation(suggestion);

		return true;
	}
}
