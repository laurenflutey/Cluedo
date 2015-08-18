import view.gui.StartupFrame;

/**
 * MainText class used to called main method which creates the GUI Cluedo game.
 *
 * @author Marcel van Workum
 * @author Reuben Puketapu
 */
public class MainGui {

    /**
     * Creates a {@link view.gui.StartupFrame} which parses the players from the user and then creates the
     * {@link controller.GuiGameController} class to handle the actual game logic.
     *
     * @param args redundant
     */
    public static void main(String[] args) {
        new StartupFrame(500, 500);
    }
}
