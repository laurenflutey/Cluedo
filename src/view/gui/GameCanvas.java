package view.gui;

import model.Board;
import model.Tile;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

    public GameCanvas(Board board) {
        this.board = board;
        tiles = board.getTiles();

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Tile X:" + e.getX()/32);
                System.out.println("Tile Y:" + e.getY()/32);
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
