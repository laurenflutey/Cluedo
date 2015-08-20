package view.gui.game.components;

import controller.GuiGameController;
import model.Tile;
import view.gui.game.GameFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
    public static int width = 800;

    /**
     * Static dimension representing height of the GameCanvas
     */
    public static int height = 832;

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

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("e.getX() = " + e.getX());
                System.out.println("e.getY() = " + e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        load();

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
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if ((tiles[x / TILE_SIZE][y / TILE_SIZE].isOccupied())) {
                    pixels[x + y * width] = Color.RED.getRGB();
                } else if (tiles[x / TILE_SIZE][y / TILE_SIZE].isWallTile()) {
                    pixels[x + y * width] = Color.BLUE.getRGB();
                } else if (tiles[x / TILE_SIZE][y / TILE_SIZE].isBoundary()) {
                    pixels[x + y * width] = Color.GREEN.getRGB();
                } else if (tiles[x / TILE_SIZE][y / TILE_SIZE].isBoundary()) {
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

                if ((tiles[x / TILE_SIZE][y / TILE_SIZE].isOccupied())) {

                } else if (tiles[x / TILE_SIZE][y / TILE_SIZE].isWallTile()) {
                    pixels[xx + yy * width] = wall32Pixels[xx % TILE_SIZE + yy % TILE_SIZE * TILE_SIZE];
                } else if (tiles[x / TILE_SIZE][y / TILE_SIZE].isBoundary()) {
                } else {
                    pixels[xx + yy * width] = floor32Pixels[xx % TILE_SIZE + yy % TILE_SIZE * TILE_SIZE];
                }
            }
        }
    }

    /**
     * Loads the sprite sheet by trying to read a buffered image from a png file
     * and storing that raster into the pixels array of the sprite sheet.
     */
    private void load() {
        try {
            BufferedImage image = ImageIO.read(GameCanvas.class.getResource("/tiles/wall-32.png"));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, wall32Pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedImage image = ImageIO.read(GameCanvas.class.getResource("/tiles/wall-64.png"));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, wall64Pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedImage image = ImageIO.read(GameCanvas.class.getResource("/tiles/floor-32.png"));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, floor32Pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedImage image = ImageIO.read(GameCanvas.class.getResource("/tiles/floor-64.png"));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, floor64Pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
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

    private static int[] wall32Pixels = new int[1024];
    private static int[] floor32Pixels = new int[1024];
    private static int[] wall64Pixels = new int[4096];
    private static int[] floor64Pixels = new int[4096];
}
