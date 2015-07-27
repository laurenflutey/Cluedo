package view;

import java.util.Scanner;

/**
 * MVC View class for interacting with the players in the Cluedo game.
 *
 * @author Marcel van Workum
 * @author Reuben Puketapu
 */
public class UI {

    /**
     * Initial messaged displayed to players of the Cluedo game
     */
    private String initMessage = "Welcome to Cluedo."; //TODO Placeholder

    /**
     * Input scanner used to handle all inputted information from the players
     */
    private Scanner reader = new Scanner(System.in);

    /**
     * Constructor for the UI Class
     */
    public UI() {
        System.out.println(initMessage);
    }

    public int getPlayerCount() {
        //TODO Add invalid input check
        System.out.println("Ever the number of players (3-6");
        int playerCount = reader.nextInt();
        return playerCount;
    }

}
