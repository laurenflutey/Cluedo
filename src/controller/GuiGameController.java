package controller;

import model.*;
import model.Character;
import view.gui.StartupFrame;
import view.gui.game.GameFrame;
import view.gui.game.components.ButtonPanel;
import view.gui.game.components.SuggestionDialog;
import view.textui.UI;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * MVC Controller class to handle the logic of the GUI Cluedo game.
 *
 * @author Marcel van Workum
 * @author Reuben Puketapu
 */
public class GuiGameController {

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
	 * <p/>
	 * Movement is controlled by a recursive brute force(maybe greedy?)
	 * algorithm
	 */
	private final MovementController MOVEMENT_CONTROLLER;

	/**
	 * 2D array of Tile objects, representing the board
	 */
	private Tile[][] tiles;

	/**
	 * Handles the interaction with the users using the GUI Frame
	 */
	private static GameFrame DISPLAY;

	private int playerCount;
	private int playerTurn;

	private boolean isGameOver;
	private boolean everyoneLost = false;

	private Player currentPlayer; // TODO set the current player somewhere

	/**
	 * Constructor
	 * <p/>
	 * Creates the entities in the game, initialises the board and creates the
	 * movement controller.
	 */
	public GuiGameController() {
		ENTITIES = new Entities();
		BOARD = ENTITIES.getBoard();

		tiles = ENTITIES.getBoard().getTiles();

		/* Assign board to movement controller */
		this.MOVEMENT_CONTROLLER = new MovementController(BOARD);

		new StartupFrame(this);

		// TODO is this needed?
		// DISPLAY.repaint();
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
	 * Delegate method to handle the various initialisation stages of the game
	 *
	 * @param playersList
	 *            Players in the game
	 */
	public void initGame(ArrayList<String> playersList, ArrayList<String> nameList) {

		// Sets up the players in the game
		initHumanPlayers(playersList, nameList);
		initFakePlayers(ENTITIES.getPlayers());

		isGameOver = false;
		DISPLAY = new GameFrame(this);

		/*
		 * Creates a final game solution, deals the cards to the players, and
		 * distributes the weapons throughout the board
		 */
		chooseSolutionCards();
		dealCards();
		distributeWeapons();

		initPlayerTurn();
		// doGame();
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
		for (Map.Entry<String, Room> room : list) {
			if (remainingWeapons.isEmpty())
				break;
			if (!room.getKey().equals("Pool")) {
				room.getValue().addWeaponToAvailableTile(ENTITIES.getBoard().getTiles(), remainingWeapons.get(0));
				remainingWeapons.remove(0);
			}
		}
	}

	/**
	 * Game loop
	 * <p/>
	 * Continuously performs the games logic until the game is ended
	 */
	private void doGame() {
		// assign player turn to 0, so that the player 1 goes first
		int playerTurn = 0;

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

				currentPlayer.setIsCurrentPlayer(false);
			}
			// Next players turn now
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

	private void endGame() {

	}

	private void endGame(Player currentPlayer) {

	}

	// TODO Secret passage
	// TODO Suggestion
	// TODO accusation
	// TODO display options to player
	// TODO player move
	// TODO end game winner
	// TODO end game all lost
	// TODO gameloop

	/**
	 * Gets entities.
	 *
	 * @return the entities
	 */
	public Entities getEntities() {
		return ENTITIES;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	private void initHumanPlayers(List<String> gamePlayers, List<String> namePlayers) {

		List<Player> endPlayers = new ArrayList<>();

		for (int i = 0; i < gamePlayers.size(); i++) {
			Character character = ENTITIES.getCharacter(gamePlayers.get(i));
			Player player = new Player(namePlayers.get(i), character.getName(), character.getCh(), character.getXPos(),
					character.getYPos());
			player.setCharacter(character);
			player.setXPos(character.getXPos());
			player.setYPos(character.getYPos());
			player.setAlive(true);
			player.setPlayerNumber(playerCount + 1);
			playerCount++;
			endPlayers.add(player);
		}

		currentPlayer = endPlayers.get(0);
		playerTurn = 1;

		ENTITIES.setPlayers(endPlayers);
		ENTITIES.setFinalPlayers(endPlayers);

	}

	/**
	 * Delegates the creation of a Player list to the {@link UI} class
	 * <p/>
	 * The UI class returns a list of players and that is then stored in the
	 * {@link Entities} class
	 *
	 * @param players
	 *            List of players in the game parsed from the startup frame
	 */
	private void initFakePlayers(List<Player> players) {
		// initialises inactive players
		List<Player> fakePlayers = new ArrayList<>();

		for (Character character : ENTITIES.getCharacters()) {
			boolean contains = false;
			for (Player player : players) {
				if (player.getCharacter().getName().equals(character.getName())) {
					contains = true;
				}
			}
			if (!contains) {
				Player player = new Player(character.getName(), character.getCh(), character.getXPos(),
						character.getYPos());
				player.setCharacter(character);
				player.setAlive(false);
				fakePlayers.add(player);
				player.setPlayerNumber(players.size());

			}
		}
		// Gets a list of player objects from the UI class and sets the entities
		// to hold it

		ENTITIES.getPlayers().addAll(fakePlayers);
		// ENTITIES.getFinalPlayers().addAll(fakePlayers);

		// Gets the list of tiles from the Entities class to set player
		// locations to tile
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				for (Player p : ENTITIES.getPlayers()) {

					// assigns a player to a tile location
					if (p.getXPos() == i && p.getYPos() == j) {
						tiles[i][j].setPlayer(p);
					}
				}
			}
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

	// TODO Secret passage
	// TODO Suggestion
	// TODO accusation
	// TODO display options to player
	// TODO player move
	// TODO end game winner
	// TODO end game all lost
	// TODO gameloop

	private void initPlayerTurn() {

		DISPLAY.getButtonPanel().setRoll(true);
		DISPLAY.getButtonPanel().setAccuse(true);

		DISPLAY.getInformationPanel().setName(currentPlayer.getPlayerName());
		DISPLAY.getInformationPanel().setChar(currentPlayer.getName());
		DISPLAY.getInformationPanel().setIcon(getCurrentPlayerImage());

		if (currentPlayer.getRoom() != null) {
			DISPLAY.getButtonPanel().setSuggest(true);
			if (currentPlayer.getRoom().getConnectingRoom() != null) {
				DISPLAY.getButtonPanel().setSecretRoom(true);
			}
		} else {
			DISPLAY.getButtonPanel().setSuggest(false);
			DISPLAY.getButtonPanel().setSecretRoom(false);
		}

		DISPLAY.getInformationPanel().setCardsInHand();
	}

	public void accuse() {
		new SuggestionDialog(ENTITIES, "accuse", this);

	}

	public void suggest() {
		new SuggestionDialog(ENTITIES, "suggest", this);

	}

	public void moveToSecretRoom() {
		randomAssignToRoom(currentPlayer, currentPlayer.getRoom().getConnectingRoom());
	}

	public void createSuggestion(Suggestion suggestion, String type) {

		if (type.equals("accuse")) {
			if (makeAccusation(currentPlayer, suggestion)) {
				isGameOver = true;
			} else {
				currentPlayer.setAlive(false);
			}
		} else if (type.equals("suggest")) {
			makeSuggestion(currentPlayer, suggestion);
		}

	}

	private void makeSuggestion(Player suggestingPlayer, Suggestion suggestion) {

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
				suggestingPlayer.getSuggestions().add(new Card(suggestion.getPlayer().getName(), "Character",
						ENTITIES.getFromAllCard(suggestion.getPlayer().getName()).getPath()));
				randomAssignToRoom(suggestion.getPlayer(), currentPlayer.getRoom());
				found = true;
			} else if (nextPlayer.containsCardWithName(suggestion.getRoom().getName())) {
				suggestingPlayer.getSuggestions().add(new Card(suggestion.getRoom().getName(), "Room",
						ENTITIES.getFromAllCard(suggestion.getRoom().getName()).getPath()));
				found = true;
			} else if (nextPlayer.containsCardWithName(suggestion.getWeapon().getName())) {
				suggestingPlayer.getSuggestions().add(new Card(suggestion.getWeapon().getName(), "Weapon",
						ENTITIES.getFromAllCard(suggestion.
								getWeapon().
								getName()).
								getPath()));
				found = true;
				playerRoom.addWeaponToAvailableTile(ENTITIES.getBoard().getTiles(), suggestion.getWeapon());
			} else {
				count++;
			}
		}

		DISPLAY.getInformationPanel().repaint();
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
	private boolean makeAccusation(Player accusingPlayer, Suggestion suggestion) {

		// first we want to clear the text output

		// Now iterate through the collection of entities to check if the
		// players accusation was valid or not
		for (Card card : ENTITIES.getWinningCards()) {
			// Used a switch statement, as these are just distinct switch
			// cases... why the heck not?
			switch (card.getType()) {
			case "Weapon":
				if (!card.getName().equals(suggestion.getWeapon().getName())) {
					// UI.doLose();
					return false;
				}
				break;
			case "Player":
				if (!card.getName().equals(suggestion.getPlayer().getName())) {
					// UI.doLose();
					return false;
				}
				break;
			case "Room":
				if (!card.getName().equals(suggestion.getRoom().getName())) {
					// UI.doLose();
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

	public void incrementPlayerTurn() {
		currentPlayer = ENTITIES.getFinalPlayers().get(playerTurn++ % playerCount);
		System.out.println(currentPlayer.getName());
		initPlayerTurn();
	}

	public final BufferedImage getCurrentPlayerImage() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("images/characters/icon/" + currentPlayer.getName() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public void sendMove(Move move) {

		if (MOVEMENT_CONTROLLER.isValidMove(move, currentPlayer, 6)) {
			// Disassociate old tile with player
			tiles[currentPlayer.getXPos()][currentPlayer.getYPos()].setPlayer(null);

			// update xy position
			currentPlayer.setXPos(move.getX());
			currentPlayer.setYPos(move.getY());
			Tile currentTile = tiles[currentPlayer.getXPos()][currentPlayer.getYPos()];

			// Associate new tile with the player and update if the player
			// is in a room or not
			currentTile.setPlayer(currentPlayer);
			currentPlayer.setRoom(currentTile.getRoom());
			DISPLAY.getInformationPanel().setRoomInfo(currentPlayer.getRoom());

			if (currentPlayer.getRoom() != null) {
				DISPLAY.getButtonPanel().setSuggest(true);
				if (currentPlayer.getRoom().getConnectingRoom() != null) {
					DISPLAY.getButtonPanel().setSecretRoom(true);
				} else {
					DISPLAY.getButtonPanel().setSecretRoom(false);
				}
			} else {
				DISPLAY.getButtonPanel().setSuggest(false);
				DISPLAY.getButtonPanel().setSecretRoom(false);
			}

		}
	}

	public ButtonPanel getButtonPanel() {
		return DISPLAY.getButtonPanel();
	}

	/**
	 * Handles the closing of the game client
	 */
	public static void closeGame() {
		DISPLAY.close();
		DISPLAY = null;
	}

	public String getTileInfo(int x, int y) {
		Tile t = tiles[x][y];
		if (t.isRoomTile()) {
			return t.getRoom().getName();
		}
		return "";
	}

	public Player getCurrentPlayerHovered(int x, int y) {
		return tiles[x][y].getPlayer();
	}
}
