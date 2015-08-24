package view.gui.game.components;

import controller.GuiGameController;
import model.Card;
import model.Player;
import model.Room;
import model.Weapon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * JPanel containing all of the game information. This includes includes the
 * players cards, the dice roll and an image of the players character.
 *
 * @author Marcel
 */
public class InformationPanel extends JPanel {

	private final GuiGameController guiGameController;
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
	private Map<Card, JLabel> jLabelMap;
	private JLabel lblCardsInHand;

	private JLabel dagger;
	private JLabel rope;
	private JLabel leadPipe;
	private JLabel revolver;
	private JLabel spanner;
	private JLabel candlestick;

	private JLabel mrsPeacock;
	private JLabel missScarlett;
	private JLabel mrsWhite;
	private JLabel reverendGreen;
	private JLabel proffessorPlum;
	private JLabel colonelMustard;

	private JLabel library;
	private JLabel billiardRoom;
	private JLabel ballRoom;
	private JLabel study;
	private JLabel kitchen;
	private JLabel conservatory;
	private JLabel diningRoom;
	private JLabel lounge;
	private JLabel hall;

	public InformationPanel(final GuiGameController guiGameController, final JPanel contentPane) {

		initDie();
		this.guiGameController = guiGameController;
		this.parentJPanel = contentPane;
		jLabelMap = new HashMap<>();
		// setBackground(Color.BLUE.darker());
		initCardIcons();
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

		roomNameLabel = new JLabel();
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

		weaponsLabel = new JLabel();
		weaponsLabel.setBounds(23, 346, 400, 16);
		add(weaponsLabel);

		charactersLabel = new JLabel();
		charactersLabel.setBounds(23, 374, 400, 16);
		add(charactersLabel);

		secretLabel = new JLabel();
		secretLabel.setBounds(23, 263, 100, 16);
		add(secretLabel);

		dieRollImage = new JLabel("DieRoll");
		dieRollImage.setBounds(239, 116, 100, 100);
		add(dieRollImage);

		lblCardsInHand = new JLabel("Cards In Hand:");
		lblCardsInHand.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblCardsInHand.setBounds(23, 402, 113, 16);
		add(lblCardsInHand);

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;

		contentPane.add(this, gridBagConstraints);

	}

	private void initCardIcons() {
		initRoomIcons();
		initPlayerIcons();
		initWeaponIcons();
	}

	private void initWeaponIcons() {

		dagger = new JLabel();
		dagger.setIcon(guiGameController.getEntities().getCard("Dagger").getImage());
		jLabelMap.put(guiGameController.getEntities().getCard("Dagger"), dagger);
		dagger.setBounds(23, 410, 64, 64);
		add(dagger);

		revolver = new JLabel();
		revolver.setIcon(guiGameController.getEntities().getCard("Revolver").getImage());
		jLabelMap.put(guiGameController.getEntities().getCard("Revolver"), revolver);
		revolver.setBounds(87, 410, 64, 64);
		add(revolver);

		leadPipe = new JLabel();
		leadPipe.setIcon(guiGameController.getEntities().getCard("Lead Pipe").getImage());
		jLabelMap.put(guiGameController.getEntities().getCard("Lead Pipe"), leadPipe);
		leadPipe.setBounds(151, 410, 64, 64);
		add(leadPipe);

		rope = new JLabel();
		rope.setIcon(guiGameController.getEntities().getCard("Rope").getImage());
		jLabelMap.put(guiGameController.getEntities().getCard("Rope"), rope);
		rope.setBounds(215, 410, 64, 64);
		add(rope);

		spanner = new JLabel();
		spanner.setIcon(guiGameController.getEntities().getCard("Spanner").getImage());
		jLabelMap.put(guiGameController.getEntities().getCard("Spanner"), spanner);
		spanner.setBounds(279, 410, 64, 64);
		add(spanner);

		candlestick = new JLabel();
		candlestick.setIcon(guiGameController.getEntities().getCard("Candlestick").getImage());
		jLabelMap.put(guiGameController.getEntities().getCard("Candlestick"), candlestick);
		candlestick.setBounds(343, 410, 64, 64);
		add(candlestick);

	}

