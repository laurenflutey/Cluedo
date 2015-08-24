package view.gui.game.components;

import view.gui.game.GameFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Marcel on 18/08/15.
 */
public class GameMenu extends JMenuBar {

    private final JMenu fileMenu;
    private final JMenuItem selectBoardItem;
    private final JMenuItem exitItem;
    private final JMenu gameSettingsMenu;
    private final JMenuItem guiModeItem;
    private final JMenuItem textBasedModeItem;
    private final JMenuItem toggleTextColouringItem;
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

        selectBoardItem = new JMenuItem("New Game");
        fileMenu.add(selectBoardItem);

        exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);

        gameSettingsMenu = new JMenu("Game Settings");
        add(gameSettingsMenu);

        guiModeItem = new JMenuItem("GUI Mode");
        gameSettingsMenu.add(guiModeItem);

        textBasedModeItem = new JMenuItem("Text-Base Mode");
        gameSettingsMenu.add(textBasedModeItem);

        toggleTextColouringItem = new JMenuItem("Toggle Text Colouring");
        gameSettingsMenu.add(toggleTextColouringItem);
    }
}
