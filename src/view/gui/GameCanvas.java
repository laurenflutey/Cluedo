package view.gui;

import model.Board;
import model.Tile;

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

    public GameCanvas(Board board) {
        this.board = board;
    }

    @Override
    public void paint(Graphics g) {
        Tile[][] tiles = board.getTiles();
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 26; j++) {

                if (tiles[i][j].isOccupied()) {
                    g.setColor(Color.GREEN);
                    g.fillRect(i * 16, j * 16, 16, 16);
//                    if (tiles[i][j].getPlayer() != null) {
//                        java.net.URL imageURL = GameCanvas.class.getResource("images/Test.png");
//                        Image image = null;
//                        try {
//                            image = ImageIO.read(imageURL);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        g.drawImage(image, i*16, j*16, null);
//                    }
                } else if (tiles[i][j].isWallTile()) {
                    g.setColor(Color.BLACK);
                    g.fillRect(i * 16, j * 16, 16, 16);
                } else if (tiles[i][j].isBoundary()){
                    g.setColor(Color.RED);
                    g.fillRect(i * 16, j * 16, 16, 16);
                } else {
                    g.setColor(Color.blue);
                    g.fillRect(i * 16, j * 16, 16, 16);
                }

            }
        }
    }
}
