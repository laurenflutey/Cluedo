package view.gui.game.components;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel that contains all of the user action buttons. The JPanel will dynamically change what buttons are displayed
 * based on the moves that a player can make.
 */
public class ButtonPanel extends JPanel {

    private final JPanel parent;

    public ButtonPanel(final JPanel contentPane) {
        parent = contentPane;
        setBackground(Color.RED.darker());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;

        parent.add(this, gridBagConstraints);
    }
}
