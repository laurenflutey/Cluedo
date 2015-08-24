package view.gui.game;

import controller.GuiGameController;
import model.Player;
import view.gui.game.components.ButtonPanel;
import view.gui.game.components.GameCanvas;
import view.gui.game.components.GameMenu;
import view.gui.game.components.InformationPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * The GameFrame is the main JFrame component of the game. It contains all
 * subsequent components in the game, and is created when the
 * {@link controller.GuiGameController} calls it, after the
 * {@link view.gui.StartupFrame} has finished parsing the players for the game.
 *
 * The GameFrame is also runnable as the {@link GameCanvas}, which handles the
 * drawing of the game needs to be on a separate thread, so that it can
 * dynamically update when the user hovers over the board etc.
 *
 * @author Marcel van Workum
 * @author Reuben Puketapu
 */
public class GameFrame extends JFrame implements Runnable {

	/**
	 * The {@link GameCanvas} acts as the CANVAS where the actual game is drawn,
	 * and is run on a separate thread so that it can be dynamically updated
	 * when a user hovers
	 */
	private final GameCanvas CANVAS;

	private final GuiGameController GUIGAMECONTROLLER;

	// Swing Components
	private InformationPanel informationPanel;
	private ButtonPanel buttonPanel;
	private JPanel contentPane;
	private JMenuBar menuBar;

	// Game Dimensions
	private int width = 1330;
	private int height = 912;
	private Dimension gameDimensions;

	// The current player, ie the player whose turn it is
	private Player currentPlayer;

	/**
	 * Constructor
	 *
	 * Creates the main GameFrame which contains all the other components in the
	 * game window
	 *
	 * Tries to follow the window styling of the OS
	 *
	 * @param guiGameController
	 *            Game Frame knows about the GuiGameController
	 */
	public GameFrame(final GuiGameController guiGameController) {

		GUIGAMECONTROLLER = guiGameController;

		// Sets the window style to the systems default look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException
				| InstantiationException e) {
			e.printStackTrace(); // TODO display something meaningful
			System.out.println("Look and feel failed");
		}

		// Create the game window and sets min size to be the initial
		// dimension(720p)
		gameDimensions = new Dimension(width, height);
		setSize(gameDimensions);
		setMinimumSize(gameDimensions);
		setResizable(false); // TODO for now
		setTitle("Cluedo");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Initialises the JPanel content pane that holds all the other
		// components of the game window
		initContentPane();

		// creates the games menu and assigns to the frame
		menuBar = new GameMenu(this);
		setJMenuBar(menuBar);

		// Initialise the right hand side panels of the game window
		informationPanel = new InformationPanel(guiGameController, contentPane);
		buttonPanel = new ButtonPanel(guiGameController, contentPane, this);

		// Create the game CANVAS
		CANVAS = new GameCanvas(guiGameController, contentPane);

		// Position the window in the middle of the screen and request focus
		setLocationRelativeTo(null);
		requestFocus();
		setVisible(true);

		// Finally start the CANVAS thread
		start();
	}

	/**
	 * Creates a content panel that has a gridbag layout, which allows the
	 * components of the gui to be dynamically shifted around the frame.
	 */
	private void initContentPane() {
		contentPane = new JPanel();
		// contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 5, canvasWidth, 20, 482, 5 };
		gridBagLayout.rowHeights = new int[] { 30, canvasHeight - 120, 20, 100, 50 };
		contentPane.setLayout(gridBagLayout);
	}

	/**
	 * Repaints the content pane and menubar of the frame.
	 *
	 * @param g
	 *            Graphics to repaint
	 */
	@Override
	public void paint(Graphics g) {
		contentPane.repaint();
		menuBar.repaint();
	}

	/*
	 * This is the game CANVAS render pipeline. Don't ask why this isn't in the
	 * {@link GameCanvas} class. It should be, it would have been, but for what
	 * ever reason the buffer strategy refused to work when in the actual
	 * CANVAS.
	 *
	 * So instead here it is.
	 */

	/**
	 * A bufferedImage of the pixels that will be rendered to the screen. It
	 * accesses the static reference to the {@link GameCanvas} width and height.
	 * The image is a RGB Buffer.
	 */
	private BufferedImage image = new BufferedImage(GameCanvas.width, GameCanvas.height, BufferedImage.TYPE_INT_RGB);

	/**
	 * An array of pixels that makes up the game's CANVAS component. It is a
	 * single 1D array, as opposed to a 2D array.
	 */
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	// Thread goodies
	private Thread canvasThread;
	private boolean running = true;

	private int canvasWidth = 800;
	private int canvasHeight = 832;

	private int xOffSet = 0;
	private int yOffSet = 0;

	// Nano second time
	private static final double ns = 1000000000.0 / 60.0;

	/**
	 * Starts the {@link GameCanvas} thread
	 */
	public synchronized void start() {
		running = true;
		canvasThread = new Thread(this, "Game");
		canvasThread.start();
	}

	/**
	 * Handles the run loop of the CANVAS thread. The loop limits the game to 60
	 * ticks per second
	 */
	@Override
	public void run() {
		// Take note of time
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0;
		int frames = 0;
		int updates = 0;

		/* CANVAS loop */
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			/* Limit to 60 ticks per second */
			while (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;

			// Debug to the console the updates and frames per second
			// if (System.currentTimeMillis() - timer > 1000) {
			// timer += 1000;
			// System.out.println("Cluedo" + " | " + updates + " ups, " + frames
			// + " fps");
			// updates = 0;
			// frames = 0;
			// }
		}
	}

	/**
	 * Calls tick to components that need to update
	 */
	private void tick() {
		currentPlayer = GUIGAMECONTROLLER.getCurrentPlayer();
		CANVAS.tick();
	}

	/**
	 * Render pipe method that handles a triple buffered render strategy
	 */
	public void render() {
		// Gets the buffer strategy for the frame
		BufferStrategy bs = CANVAS.getBufferStrategy();
		if (bs == null) {
			CANVAS.createBufferStrategy(3);
			return;
		}

		// TODO I don't think this is needed
		// Clears the CANVAS by setting pixels to 0
		CANVAS.clear();

		// Delegates to the CANVAS to handle it's own rendering
		// -currentPlayer.getXPos(), -currentPlayer.getYPos()
		if (currentPlayer != null) {
			xOffSet = currentPlayer.getXPos();
			yOffSet = currentPlayer.getYPos();
		}
		CANVAS.render(-xOffSet, -yOffSet);

		// Copies the contents of the CANVAS pixels to the local pixel array
		System.arraycopy(CANVAS.getPixels(), 0, pixels, 0, pixels.length);

		// Finally lets pull the buffer to the front
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, canvasWidth, canvasHeight, null);
		g.dispose();
		bs.show();
	}

	public GameCanvas getCanvas() {
		return CANVAS;
	}

	/**
	 * @return the buttonPanel
	 */
	public ButtonPanel getButtonPanel() {
		return buttonPanel;
	}

	/**
	 * @return the informationPanel
	 */
	public InformationPanel getInformationPanel() {
		return informationPanel;
	}

	public void doRoll() {
		int roll = GUIGAMECONTROLLER.rollDice();
		informationPanel.rollDieAnimation(roll);
		buttonPanel.setRoll(false);
	}

}
