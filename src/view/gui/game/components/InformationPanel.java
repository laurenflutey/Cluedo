package view.gui.game.components;

import javax.imageio.ImageIO;
import javax.sound.midi.VoiceStatus;
import javax.swing.*;

import controller.GuiGameController;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * JPanel containing all of the game information. This includes includes the
 * players cards, the dice roll and an image of the players character.
 *
 * @author Marcel
 */
public class InformationPanel extends JPanel {

	private GuiGameController guiGameController;
	private JPanel parentJPanel;
	private BufferedImage img;
	private JLabel lblNewLabel;
	private ImageIcon imageIcon;
	private JLabel lblName;
	private JLabel lblCharacter;

	public InformationPanel(final GuiGameController guiGameController, final JPanel contentPane) {
		this.guiGameController = guiGameController;
		this.parentJPanel = contentPane;
		// setBackground(Color.BLUE.darker());
		setLayout(null);

		lblNewLabel = new JLabel();
		lblNewLabel.setBounds(23, 27, 140, 140);

		img = null;
		try {
			img = ImageIO.read(new File("images/characters/green/green.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(140, 140, Image.SCALE_SMOOTH);

		imageIcon = new ImageIcon(dimg);

		lblNewLabel.setIcon(imageIcon);
		add(lblNewLabel);

		lblName = new JLabel(guiGameController.getCurrentPlayer().getPlayerName());
		lblName.setBounds(166, 27, 261, 16);
		add(lblName);

		lblCharacter = new JLabel(guiGameController.getCurrentPlayer().getName());
		lblCharacter.setBounds(166, 55, 261, 16);
		add(lblCharacter);

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;

		contentPane.add(this, gridBagConstraints);
	}

	public void displayIcon() {

	}
	
	public void setName(String text){
		lblName.setText(text);
	}
	
	public void setChar(String text){
		lblCharacter.setText(text);
	}
}
