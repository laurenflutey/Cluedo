package view.gui.game;

import model.Board;
import model.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Game canvas, which is embedded in the JFrame of the {@link GameFrame}. The canvas is used to draw the current
 * state of the game BOARD for the player to interact with.
 *
 * @author Marcel
 * @author Reuben
 */
public class GameCanvas extends Canvas {

    private final Board BOARD;
    private Tile[][] tiles;
    private BufferedImage bufferedImage;
    private int[][] pixels;

    public GameCanvas(Board board, JPanel contentPane) {
        this.BOARD = board;
        tiles = board.getTiles();

        pixels = new int[800][832];

        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 1;
        gbc_panel.gridy = 1;
        gbc_panel.gridheight = 2;
        contentPane.add(this, gbc_panel);
    }

    public void tick() {
        //TODO Create image raster and convert to buffered image
        //TODO aim for 60 fps, and about 120 ticks a second
    }

    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                if (tiles[i][j].isOccupied()) {
                    for (int x = 0; x < 32; x++) {
                        for (int y = 0; y < 32; y++) {
                            pixels[i * 32 + x][j * 32 + y] = Color.RED.getRGB();
                            g.setColor(Color.RED);
                            g.drawRect(i * 32 + x, j * 32 + y, 1, 1);
                        }
                    }
                } else if (tiles[i][j].isWallTile()) {
                    for (int x = 0; x < 32; x++) {
                        for (int y = 0; y < 32; y++) {
                            pixels[i * 32 + x][j * 32 + y] = Color.BLUE.getRGB();
                            g.setColor(Color.BLUE);
                            g.drawRect(i * 32 + x, j * 32 + y, 1, 1);
                        }
                    }
                } else if (tiles[i][j].isBoundary()){
                    for (int x = 0; x < 32; x++) {
                        for (int y = 0; y < 32; y++) {
                            pixels[i * 32 + x][j * 32 + y] = Color.GREEN.getRGB();
                            g.setColor(Color.GREEN);
                            g.drawRect(i * 32 + x, j * 32 + y, 1, 1);
                        }
                    }
                } else {
                    for (int x = 0; x < 32; x++) {
                        for (int y = 0; y < 32; y++) {
                            pixels[i * 32 + x][j * 32 + y] = Color.CYAN.getRGB();
                            g.setColor(Color.CYAN);
                            g.drawRect(i * 32 + x, j * 32 + y, 1, 1);
                        }
                    }
                }
            }
        }
    }
}
