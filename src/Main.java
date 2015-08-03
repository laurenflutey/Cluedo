import controller.GameController;

/**
 * Main class used to called main method which creates the Cluedo game.
 *
 * @author Marcel van Workum
 * @author Reuben Puketapu
 */
public class Main {

	/**
	 * Main method calls the creation of a new Game Controller
	 *
	 * @param args
	 *            redundant
	 */
	public static void main(String[] args) {
		new GameController().initGame();

	}
}
