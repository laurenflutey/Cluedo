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
    private String initMessage = "Welcome to Cluedo.\n" +
            "--------------------------------\n" +
            "| A simple text based Java game.\n" +
            "| Created by: \n|\tReuben Puketapu\n|\tMarcel van Workum.\n" +
            "--------------------------------\n\n";

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

    /**
     * Gets the user to input the number of players for the Cluedo game.
     *
     * Called by {@link controller.GameController}
     * and stored in the {@link model} package.
     *
     * @return Number of players.
     */
    public int getPlayerCount() {
        System.out.println("Enter the number of players (3-6):");
        String input;

        /* Continuously loops while valid input has not been found */
        while (true) {
            input = reader.nextLine();

            // Checks if number input is integer and between 3 - 6
            if (isValidInteger(input)) {
                break;
            }

            System.out.println("Please enter a valid integer between 3 - 6");
        }

        // Parses the valid number and returns to GameController
        return Integer.parseInt(input);
    }

    /**
     * Helper method for {@link #getPlayerCount()}. Checks the validity of a users input to see
     * if it is first a valid integer and then if it is between 3 - 6.
     *
     * @param input input from user via System.In
     *
     * @return The result of the validity check
     */
    private boolean isValidInteger(String input) {
        try {
            int valid = Integer.parseInt(input);
            return valid >= 3 && valid <= 6;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
