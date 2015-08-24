package view.gui.game;

import controller.GuiGameController;
import model.Player;
import view.gui.game.components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

	// Confirmation dialog used on exit close
	private ConfirmationDialog closingConfirmation;

	// Game Dimensions
	private int width = 1312;
	private int height = 892;
	private Dimension gameDimensions;

	// The current player, ie the player whose turn it is
	private Player currentPlayer;
	private GridBagLayout gridBagLayout;
	private int columnBorder = 5;
	private int rowBorder = 30;
	private int middleBorder = 20;
	private int panelSize = 482;
	private int buttonPanelSize = 100;
	private int vertialBorder = 20;

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
		gameDimensions = new Dimension(width, height);
		setSize(gameDimensions);
		setMinimumSize(new Dimension(panelSize + columnBorder * 2 + middleBorder + 100, 800));
		//setResizable(false); // TODO for now
		setTitle("Cluedo");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

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
		CANVAS = new GameCanvas(guiGameController, contentPane, this);

		// Position the window in the middle of the screen and request focus
		setLocationRelativeTo(null);
		requestFocus();
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closingConfirmation = new ConfirmationDialog();
			}
		});

		// Finally start the CANVAS thread
		start();
	}

	/**
	 * Creates a content panel that has a gridbag layout, which allows the
	 * components of the gui to be dynamically shifted around the frame.
	 */
	private void initContentPane() {
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setBackground(new Color(0x2c3e50));
		gridBagLayout = new GridBagLayout();

		// Delegates to a method as these constraints will need to be updated as game resizes
		updateGridBagConstraints();

		contentPane.setLayout(gridBagLayout);
	}

	private void updateGridBagConstraints() {
		gridBagLayout.columnWidths = new int[] {columnBorder, canvasWidth, middleBorder, panelSize, columnBorder };
		gridBagLayout.rowHeights = new int[] {rowBorder, canvasHeight - (vertialBorder + buttonPanelSize),
				vertialBorder, buttonPanelSize, rowBorder };
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

	private int canvasWidth = width - columnBorder * 2 - middleBorder - panelSize;
	private int canvasHeight = height - (rowBorder * 2);

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
//			 if (System.currentTimeMillis() - timer > 1000) {
//			 timer += 1000;
//			 System.out.println("Cluedo" + " | " + updates + " ups, " + frames
//			 + " fps");
//			 updates = 0;
//			 frames = 0;
//			 }
		}
	}

	/**
	 * Calls tick to components that need to update
	 */
	private void tick() {
		currentPlayer = GUIGAMECONTROLLER.getCurrentPlayer();
		CANVAS.tick();
		updateSizing();
		updateGridBagConstraints();
	}

	private void updateSizing() {
		int newWidth = getWidth();
		int newHeight = getHeight();
		if(newWidth != gameDimensions.getWidth() || newHeight != gameDimensions.getHeight()) {
			gameDimensions = new Dimension(newWidth, newHeight);
			width = newWidth;
			height = newHeight;

			// Update canvas
			canvasWidth = width - columnBorder * 2 - middleBorder - panelSize;
			canvasHeight = height - 60;
		}
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

	public Dimension getGameDimensions() {
		return gameDimensions;
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

	//TODO fix this
	public void doRoll() {
		int roll = GUIGAMECONTROLLER.rollDice();
		informationPanel.rollDieAnimation(roll);
		buttonPanel.setRoll(false);
	}

	/**
	 * Changes the game state to false and closes the canvas thread.
	 */
	public void close() {
		running = false;
		try {
			canvasThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		dispose();
	}

	public int getCanvasWidth() {
		return canvasWidth;
	}

	public int getCanvasHeight() {
		return canvasHeight;
	}
}
