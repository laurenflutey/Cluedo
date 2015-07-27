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
     * Constructor for the {@link GameController} class
     */
    public GameController() {
        this.UI = new UI();
    }



    public static void main(String[] args) {
        new GameController();
    }
}
