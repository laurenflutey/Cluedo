package view.gui.game.components;

import controller.GuiGameController;
import view.gui.game.GameFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Game menu for the {@link GameFrame}. The menu is pretty standard and probably somewhat unimplemented.
 *
 * @author Marcel
 * @author Reuben
 */
public class GameMenu extends JMenuBar {

    /**
     * Component containing the menu bar
     */
    private final GameFrame PARENT;

    // Menus
    private JMenu fileMenu;
    private JMenu appearanceMenu;
    private JMenu themeMenu;

    // Menu items
    private JMenuItem selectBoardItem;
    private JMenuItem exitItem;
    private JMenuItem toggleZoomItem;
    private JMenuItem theme1;
    private JMenuItem theme2;
    private JMenuItem theme3;
    private JMenu soundMenu;
    private JMenuItem toggleSoundItem;

    /**
     * Constructor
     *
     * Creates the menu components and delegates to init methods to create action listeners for each
     *
     * @param PARENT Parent container of the menu
     */
    public GameMenu(final GameFrame PARENT) {
        this.PARENT = PARENT;

        // Remove ugly border
        setBorderPainted(false);

        initFileMenu();

        initAppearanceMenu();

        initSoundMenu();
    }

    /**
     * Creates the sound menu items and initialises their action listeners
     */
    private void initSoundMenu() {
        soundMenu = new JMenu("Sound");
        add(soundMenu);

        toggleSoundItem = new JMenuItem("Toggle Sound"); //TODO implement sound
        soundMenu.add(toggleSoundItem);
    }

    /**
     * Menu that handles the multiple themes of the game, as well as the tile sizes.
     *
     * A theme change calls to the {@link GameCanvas} class to reload a different set of spritesheets
     */
    private void initAppearanceMenu() {
        // Create menu
        appearanceMenu = new JMenu("Appearance");
        add(appearanceMenu);

        // Toggle zoom item
        toggleZoomItem = new JMenuItem(new AbstractAction("Toggle Zoom") {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Delegate to the canvas to toggle its size
                PARENT.getCanvas().toggleTileSize();
            }
        });
        appearanceMenu.add(toggleZoomItem);

        // Creates the theme menu, and the theme items
        themeMenu = new JMenu("Theme");
        appearanceMenu.add(themeMenu);

        theme1 = new JMenuItem("Theme 1"); //TODO implement multiple themes
        theme2 = new JMenuItem("Theme 2");
        theme3 = new JMenuItem("Theme 3");

        themeMenu.add(theme1);
        themeMenu.add(theme2);
        themeMenu.add(theme3);
    }

    /**
     * Handles the closing and new game logic of the game. The new game delegates to eh {@link GuiGameController}
     * to handle the closing of its components threads.
     */
    private void initFileMenu() {
        fileMenu = new JMenu("File");
        add(fileMenu);

        selectBoardItem = new JMenuItem(new AbstractAction("New Game") {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiGameController.closeGame();
                new GuiGameController();
            }
        });
        fileMenu.add(selectBoardItem);

        exitItem = new JMenuItem(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConfirmationDialog();
            }
        });
        fileMenu.add(exitItem);
    }
}
