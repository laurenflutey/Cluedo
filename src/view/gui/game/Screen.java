package view.gui.game;

import model.Board;
import model.Player;
import model.Tile;

/**
 * Represents a screen that a PLAYER can see from a specific point in the game BOARD.
 *
 * This allows only a small amount of the gameboard to be displayed
 */
public class Screen {

    private final Player PLAYER;
    private final Board BOARD;
    private final Tile[][] TILES;
    private Tile[][] screenTiles;
    private int width, height;

    private int boardWidth;
    private int boardHeight;

    public Screen(int width, int height, Player player, Board board) {
        BOARD = board;
        TILES = BOARD.getTiles();
        PLAYER = player;

        this.width = width;
        this.height = height;

        boardWidth = BOARD.getWidth();
        boardHeight = BOARD.getHeight();

        screenTiles = new Tile[width][height];
    }

    private void updateScreen() {
        boolean leftFill = false;
        boolean rightFill = false;
        boolean topFill = false;
        boolean bottomFill = false;

        //TODO make these ternaries
        if (PLAYER.getXPos() - (width/2) > 0) leftFill = true;
        if (PLAYER.getXPos() + (width/2) < boardWidth) rightFill = true;

        if (PLAYER.getYPos() - (height/2) > 0) topFill = true;
        if (PLAYER.getYPos() + (height/2) < boardHeight) bottomFill = true;

        if (leftFill && rightFill && topFill && bottomFill) {
            int tileX = 0;
            int tileY = 0;
            for (int x = PLAYER.getXPos() - (width/2); x < PLAYER.getXPos() + (width/2); x++) {
                for (int y = PLAYER.getYPos() - (height/2); y < PLAYER.getYPos() + (height/2); y++) {
                    screenTiles[tileX][tileY] = TILES[x][y];
                    tileY++;
                }
                tileX++;
            }
        }
    }
}
