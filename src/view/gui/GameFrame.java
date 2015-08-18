package view.gui;

import model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Marcel on 12/08/15.
 */
public class GameFrame extends JFrame {

	private ButtonPanel buttonPanel;
	private int width = 1280;
	private int height = 720;

	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem selectBoardItem;
	private JMenuItem exitItem;
	private JMenu gameSettingsMenu;
	private JMenuItem guiModeItem;
	private JMenuItem textBasedModeItem;
	private JMenuItem toggleTextColouringItem;
	private JPanel informationPanel;

	private final GameCanvas canvas;
	private final Dimension gameDimensions;

	private final Board gameBoard;

	public GameFrame(final Board gameBoard) {

		this.gameBoard = gameBoard;

		// Sets the window style to the systems default look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException
				| InstantiationException e) {
			e.printStackTrace(); // TODO display something meaningful
			System.out.println("Look and feel failed");
		}

		canvas = new GameCanvas(gameBoard);

		gameDimensions = new Dimension(width, height);
		setSize(gameDimensions);
		setMinimumSize(gameDimensions);

		setTitle("Cluedo");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		initMenu();

		initContentPane();

		initCanvas();

		this.informationPanel = new InformationPanel(contentPane);

		this.buttonPanel = new ButtonPanel(contentPane);

		setLocationRelativeTo(null);
		requestFocus();
		setVisible(true);
	}

	private void initContentPane() {
		contentPane = new JPanel();
		// contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 5, 768, 502, 5 };
		gbl_contentPane.rowHeights = new int[] { 30, 540, 100, 50 };
		contentPane.setLayout(gbl_contentPane);
	}

	private void initCanvas() {
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		gbc_panel.gridheight = 2;
		contentPane.add(canvas, gbc_panel);
	}

	private void initMenu() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		menuBar.setBorderPainted(false);

		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		selectBoardItem = new JMenuItem("Select Board");
		fileMenu.add(selectBoardItem);

		exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.out.println("Good bye.");
				System.exit(0);
			}
		});
		fileMenu.add(exitItem);

		gameSettingsMenu = new JMenu("Game Settings");
		menuBar.add(gameSettingsMenu);

		guiModeItem = new JMenuItem("GUI Mode");
		gameSettingsMenu.add(guiModeItem);

		textBasedModeItem = new JMenuItem("Text-Base Mode");
		gameSettingsMenu.add(textBasedModeItem);

		toggleTextColouringItem = new JMenuItem("Toggle Text Colouring");
		gameSettingsMenu.add(toggleTextColouringItem);
	}

	@Override
	public void paint(Graphics g) {
		// canvas.repaint();
		contentPane.repaint();
		menuBar.repaint();
	}
}
