package view.gui.game.components;

import javax.swing.*;

import controller.GuiGameController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JPanel that contains all of the user action buttons. The JPanel will
 * dynamically change what buttons are displayed based on the moves that a
 * player can make.
 */
public class ButtonPanel extends JPanel {

	private final JPanel parent;

	private final GuiGameController gameController;

	public ButtonPanel(final GuiGameController guiGameController, final JPanel contentPane) {
		this.gameController = guiGameController;
		this.parent = contentPane;
		// setBackground(Color.RED.darker().darker());

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 3;
		final JButton rollButton = new JButton("Roll");
		rollButton.setBounds(17, 105, 105, 82);
		rollButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO add what roll does
				System.out.println(guiGameController.rollDice());
			}
		});
		add(rollButton);

		JButton accuButton = new JButton("Accuse");
		accuButton.setBounds(134, 105, 105, 200);
		accuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		add(accuButton);

		JButton suggButton = new JButton("Suggest");
		suggButton.setBounds(251, 105, 105, 83);
		suggButton.setEnabled(false);
		suggButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		add(suggButton);

		JButton secretButton = new JButton("Secret Room");
		secretButton.setBounds(368, 105, 105, 82);
		secretButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		add(secretButton);

		parent.add(this, gridBagConstraints);

	}
}
