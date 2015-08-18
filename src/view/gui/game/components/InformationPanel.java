package view.gui.game.components;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel containing all of the game information. This includes includes the players cards, the dice roll and an
 * image of the players character.
 *
 * @author Marcel
 */
public class InformationPanel extends JPanel {

    public InformationPanel(final JPanel contentPane) {
        setBackground(Color.BLUE.darker());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;

        contentPane.add(this, gridBagConstraints);
    }
}
