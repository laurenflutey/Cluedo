package controller;

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
     * Number of players in the Cluedo game. Must be between 3 - 6.
     * Initialised in {@link #GameController()}
     */
    private int playerCount;

    /**
     * Constructor for the {@link GameController} class
     */
    public GameController() {
        UI = new UI();

        // Delegates player count parsing to UI class
        playerCount = UI.getPlayerCount();
        System.out.println(playerCount);
    }


}
