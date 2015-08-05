package tests;

import controller.GameController;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * JUnit test suite for the GameController Class
 *
 * @see controller.GameController
 *
 * @author Marcel van Workum
 * @author Reuben Puketapu
 */
public class GameControllerTests {

    private GameController gameController;

    public GameControllerTests() {
        gameController = new GameController();
    }

    @Test
    public void testRolling() {
        for (int i = 0; i < 1000; i++) {
            int roll = gameController.rollDice();
            assertTrue(roll <= 6 && roll > 0);
        }
    }
}
