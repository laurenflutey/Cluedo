package view.gui;

import controller.GuiGameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

/**
 * Start up window frame that greets the user when they start the game. The frame allows the user to select
 * the number of players in the game and then allows the creation of actual player objects. The player can enter a name
 * for themselves and select a character token.
 *
 * Once the creation process is complete the frame returns to the {@link GuiGameController} a list of players and the
 * actual game begins
 *
 * @author Marcel
 * @author Reuben
 */
public class StartupFrame extends JFrame {

	/**
	 * Randomly generated UID
	 */
	private static final long serialVersionUID = -5354647840440829401L;
	private final int width = 500;
	private final int height = 500;
	private final Dimension gameDimensions = new Dimension(width, height);

	private int players;
	private int count;
	private JPanel panel;
	private JTextField txtName;

	public static void main(String[] args) {
		new StartupFrame();
	}

	/**
	 * Constructor for the startup frame
	 *
	 *
	 */
	public StartupFrame() {

		// Sets the window style to the systems default look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
			e.printStackTrace(); //TODO display something meaningful
			System.out.println("Look and feel failed");
		}

		// Create contentPanel for frame
		panel = new JPanel();
		setContentPane(panel);

		// Setup of frame
		setTitle("Welcome to Cluedo");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Gets the player count from the user
		setupStartupFrame();

		// Absolute layout
		setLayout(null);
		setResizable(false);
		requestFocus();

		// Centred
		setLocationRelativeTo(null);

