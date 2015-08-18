package view.gui.game;

import model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * The GameFrame is the main JFrame component of the game. It contains all subsequent components in the game, and is
 * created when the {@link controller.GuiGameController} calls it, after the {@link view.gui.StartupFrame} has finished
 * parsing the players for the game.
 *
 * The GameFrame is also runnable as the {@link GameCanvas}, which handles the drawing of the game needs to be on a
 * separate thread, so that it can dynamically update when the user hovers over the board etc.
 *
 * @author Marcel van Workum
 * @author Reuben Puketapu
 */
public class GameFrame extends JFrame implements Runnable{

    /**
     * The {@link GameCanvas} acts as the canvas where the actual game is drawn, and is run on a separate thread
     * so that it can be dynamically updated when a user hovers
     */
    private final GameCanvas canvas;

    // Swing Components
    private InformationPanel informationPanel;
    private ButtonPanel buttonPanel;
    private JPanel contentPane;
    private JMenuBar menuBar;

    // Game Dimensions
    private int width = 1280;
    private int height = 720;
    private Dimension gameDimensions;

    /**
     * Constructor
     *
     * Creates the main GameFrame which contains all the other components in the game window
     *
     * Tries to follow the window styling of the OS
     *
     * @param gameBoard Game board passed into the constructor
     */
    public GameFrame(final Board gameBoard){
        // Sets the window style to the systems default look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace(); //TODO display something meaningful
            System.out.println("Look and feel failed");
        }

        // Create the game window and sets min size to be the initial dimension(720p)
        gameDimensions = new Dimension(width, height);
        setSize(gameDimensions);
        setMinimumSize(gameDimensions);
        setTitle("Cluedo");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Initialises the JPanel content pane that holds all the other components of the game window
        initContentPane();

        // creates the games menu and assigns to the frame
        menuBar = new GameMenu();
        setJMenuBar(menuBar);

        // Initialise the right hand side panels of the game window
        informationPanel = new InformationPanel(contentPane);
        buttonPanel = new ButtonPanel(contentPane);

        // Create the game canvas
        canvas = new GameCanvas(gameBoard, contentPane);

        // Position the window in the middle of the screen and request focus
        setLocationRelativeTo(null);
        requestFocus();
        setVisible(true);

        // Finally start the canvas thread
        start();
    }

    /**
     * Creates a content panel that has a gridbag layout, which allows the components of the gui to be
     * dynamically shifted around the frame.
     */
    private void initContentPane() {
        contentPane = new JPanel();
        //contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{5, 768, 20, 482, 5};
        gridBagLayout.rowHeights = new int[]{30, 520, 20, 100, 50};
        contentPane.setLayout(gridBagLayout);
    }

    /**
     * Repaints the content pane and menubar of the frame.
     *
     * @param g Graphics to repaint
     */
    @Override
    public void paint(Graphics g) {
        //canvas.repaint();
        contentPane.repaint();
        menuBar.repaint();
    }

    /*
     * This is the game canvas render pipeline. Don't ask why this isn't in the {@link GameCanvas} class.
     * It should be, it would have been, but for what ever reason the buffer strategy refused to work when in the actual
     * canvas.
     *
     * So instead here it is.
     * */

    /**
     * A bufferedImage of the pixels that will be rendered to the screen. It accesses the static reference to the
     * {@link GameCanvas} width and height. The image is a RGB Buffer.
     */
    private BufferedImage image = new BufferedImage(GameCanvas.width, GameCanvas.height, BufferedImage.TYPE_INT_RGB);

    /**
     * An array of pixels that makes up the game's canvas component. It is a single 1D array, as opposed to a 2D array.
     */
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    // Thread goodies
    private Thread canvasThread;
    private boolean running = true;

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
     * Handles the run loop of the canvas thread. The loop limits the game to 60 ticks per second
     */
    @Override
    public void run() {
        // Take note of time
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0;
        int frames = 0;
        int updates = 0;

        /* canvas loop*/
        while (running) {
            long now = System.nanoTime();
            delta += (now-lastTime) / ns;
            lastTime = now;
            /* Limit to 60 ticks per second*/
            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            // Debug to the console the updates and frames per second
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("Cluedo" + "  |  " + updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }
        }
    }

    /**
     * Calls tick to components that need to update
     */
    private void tick() {
        canvas.tick();
    }

    /**
     * Render pipe method that handles a triple buffered render strategy
     */
    public void render() {
        // Gets the buffer strategy for the frame
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }

        //TODO I don't think this is needed
        // Clears the canvas by setting pixels to 0
        //canvas.clear();

        // Delegates to the canvas to handle it's own rendering
        canvas.render();

        // Copies the contents of the canvas pixels to the local pixel array
        System.arraycopy(canvas.getPixels(), 0, pixels, 0, pixels.length);

        // Finally lets pull the buffer to the front
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }
}
