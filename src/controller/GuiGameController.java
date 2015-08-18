package controller;

import model.*;
import view.gui.game.GameFrame;
import view.textui.UI;

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
     * Handles the interaction with the users using the GUI Frame
     */
    private final GameFrame DISPLAY;

    private int playerCount;
    private boolean isGameOver;
    private boolean everyoneLost = false;

    /**
     * Constructor
     *
     * Creates the entities in the game, initialises the board and creates the movement controller.
     *
     * @param gamePlayers list of players in the game
     */
    public GuiGameController(List<Player> gamePlayers, Entities entities) {
        ENTITIES = entities;
        ENTITIES.setFinalPlayers(gamePlayers);
        ENTITIES.setPlayers(gamePlayers);
        BOARD = ENTITIES.getBoard();

        tiles = ENTITIES.getBoard().getTiles();

		/* Assign board to movement controller */
        MOVEMENT_CONTROLLER = new MovementController(BOARD);

        // Initialises the game
        initGame(gamePlayers);

        // create the game frame
        DISPLAY = new GameFrame(BOARD);

        //TODO is this needed?
        //DISPLAY.repaint();
    }

    /**
     * Alt Constructor
     *
     * Doesn't take a list of players parsed from the startup frame, and so is somewhat useless in the current
     * implementation.
     *
     * Creates the entities in the game, initialises the board and creates the movement controller.
     *
     * Also creates the GUI Display frame
     */
    public GuiGameController() {
        ENTITIES = new Entities();
        BOARD = ENTITIES.getBoard();

        tiles = ENTITIES.getBoard().getTiles();

		/* Assign board to movement controller */
        MOVEMENT_CONTROLLER = new MovementController(BOARD);

        DISPLAY = new GameFrame(BOARD);
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
     * @param gamePlayers Players in the game
     */
    private void initGame(List<Player> gamePlayers) {
        // Sets up the players in the game
        playerCount = gamePlayers.size();
        initPlayers(gamePlayers);

        isGameOver = false;

        /*
		 * Creates a final game solution, deals the cards to the players, and
		 * distributes the weapons throughout the board
		 */
        chooseSolutionCards();
        dealCards();
        distributeWeapons();

        doGame();
    }

    /**
     * Delegates the creation of a Player list to the {@link UI} class
     *
     * The UI class returns a list of players and that is then stored in the
     * {@link Entities} class
     *
     * @param players List of players in the game parsed from the startup frame
     */
    private void initPlayers(List<Player> players) {
        // Gets a list of player objects from the UI class and sets the entities
        // to hold it
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
        for (Map.Entry<String, Room> room : list) {
            if (remainingWeapons.isEmpty())
                break;
            if (!room.getKey().equals("Pool")) {
                room.getValue().addWeaponToAvailableTile(ENTITIES.getBoard().getTiles(), remainingWeapons.get(0));
                remainingWeapons.remove(0);
            }
        }
    }

    private void doGame() {

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
     * @param player Player being randomly placed in the room
     * @param assignedRoom Room that the player is being randomly assigned to
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

    //TODO Secret passage
    //TODO Suggestion
    //TODO accusation
    //TODO display options to player
    //TODO player move
    //TODO end game winner
    //TODO end game all lost
    //TODO gameloop

    public static void main(String[] args) {
        new GuiGameController();
    }
}