		// Display the
		setVisible(true);
	}

	/**
	 * First panel displayed to the user which shows the name of the game and asks them for the number of players that
	 * will be playing Cluedo. When they click submit it refreshes the content of the panel and allows them to create
	 * each player for the game.
	 */
	private void setupStartupFrame() {
		// Change size to fit startup window
		setSize(width, 250);

		// Display image on panel
		JLabel imageLabel = new JLabel();
		imageLabel.setIcon(image);
		imageLabel.setBounds(150, 0, 300, 100);
		panel.add(imageLabel);

		// player combo box
		String[] options = { "3", "4", "5", "6" };
		final JComboBox<String> cb = new JComboBox<>(options);
		cb.setBounds(170, 110, 60, 60);
		panel.add(cb);

		// Add action listener so when user selects option, the player count is updated
		cb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				players = cb.getSelectedIndex() + 3;
			}
		});

		// number of players label
		JLabel playersLabel = new JLabel("Number of Players:");
		playersLabel.setBounds(40, 90, 200, 100);
		panel.add(playersLabel);

		// submit button
		JButton submit = new JButton("Submit");
		submit.setBounds(300, 130, 100, 20);
		panel.add(submit);
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getPlayers();
			}
		});
	}

	private void getPlayers() {

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		panel = new JPanel();
		setContentPane(panel);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		panel.setLayout(gbl_contentPane);

		final JLabel lblPlayer = new JLabel("Player: " + (count + 1));
		GridBagConstraints gbc_lblPlayer = new GridBagConstraints();
		gbc_lblPlayer.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlayer.gridx = 0;
		gbc_lblPlayer.gridy = 0;
		panel.add(lblPlayer, gbc_lblPlayer);

		JLabel lblPleaseEnterYour = new JLabel("Please enter your name");
		GridBagConstraints gbc_lblPleaseEnterYour = new GridBagConstraints();
		gbc_lblPleaseEnterYour.insets = new Insets(0, 0, 5, 5);
		gbc_lblPleaseEnterYour.gridx = 0;
		gbc_lblPleaseEnterYour.gridy = 1;
		panel.add(lblPleaseEnterYour, gbc_lblPleaseEnterYour);

		txtName = new JTextField();
		txtName.setText("");
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.insets = new Insets(0, 0, 5, 0);
		gbc_txtName.gridx = 4;
		gbc_txtName.gridy = 1;
		panel.add(txtName, gbc_txtName);
		txtName.setColumns(10);

		JLabel lblSelectYourToken = new JLabel("Select your token");
		GridBagConstraints gbc_lblSelectYourToken = new GridBagConstraints();
		gbc_lblSelectYourToken.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectYourToken.gridx = 0;
		gbc_lblSelectYourToken.gridy = 3;
		panel.add(lblSelectYourToken, gbc_lblSelectYourToken);

		final ButtonGroup bg = new ButtonGroup();

		final JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Miss Scarlett");
		GridBagConstraints gbc_rdbtnNewRadioButton_1 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_1.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnNewRadioButton_1.gridx = 4;
		gbc_rdbtnNewRadioButton_1.gridy = 3;
		panel.add(rdbtnNewRadioButton_1, gbc_rdbtnNewRadioButton_1);

		final JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Mrs Peacock");
		GridBagConstraints gbc_rdbtnNewRadioButton_2 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_2.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_2.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnNewRadioButton_2.gridx = 4;
		gbc_rdbtnNewRadioButton_2.gridy = 4;
		panel.add(rdbtnNewRadioButton_2, gbc_rdbtnNewRadioButton_2);

		final JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("Mrs White");
		GridBagConstraints gbc_rdbtnNewRadioButton_3 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_3.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_3.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnNewRadioButton_3.gridx = 4;
		gbc_rdbtnNewRadioButton_3.gridy = 5;
		panel.add(rdbtnNewRadioButton_3, gbc_rdbtnNewRadioButton_3);

		final JRadioButton rdbtnNewRadioButton_4 = new JRadioButton("Colonel Mustard");
		GridBagConstraints gbc_rdbtnNewRadioButton_4 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_4.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_4.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnNewRadioButton_4.gridx = 4;
		gbc_rdbtnNewRadioButton_4.gridy = 6;
		panel.add(rdbtnNewRadioButton_4, gbc_rdbtnNewRadioButton_4);
		bg.add(rdbtnNewRadioButton_4);

		final JRadioButton rdbtnNewRadioButton = new JRadioButton("Reverend Green");
		GridBagConstraints gbc_rdbtnNewRadioButton = new GridBagConstraints();
		gbc_rdbtnNewRadioButton.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnNewRadioButton.gridx = 4;
		gbc_rdbtnNewRadioButton.gridy = 7;
		panel.add(rdbtnNewRadioButton, gbc_rdbtnNewRadioButton);

		final JRadioButton rdbtnNewRadioButton_5 = new JRadioButton("Professor Plum");
		GridBagConstraints gbc_rdbtnNewRadioButton_5 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_5.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_5.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnNewRadioButton_5.gridx = 4;
		gbc_rdbtnNewRadioButton_5.gridy = 8;
		panel.add(rdbtnNewRadioButton_5, gbc_rdbtnNewRadioButton_5);

		bg.add(rdbtnNewRadioButton);
		bg.add(rdbtnNewRadioButton_1);
		bg.add(rdbtnNewRadioButton_2);
		bg.add(rdbtnNewRadioButton_3);
		bg.add(rdbtnNewRadioButton_5);

		final JButton btnSubmit = new JButton("Submit");
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.insets = new Insets(0, 0, 5, 0);
		gbc_btnSubmit.gridx = 4;
		gbc_btnSubmit.gridy = 10;

		final JButton btnNext = new JButton("Next");
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.insets = new Insets(0, 0, 5, 0);
		gbc_btnNext.gridx = 5;
		gbc_btnNext.gridy = 10;
		btnNext.setEnabled(false);
		btnNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new GuiGameController();
			}
		});

		panel.add(btnNext, gbc_btnNext);

		// create actions for each submit push
		btnSubmit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				for (Enumeration<AbstractButton> buttons = bg.getElements(); buttons.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();

					if (button.isSelected()) {
						// TODO find XY POSITION AND CHAR
						if (!txtName.getText().equals("")) {
							// find the character in the model that represents
							// that player

							//TODO FIX THIS
//							model.Character ch = entities.getCharacter(button.getText());
//							Player p = new Player(txtName.getText(), button.getText(), ch.getCh(), ch.getXPos(),
//									ch.getYPos());
//							p.setCharacter(ch);
							//gamePlayers.add(p);
							// reset the text field and disable the radio button
							// they clicked
							button.setEnabled(false);
							bg.clearSelection();
							lblPlayer.setText("Player: " + (count + 1));
							txtName.setSelectionStart(0);
							System.out.println(txtName.getText());
							txtName.setText("");
							txtName.requestFocus();
							txtName.setSelectionStart(0);
							count++;
						}
					}
					// players have reached the max number specified
					// disable all the radio buttons
					if (count == players) {
						btnSubmit.setEnabled(false);
						txtName.setEnabled(false);
						lblPlayer.setText("Player: " + (count));
						rdbtnNewRadioButton.setEnabled(false);
						rdbtnNewRadioButton_1.setEnabled(false);
						rdbtnNewRadioButton_2.setEnabled(false);
						rdbtnNewRadioButton_3.setEnabled(false);
						rdbtnNewRadioButton_4.setEnabled(false);
						rdbtnNewRadioButton_5.setEnabled(false);
						btnNext.setEnabled(true);
					}
				}

			}
		});

		panel.add(btnSubmit, gbc_btnSubmit);

		txtName.requestFocus();
		txtName.setSelectionStart(0);
		// finalise the display
		setLocationRelativeTo(null);
		setResizable(false);
		requestFocus();
		setVisible(true);

	}

	// Image resource
	private static final ImageIcon image = new ImageIcon("images/Cluedo.png");
}
