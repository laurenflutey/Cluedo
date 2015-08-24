package view.gui.game.components;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.Timer;

import controller.GuiGameController;
import model.Player;
import model.Room;
import model.Weapon;

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
	private JLabel secretNameLabel;
	private JLabel secretLabel;
	private JLabel dieRollImage;

	private Set<BufferedImage> dieSet;
	private JLabel lblCardsInHand;
	private JLabel currentWeapons;
	private JLabel currentCharacters;
	private JLabel currentRooms;
	private JList<Player> list;

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

		roomInfoLabel = new JLabel("Room Information:");
		roomInfoLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		roomInfoLabel.setBounds(23, 179, 156, 16);
		add(roomInfoLabel);

		roomNameLabel = new JLabel("");
		roomNameLabel.setBounds(23, 207, 100, 16);
		add(roomNameLabel);

		secretNameLabel = new JLabel("Secret Room:");
		secretNameLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		secretNameLabel.setBounds(23, 235, 100, 16);
		add(secretNameLabel);

		contentsLabel = new JLabel("Contents:");
		contentsLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		contentsLabel.setBounds(23, 318, 100, 16);
		add(contentsLabel);

		weaponsLabel = new JLabel("");
		weaponsLabel.setBounds(23, 346, 400, 16);
		add(weaponsLabel);

		charactersLabel = new JLabel("");
		charactersLabel.setBounds(23, 374, 400, 16);
		add(charactersLabel);
		
		secretLabel = new JLabel("");
		secretLabel.setBounds(23, 263, 100, 16);
		add(secretLabel);
		
		dieRollImage = new JLabel("DieRoll");
		dieRollImage.setBounds(23, 546, 100, 100);
		add(dieRollImage);
		
		lblCardsInHand = new JLabel("Cards In Hand:");
		lblCardsInHand.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblCardsInHand.setBounds(23, 402, 113, 16);
		add(lblCardsInHand);
		
		list = new JList<Player>();
		list.setBounds(265, 458, 120, 31);
		list.setCellRenderer(new ListCellRenderer<Player>() {

			@Override
			public Component getListCellRendererComponent(JList<? extends Player> list, Player value, int index,
					boolean isSelected, boolean cellHasFocus) {
				//super()
				return null;
			}
		});
		add(list);
		
		currentWeapons = new JLabel("weapons");
		currentWeapons.setBounds(23, 430, 61, 16);
		add(currentWeapons);
		
		currentCharacters = new JLabel("characters");
		currentCharacters.setBounds(23, 458, 61, 16);
		add(currentCharacters);
		
		currentRooms = new JLabel("rooms");
		currentRooms.setBounds(23, 486, 61, 16);
		add(currentRooms);

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

	public void rollDieAnimation(int roll) {
		int count = 0;
		// TODO
		for (BufferedImage img : dieSet) {
			System.out.println(count++);
			Image dimg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			dieRollImage.setIcon(new ImageIcon(dimg));
			dieRollImage.repaint();
		}
		rollLabel.setText("Roll: " + roll);

		// TODO
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

	public void setRoomInfo(Room room) {
		if (room != null) {
			roomNameLabel.setText(room.getName());
			String weaponString = "";
			for (Weapon weapon : room.getWeapons()) {
				weaponString += (weapon.getName() + " ");
			}
			weaponsLabel.setText(weaponString);
			String playerString = "";
			for (Player player : room.getPlayers()) {
				if (!player.equals(guiGameController.getCurrentPlayer())) {
					playerString += (player.getName() + " ");
				}
			}
			charactersLabel.setText(playerString);
			if (room.getConnectingRoom() != null) {
				secretLabel.setText(room.getConnectingRoom().getName());
			}
		} else {
			roomNameLabel.setText("");
			weaponsLabel.setText("");
			charactersLabel.setText("");
			secretLabel.setText("");
		}
	}

}
