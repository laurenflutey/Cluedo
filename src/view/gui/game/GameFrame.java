package view.gui.game;

import model.Board;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Marcel on 12/08/15.
 */
public class GameFrame extends JFrame {

    private final InformationPanel informationPanel;
    private ButtonPanel buttonPanel;
    private int width = 1280;
    private int height = 720;

    private JPanel contentPane;
    private JMenuBar menuBar;

    private final GameCanvas canvas;
    private final Dimension gameDimensions;

    private final Board gameBoard;

    public GameFrame(final Board gameBoard){

        this.gameBoard = gameBoard;

        // Sets the window style to the systems default look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace(); //TODO display something meaningful
            System.out.println("Look and feel failed");
        }



        gameDimensions = new Dimension(width, height);
        setSize(gameDimensions);
        setMinimumSize(gameDimensions);


        setTitle("Cluedo");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        menuBar = new GameMenu();
        setJMenuBar(menuBar);

        initContentPane();

        canvas = new GameCanvas(gameBoard, contentPane);

        this.informationPanel = new InformationPanel(contentPane);

        this.buttonPanel = new ButtonPanel(contentPane);


        setLocationRelativeTo(null);
        requestFocus();
        setVisible(true);
    }

    private void initContentPane() {
        contentPane = new JPanel();
        //contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{5, 768, 502, 5};
        gbl_contentPane.rowHeights = new int[]{30, 540, 100, 50};
        contentPane.setLayout(gbl_contentPane);
    }

    @Override
    public void paint(Graphics g) {
        //canvas.repaint();
        contentPane.repaint();
        menuBar.repaint();
    }
}
