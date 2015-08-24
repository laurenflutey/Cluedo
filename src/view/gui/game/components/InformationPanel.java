package view.gui.game.components;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.GuiGameController;

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
	private JLabel imageLabel;
	private ImageIcon imageIcon;
	private JLabel nameLabel;
	private JLabel characterLabel;
	private JLabel rollLabel;
	private JLabel roomInfoLabel;
	private JLabel roomNameLabel;
	private JLabel contentsLabel;
	private JLabel weaponsLabel;
	private JLabel charactersLabel;
	private Component secretNameLabel;
	private JLabel dieRollImage;

	private Set<BufferedImage> dieSet;

	public InformationPanel(final GuiGameController guiGameController, final JPanel contentPane) {

		initDie();
		this.guiGameController = guiGameController;
		this.parentJPanel = contentPane;
		// setBackground(Color.BLUE.darker());
		setLayout(null);

		imageLabel = new JLabel();
		imageLabel.setBounds(23, 27, 140, 140);

		img = guiGameController.getCurrentPlayerImage();
		Image dimg = img.getScaledInstance(140, 140, Image.SCALE_SMOOTH);

		imageIcon = new ImageIcon(dimg);

		imageLabel.setIcon(imageIcon);
		add(imageLabel);

		characterLabel = new JLabel(guiGameController.getCurrentPlayer().getName());
		characterLabel.setBounds(239, 55, 261, 16);
		add(characterLabel);

		nameLabel = new JLabel(guiGameController.getCurrentPlayer().getPlayerName());
		nameLabel.setBounds(239, 27, 261, 16);
		add(nameLabel);

		rollLabel = new JLabel("Roll: ");
		rollLabel.setBounds(239, 89, 113, 16);
		add(rollLabel);

		roomInfoLabel = new JLabel("Room Information ");
		roomInfoLabel.setBounds(23, 179, 156, 16);
		add(roomInfoLabel);

		roomNameLabel = new JLabel("name");
		roomNameLabel.setBounds(23, 207, 61, 16);
		add(roomNameLabel);

		secretNameLabel = new JLabel("secret");
		secretNameLabel.setBounds(23, 235, 61, 16);
		add(secretNameLabel);

		contentsLabel = new JLabel("Contents:");
		contentsLabel.setBounds(23, 263, 61, 16);
		add(contentsLabel);

		weaponsLabel = new JLabel("Weapons");
		weaponsLabel.setBounds(23, 291, 225, 16);
		add(weaponsLabel);

		charactersLabel = new JLabel("Characters");
		charactersLabel.setBounds(23, 319, 225, 16);
		add(charactersLabel);

		dieRollImage = new JLabel("DieRoll");
		dieRollImage.setBounds(23, 546, 100, 100);
		add(dieRollImage);

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;

		contentPane.add(this, gridBagConstraints);
	}

	private void initDie() {
		dieSet = new HashSet<>();

		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("images/dice.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 18; j++) {
				dieSet.add(image.getSubimage(i * 64, j * 68, 64, 68));
			}
			
		}
		System.out.println();

	}
	
	public void rollDieAnimation(int roll){
		int count = 0;

		for(BufferedImage img : dieSet){
			System.out.println(count++);
			Image dimg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			dieRollImage.setIcon(new ImageIcon(dimg));
			dieRollImage.repaint();
		}
		
		//TODO
	}

	public void displayIcon() {

	}

	public void setName(String text) {
		nameLabel.setText(text);
	}

	public void setChar(String text) {
		characterLabel.setText(text);
	}

	public void setIcon(BufferedImage img) {
		Image dimg = img.getScaledInstance(140, 140, Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(dimg);
		imageLabel.setIcon(imageIcon);

	}

	public void setDieIcon(BufferedImage img) {
		Image dimg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(dimg);
		dieRollImage.setIcon(imageIcon);
	}
}
