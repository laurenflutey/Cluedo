package view.gui.game.components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.media.sound.ModelAbstractChannelMixer;

import controller.GuiGameController;
import model.Entities;
import model.Room;
import model.Weapon;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class SuggestionDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final Entities entities;
	private final GuiGameController gameController;

	/**
	 * Create the dialog.
	 */
	public SuggestionDialog(Entities entities, String type, GuiGameController gameController) {
		this.entities = entities;
		this.gameController = gameController;

		// init of pane
		setBounds(100, 100, 349, 361);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setAlwaysOnTop(true);

		final JComboBox<Weapon> weapons = new JComboBox<Weapon>();
		for (Weapon weapon : entities.getWeapons()) {
			weapons.addItem(weapon);
		}
		weapons.setBounds(115, 85, 201, 27);
		contentPanel.add(weapons);

		final JComboBox<model.Character> characters = new JComboBox<model.Character>();
		for (model.Character character : entities.getCharacters()) {
			characters.addItem(character);
		}

		characters.setBounds(115, 139, 201, 27);
		contentPanel.add(characters);

		final JComboBox<Room> rooms = new JComboBox<Room>();
		for (Room room : entities.getRooms().values()) {
			rooms.addItem(room);
		}

		rooms.setBounds(115, 199, 201, 27);
		contentPanel.add(rooms);

		JLabel lblPleaseHighlightThe = new JLabel("Please highlight the items you want to " + type);
		lblPleaseHighlightThe.setBounds(21, 37, 309, 16);
		contentPanel.add(lblPleaseHighlightThe);

		JLabel lblWeapon = new JLabel("Weapon");
		lblWeapon.setBounds(21, 89, 61, 16);
		contentPanel.add(lblWeapon);

		JLabel charLabel = new JLabel("Character");
		charLabel.setBounds(21, 143, 61, 16);
		contentPanel.add(charLabel);

		JLabel lblRoom = new JLabel("Room");
		lblRoom.setBounds(21, 203, 61, 16);
		contentPanel.add(lblRoom);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// gameController.createSuggestion()

			}
		});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

	}
}
