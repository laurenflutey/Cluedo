package view.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Marcel on 17/08/15.
 */
public class InformationPanel extends JPanel {

    public InformationPanel(final JPanel contentPane) {
        setBackground(Color.BLUE.darker());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;

        contentPane.add(this, gridBagConstraints);
    }
}
