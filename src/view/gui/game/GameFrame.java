package view.gui.game;

import model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Created by Marcel on 12/08/15.
 */
public class GameFrame extends JFrame implements Runnable{

    private final InformationPanel informationPanel;
    private final Screen screen;
    private ButtonPanel buttonPanel;
    private int width = 1280;
    private int height = 720;

    private BufferedImage image = new BufferedImage(768, 640, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    private JPanel contentPane;
    private JMenuBar menuBar;

    private final GameCanvas canvas;
    private final Dimension gameDimensions;

    private final Board gameBoard;

    private boolean running = true;
    private Thread gameThread;


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

        
        screen = new Screen(width, height);
        setVisible(true);
        start();



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

    public synchronized void start() {
        running = true;
        gameThread = new Thread(this, "Game");
        gameThread.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int frames = 0;
        int updates = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now-lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

//            if (System.currentTimeMillis() - timer > 1000) {
//                timer += 1000;
//                setTitle("Cluedo" + "  |  " + updates + " ups, " + frames + " fps");
//                updates = 0;
//                frames = 0;
//            }
        }
    }

    private void tick() {

    }

    public void render() {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }

        canvas.clear();
        canvas.render(0, 0);

        System.arraycopy(canvas.pixels, 0, pixels, 0, pixels.length);

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }
}
