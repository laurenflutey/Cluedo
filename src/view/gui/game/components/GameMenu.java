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

    private final JMenu fileMenu;
    private final JMenuItem selectBoardItem;
    private final JMenuItem exitItem;
    private final JMenu appearanceMenu;

    private final GameFrame PARENT;

    public GameMenu(final GameFrame PARENT) {

        this.PARENT = PARENT;

        setBorderPainted(false);

        fileMenu = new JMenu("File");
        add(fileMenu);

        appearanceMenu = new JMenu("Appearance");
        add(appearanceMenu);

        JMenuItem toggleZoomItem = new JMenuItem(new AbstractAction("Toggle Zoom") {
            @Override
            public void actionPerformed(ActionEvent e) {
                PARENT.getCanvas().toggleTileSize();
            }
        });
        appearanceMenu.add(toggleZoomItem);

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
                System.exit(0);
            }
        });
        fileMenu.add(exitItem);

        JMenu themeMenu = new JMenu("Theme");
        appearanceMenu.add(themeMenu);

        JMenuItem theme1 = new JMenuItem("Theme 1");
        JMenuItem theme2 = new JMenuItem("Theme 2");
        JMenuItem theme3 = new JMenuItem("Theme 3");

        themeMenu.add(theme1);
        themeMenu.add(theme2);
        themeMenu.add(theme3);

        JMenu soundMenu = new JMenu("Sound");
        add(soundMenu);

        JMenuItem toggleSound = new JMenuItem("Toggle Sound");
        soundMenu.add(toggleSound);
    }
}
