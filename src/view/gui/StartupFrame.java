package view.gui;

import controller.GuiGameController;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
	private final Dimension startupFrame = new Dimension(width, height);

	private int players = 3;
	private int count;
	private JPanel panel;
	private JTextField txtName;

	private ArrayList<Player> playersList = new ArrayList<>();

	//TODO remove me
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
		cb.setBounds(230, 110, 60, 60);
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
		playersLabel.setBounds(100, 90, 200, 100);
		panel.add(playersLabel);

		// submit button
		JButton submit = new JButton("Submit");
		submit.setBounds(350, 180, 100, 20);
		panel.add(submit);
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getPlayers();
			}
		});
	}

	/**
	 *
	 */
	private void getPlayers() {

		// Resize the frame
		setSize(startupFrame);

		// reset the panel
		panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(null);

		// Display image on panel
		JLabel imageLabel = new JLabel();
		imageLabel.setIcon(image);
		imageLabel.setBounds(150, 0, 300, 100);
		panel.add(imageLabel);

		final JLabel lblPlayer = new JLabel("Player: " + (count + 1));
		lblPlayer.setBounds(40, 100, 200, 50);
		panel.add(lblPlayer);

		JLabel lblPleaseEnterYour = new JLabel("Please enter your name:");
		lblPleaseEnterYour.setBounds(40, 140, 150, 50);
		panel.add(lblPleaseEnterYour);



		txtName = new JTextField();
		txtName.setText("");
		txtName.setBounds(190, 150, 200, 35);
		panel.add(txtName);
		txtName.setColumns(10);

		txtName.requestFocusInWindow();

		JLabel lblSelectYourToken = new JLabel("Select your token:");
		lblSelectYourToken.setBounds(40, 200, 300, 35);
		panel.add(lblSelectYourToken);

		final ButtonGroup bg = new ButtonGroup();

		final JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Miss Scarlett");
		rdbtnNewRadioButton_1.setBounds(180, 200, 300, 35);
		panel.add(rdbtnNewRadioButton_1);

		final JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Mrs Peacock");
		rdbtnNewRadioButton_2.setBounds(180, 235, 300, 35);
		panel.add(rdbtnNewRadioButton_2);

		final JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("Mrs White");
		rdbtnNewRadioButton_3.setBounds(180, 270, 300, 35);
		panel.add(rdbtnNewRadioButton_3);

		final JRadioButton rdbtnNewRadioButton_4 = new JRadioButton("Colonel Mustard");
		rdbtnNewRadioButton_4.setBounds(180, 305, 300, 35);
		panel.add(rdbtnNewRadioButton_4);


		final JRadioButton rdbtnNewRadioButton = new JRadioButton("Reverend Green");
		rdbtnNewRadioButton.setBounds(180, 340, 300, 35);
		panel.add(rdbtnNewRadioButton);

		final JRadioButton rdbtnNewRadioButton_5 = new JRadioButton("Professor Plum");
		rdbtnNewRadioButton_5.setBounds(180, 375, 300, 35);
		panel.add(rdbtnNewRadioButton_5);

		bg.add(rdbtnNewRadioButton);
		bg.add(rdbtnNewRadioButton_1);
		bg.add(rdbtnNewRadioButton_2);
		bg.add(rdbtnNewRadioButton_3);
		bg.add(rdbtnNewRadioButton_4);
		bg.add(rdbtnNewRadioButton_5);

		final JButton btnSubmit = new JButton("Next Player");
		btnSubmit.setBounds(270, 420, 100, 35);

		final JButton btnNext = new JButton("Start Game");
		btnNext.setBounds(380, 420, 100, 35);
		btnNext.setEnabled(false);
		btnNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new GuiGameController();
			}
		});

		panel.add(btnNext);

		// create actions for each submit push
		btnSubmit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				for (Enumeration<AbstractButton> buttons = bg.getElements(); buttons.hasMoreElements(); ) {
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
							count++;
							lblPlayer.setText("Player: " + (count + 1));
							txtName.setSelectionStart(0);
							System.out.println(txtName.getText());
							txtName.setText("");
							txtName.requestFocus();
							txtName.setSelectionStart(0);
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

		panel.add(btnSubmit);

		// finalise the display
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

	}

	public ArrayList<Player> getPlayersList() {
		return playersList;
	}

	// Image resource
	private static final ImageIcon image = new ImageIcon("images/Cluedo.png");
}
