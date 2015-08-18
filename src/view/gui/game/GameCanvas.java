package view.gui.game;

import model.Board;
import model.Tile;

import javax.swing.*;
import java.awt.*;

/**
 * Game canvas, which is embedded in the JFrame of the {@link GameFrame}. The canvas is used to draw the current
 * state of the game board for the player to interact with.
 *
 * @author Marcel
 * @author Reuben
 */
public class GameCanvas extends Canvas {

    private final Board board;
    Tile[][] tiles;

    public GameCanvas(Board board, JPanel contentPane) {
        this.board = board;
        tiles = board.getTiles();

        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 1;
        gbc_panel.gridy = 1;
        gbc_panel.gridheight = 2;
        contentPane.add(this, gbc_panel);
    }

    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 26; j++) {
                if (tiles[i][j].isOccupied()) {
                    g.setColor(Color.BLUE);
                    g.fillRect(i * 32, j * 32, 32, 32);
                } else if (tiles[i][j].isWallTile()) {
                    g.setColor(Color.GREEN);
                    g.fillRect(i * 32, j * 32, 32, 32);
                } else if (tiles[i][j].isBoundary()){
                    g.setColor(Color.RED);
                    g.fillRect(i * 32, j * 32, 32, 32);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(i * 32, j * 32, 32, 32);
                }
            }
        }
    }
}
