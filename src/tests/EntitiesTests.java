package tests;

import model.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * JUnit test suite for the collections of entities within the Cluedo game.
 *
 * @author Marcel van Workum
 * @author Reuben Puketapu
 */
public class EntitiesTests {

    private Entities entities;

    public EntitiesTests() {
        entities = new Entities();
    }

    @Test
    public void testTileSizeDimensions() {
        Tile[][] tiles = entities.getBoard().getTiles();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                assertTrue(!(tiles[i][j].getX() < 0));
                assertTrue(!(tiles[i][j].getX() > 23));
                assertTrue(!(tiles[i][j].getY() < 0));
                assertTrue(!(tiles[i][j].getY() > 25));
            }
        }
    }

    @Test
    public void testCharacterCollection() {
        assertTrue(entities.getCharacters().size() == 6);
        for (model.Character c : entities.getCharacters()) {
            assertTrue(!c.isUsed());
            c.setUsed(true);
            assertTrue(c.isUsed());
        }
    }
}
