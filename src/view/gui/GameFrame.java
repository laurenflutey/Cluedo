package view.gui;

import model.Board;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Marcel on 12/08/15.
 */
public class GameFrame extends JFrame {

    private final JPanel contentPane;
    private final JMenuBar menuBar;
    private final JMenu fileMenu;
    private final JMenuItem selectBoardItem;
    private final JMenuItem exitItem;
    private final JMenu gameSettingsMenu;
    private final JMenuItem guiModeItem;
    private final JMenuItem textBasedModeItem;
    private final JMenuItem toggleTextColouringItem;
    private final JPanel informationPanel;
    private final JPanel playerHandPanel;
    private final JPanel minimapPanel;

    private final GameCanvas canvas;
    private final Dimension gameDimensions;

    private final Board gameBoard;

    public GameFrame(Board gameBoard){

        this.gameBoard = gameBoard;

        // Sets the window style to the systems default look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace(); //TODO display something meaningful
            System.out.println("Look and feel failed");
        }

        canvas = new GameCanvas(gameBoard);

        gameDimensions = new Dimension(615, 576);
        setSize(gameDimensions);
        setMinimumSize(gameDimensions);


        setTitle("Cluedo");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        menuBar.setBorderPainted(false);

        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        selectBoardItem = new JMenuItem("Select Board");
        fileMenu.add(selectBoardItem);

        exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);

        gameSettingsMenu = new JMenu("Game Settings");
        menuBar.add(gameSettingsMenu);

        guiModeItem = new JMenuItem("GUI Mode");
        gameSettingsMenu.add(guiModeItem);

        textBasedModeItem = new JMenuItem("Text-Base Mode");
        gameSettingsMenu.add(textBasedModeItem);

        toggleTextColouringItem = new JMenuItem("Toggle Text Colouring");
        gameSettingsMenu.add(toggleTextColouringItem);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{5, 400, 200, 10};
        gbl_contentPane.rowHeights = new int[]{30, 416, 100, 30};
        contentPane.setLayout(gbl_contentPane);

        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.insets = new Insets(0, 0, 5, 5);
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 1;
        gbc_panel.gridy = 1;
        contentPane.add(canvas, gbc_panel);


        canvas.repaint(); //TODO

        informationPanel = new JPanel();
        informationPanel.setBackground(Color.BLUE.darker());
        GridBagConstraints gbc_panel2 = new GridBagConstraints();
        gbc_panel2.insets = new Insets(0, 0, 5, 0);
        gbc_panel2.fill = GridBagConstraints.BOTH;
        gbc_panel2.gridx = 2;
        gbc_panel2.gridy = 1;
        contentPane.add(informationPanel, gbc_panel2);

        playerHandPanel = new JPanel();
        playerHandPanel.setBackground(Color.GREEN.darker().darker().darker());
        GridBagConstraints gbc_panel3 = new GridBagConstraints();
        gbc_panel3.insets = new Insets(0, 0, 0, 5);
        gbc_panel3.fill = GridBagConstraints.BOTH;
        gbc_panel3.gridx = 1;
        gbc_panel3.gridy = 2;
        contentPane.add(playerHandPanel, gbc_panel3);
        playerHandPanel.setLayout(new GridLayout(1, 0, 0, 0));

        minimapPanel = new JPanel();
        minimapPanel.setBackground(Color.RED.darker());
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 2;
        gbc_panel_1.gridy = 2;
        contentPane.add(minimapPanel, gbc_panel_1);


        setLocationRelativeTo(null);
        setVisible(true);
        requestFocus();
    }

    @Override
    public void paint(Graphics g) {
        canvas.repaint();
        contentPane.repaint();
    }
}
