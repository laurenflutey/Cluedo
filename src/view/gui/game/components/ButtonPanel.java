package view.gui.game.components;

import javax.swing.*;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.sun.istack.internal.FinalArrayList;

import controller.GuiGameController;
import view.gui.game.GameFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JPanel that contains all of the user action buttons. The JPanel will
 * dynamically change what buttons are displayed based on the moves that a
 * player can make.
 */
public class ButtonPanel extends JPanel  {

	private final JPanel parent;

	final private JButton suggButton;
	final private JButton accuButton;
	final private JButton rollButton;
	final private JButton secretButton;

	private final GuiGameController gameController;

	private JButton endButton;

	public ButtonPanel(final GuiGameController guiGameController, final JPanel contentPane, final GameFrame frame) {
		this.gameController = guiGameController;
		this.parent = contentPane;
		// setBackground(Color.RED.darker().darker());

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 3;
		rollButton = new JButton("Roll");
		rollButton.setBounds(17, 105, 105, 82);
		rollButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.doRoll();
			}
		});
		add(rollButton);

		accuButton = new JButton("Accuse");
		// accuButton.setBounds(134, 105, 105, 200);
		accuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameController.accuse();
			}
		});
		add(accuButton);

		suggButton = new JButton("Suggest");
		// suggButton.setBounds(251, 105, 105, 83);
		suggButton.setEnabled(false);
		suggButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameController.suggest();
				suggButton.setEnabled(false);
				secretButton.setEnabled(false);
				rollButton.setEnabled(false);
			}
		});
		add(suggButton);

		secretButton = new JButton("Secret Room");
		// secretButton.setBounds(368, 105, 105, 82);
		secretButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameController.moveToSecretRoom();
				setSecretRoom(false);
			}
		});
		add(secretButton);
		
		endButton = new JButton("End Turn");
		// secretButton.setBounds(368, 105, 105, 82);
		endButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameController.incrementPlayerTurn();
			}
		});
		add(endButton);

		parent.add(this, gridBagConstraints);

	}

	public void setSecretRoom(boolean toggle) {
		secretButton.setEnabled(toggle);
	}

	public void setRoll(boolean toggle) {
		rollButton.setEnabled(toggle);
	}

	public void setSuggest(boolean toggle) {
		suggButton.setEnabled(toggle);
	}

	public void setAccuse(boolean toggle) {
		accuButton.setEnabled(toggle);

	}

}