	private void initPlayerIcons() {
		mrsPeacock = new JLabel();
		mrsPeacock.setIcon(guiGameController.getEntities().getCard("Mrs Peacock").getImage());
		jLabelMap.put(guiGameController.getEntities().getCard("Mrs Peacock"), mrsPeacock);
		mrsPeacock.setBounds(23, 454, 64, 64);
		add(mrsPeacock);

		missScarlett = new JLabel();
		missScarlett.setIcon(guiGameController.getEntities().getCard("Miss Scarlett").getImage());
		jLabelMap.put(guiGameController.getEntities().getCard("Miss Scarlett"), missScarlett);
		missScarlett.setBounds(87, 454, 64, 64);
		add(missScarlett);

		mrsWhite = new JLabel();
		mrsWhite.setIcon(guiGameController.getEntities().getCard("Mrs White").getImage());
		jLabelMap.put(guiGameController.getEntities().getCard("Mrs White"), mrsWhite);
		mrsWhite.setBounds(151, 454, 64, 64);
		add(mrsWhite);

		reverendGreen = new JLabel();
		reverendGreen.setIcon(guiGameController.getEntities().getCard("Reverend Green").getImage());
		jLabelMap.put(guiGameController.getEntities().getCard("Reverend Green"), reverendGreen);
		reverendGreen.setBounds(215, 454, 64, 64);
		add(reverendGreen);

		proffessorPlum = new JLabel();
		proffessorPlum.setIcon(guiGameController.getEntities().getCard("Proffessor Plum").getImage());
		jLabelMap.put(guiGameController.getEntities().getCard("Proffessor Plum"), proffessorPlum);
		proffessorPlum.setBounds(279, 454, 64, 64);
		add(proffessorPlum);

		colonelMustard = new JLabel();
		colonelMustard.setIcon(guiGameController.getEntities().getCard("Colonel Mustard").getImage());
		jLabelMap.put(guiGameController.getEntities().getCard("Colonel Mustard"), colonelMustard);
		colonelMustard.setBounds(343, 454, 64, 64);
		add(colonelMustard);

	}

	private void initRoomIcons() {
		library = new JLabel();
		library.setIcon(guiGameController.getEntities().getCard("Library").getImage());
		jLabelMap.put(guiGameController.getEntities().getCard("Library"), library);
		library.setBounds(23, 480, 100, 100);
		add(library);

		billiardRoom = new JLabel();
		billiardRoom.setIcon(guiGameController.getEntities().getCard("Billiard Room").getImage());
		jLabelMap.put(guiGameController.getEntities().getCard("Billiard Room"), billiardRoom);
		billiardRoom.setBounds(133, 480, 170, 100);
		add(billiardRoom);

		ballRoom = new JLabel();
		ballRoom.setIcon(guiGameController.getEntities().getCard("Ball Room").getImage());
		jLabelMap.put(guiGameController.getEntities().getCard("Ball Room"), ballRoom);
		ballRoom.setBounds(313, 480, 120, 100);
		add(ballRoom);

		study = new JLabel();
		study.setIcon(guiGameController.getEntities().getCard("Study").getImage());
		jLabelMap.put(guiGameController.getEntities().getCard("Study"), study);
		study.setBounds(23, 510, 100, 100);
		add(study);

		kitchen = new JLabel();
		kitchen.setIcon(guiGameController.getEntities().getCard("Kitchen").getImage());
		jLabelMap.put(guiGameController.getEntities().getCard("Kitchen"), kitchen);
		kitchen.setBounds(130, 510, 100, 100);
		add(kitchen);

		conservatory = new JLabel();
		conservatory.setIcon(guiGameController.getEntities().getCard("Conservatory").getImage());
		jLabelMap.put(guiGameController.getEntities().getCard("Conservatory"), conservatory);
		conservatory.setBounds(250, 510, 200, 100);
		add(conservatory);

		diningRoom = new JLabel();
		diningRoom.setIcon(guiGameController.getEntities().getCard("Dining Room").getImage());
		jLabelMap.put(guiGameController.getEntities().getCard("Dining Room"), diningRoom);
		diningRoom.setBounds(23, 540, 200, 100);
		add(diningRoom);

		lounge = new JLabel();
		lounge.setIcon(guiGameController.getEntities().getCard("Lounge").getImage());
		jLabelMap.put(guiGameController.getEntities().getCard("Lounge"), lounge);
		lounge.setBounds(170, 540, 100, 100);
		add(lounge);

		hall = new JLabel();
		hall.setIcon(guiGameController.getEntities().getCard("Hall").getImage());
		jLabelMap.put(guiGameController.getEntities().getCard("Hall"), hall);
		hall.setBounds(300, 540, 100, 100);
		add(hall);

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
			dieRollImage.firePropertyChange("String", false, false);
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
		dieRollImage.setIcon(new ImageIcon(dimg));
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

	public void setCardsInHand() {
		for (Card card : guiGameController.getEntities().getAllCards()) {
			if (guiGameController.getCurrentPlayer().containsCardWithName(card.getName())
					|| guiGameController.getCurrentPlayer().getSuggestions().contains(card.getName())) {
				jLabelMap.get(card).setVisible(true);
			} else {
				jLabelMap.get(card).setVisible(false);
			}
		}
	}

}
