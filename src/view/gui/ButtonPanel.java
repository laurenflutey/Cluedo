package view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Created by Marcel on 17/08/15.
 */
public class ButtonPanel extends JPanel {

    private final JPanel parent;

    public ButtonPanel(final JPanel contentPane) {
        parent = contentPane;
        setBackground(Color.RED.darker());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;

        parent.add(this, gridBagConstraints);

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.println("Hello");
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                System.out.println("Hello");
            }
        });
    }
}
