package view.gui.game;

import model.Board;
import model.Tile;

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

    private static final int CANVAS_SIZE = 320;
    private final Board BOARD;
    private final Random random = new Random();
    private Tile[][] tiles;
    private int width = 768;
    private int height = 640;

    private boolean running;


    private Thread gameThread;
    private Screen screen;
    public int[] pixels;


    public GameCanvas(Board board, JPanel contentPane) {
        this.BOARD = board;
        tiles = board.getTiles();


        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 1;
        gbc_panel.gridy = 1;
        gbc_panel.gridheight = 2;
        contentPane.add(this, gbc_panel);

        screen = new Screen(width, height);

        pixels = new int[width * height];

    }

    public void tick() {
        //TODO Create image raster and convert to buffered image
        //TODO aim for 60 fps, and about 120 ticks a second
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    public void render(int xOffset, int yOffset) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[x + y * width] = random.nextInt();
            }
        }
    }

//    @Override
//    public void paint(Graphics g) {
//        for (int i = 0; i < 12; i++) {
//            for (int j = 0; j < 12; j++) {
//                if (tiles[i][j].isOccupied()) {
//                    for (int x = 0; x < 32; x++) {
//                        for (int y = 0; y < 32; y++) {
//                        }
//                    }
//                } else if (tiles[i][j].isWallTile()) {
//                    for (int x = 0; x < 32; x++) {
//                        for (int y = 0; y < 32; y++) {
//                        }
//                    }
//                } else if (tiles[i][j].isBoundary()){
//                    for (int x = 0; x < 32; x++) {
//                        for (int y = 0; y < 32; y++) {
//                        }
//                    }
//                } else {
//                    for (int x = 0; x < 32; x++) {
//                        for (int y = 0; y < 32; y++) {
//                        }
//                    }
//                }
//            }
//        }
//    }
}
