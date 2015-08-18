package view.gui.game.components;

import controller.GuiGameController;
import model.Tile;
import view.gui.game.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Game canvas, which is embedded in the JFrame of the {@link GameFrame}. The canvas is used to draw the current
 * state of the game BOARD for the player to interact with.
 *
 * @author Marcel
 * @author Reuben
 */
public class GameCanvas extends Canvas{

    /**
     * Static dimension representing width of the GameCanvas
     */
    public static int width = 768;

    /**
     * Static dimension representing height of the GameCanvas
     */
    public static int height = 640;

    /**
     * Size of a single tile in the game
     */
    private final int TILE_SIZE = 32;

    private final Tile[][] tiles;
    private final GuiGameController GUIGAMECONTROLLER;

    // An array of pixels representing the game canvas
    private int[] pixels;

    //TODO Remove
    private final Random random = new Random();

    /**
     * Constructor
     *
     * Creates the GameCanvas and assigns it to it position on the parent grid bag layout
     *  @param guiGameController Board
     * @param contentPane Parent panel
     */
    public GameCanvas(final GuiGameController guiGameController, JPanel contentPane) {
        GUIGAMECONTROLLER = guiGameController;
        tiles = GUIGAMECONTROLLER.getEntities().getBoard().getTiles();

        // Set up grid bag constraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridheight = 3;

        // add canvas to parent panel
        contentPane.add(this, constraints);

        // init pixel array, 1D over 2D for access speed
        pixels = new int[width * height];
    }

    /**
     * Method to update the contents of the canvas
     */
    public void tick() {
        //TODO Create image raster and convert to buffered image
        //TODO aim for 60 fps, and about 120 ticks a second
    }

    /**
     * Clears the canvas
     */
    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    /**
     * Render method which updates the contents of the canvas
     */
    public void render() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if ((tiles[y / TILE_SIZE][x / TILE_SIZE].isOccupied())) {
                    pixels[x + y * width] = Color.RED.getRGB();
                } else if (tiles[y / TILE_SIZE][x / TILE_SIZE].isWallTile()) {
                    pixels[x + y * width] = Color.BLUE.getRGB();
                } else if (tiles[y / TILE_SIZE][x / TILE_SIZE].isBoundary()) {
                    pixels[x + y * width] = Color.BLACK.getRGB();
                } else if (tiles[y / TILE_SIZE][x / TILE_SIZE].isBoundary()) {
                    pixels[x + y * width] = Color.CYAN.getRGB();
                } else {
                    pixels[x + y * width] = random.nextInt();
                }
            }
        }
    }

    /**
     * Alternate render method that off sets the render area by a specified amount
     *
     * @param xOffSet x offset value
     * @param yOffSet y offset value
     */
    public void render(int xOffSet, int yOffSet) {
        for (int y = 0; y < height; y++) {
            int yy = y + yOffSet;
            if (yy < 0 || yy >= height) continue;
            for (int x = 0; x < width; x++) {
                int xx = x + xOffSet;
                if (xx < 0 || xx >= width) continue;

                if ((tiles[y / TILE_SIZE][x / TILE_SIZE].isOccupied())) {
                    pixels[xx + yy * width] = Color.RED.getRGB();
                } else if (tiles[y / TILE_SIZE][x / TILE_SIZE].isWallTile()) {
                    pixels[xx + yy * width] = Color.BLUE.getRGB();
                } else if (tiles[y / TILE_SIZE][x / TILE_SIZE].isBoundary()) {
                    pixels[xx + yy * width] = Color.GREEN.getRGB();
                } else if (tiles[y / TILE_SIZE][x / TILE_SIZE].isBoundary()) {
                    pixels[xx + yy * width] = Color.CYAN.getRGB();
                } else {
                    pixels[xx + yy * width] = random.nextInt();
                }
            }
        }
    }

    /**
     * Getter
     *
     * @return Int array of pixels in canvas
     */
    public int[] getPixels() {
        return pixels;
    }
}
