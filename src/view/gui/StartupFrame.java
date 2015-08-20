package view.gui;

import controller.GuiGameController;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Start up window frame that greets the user when they start the game. The
 * frame allows the user to select the number of players in the game and then
 * allows the creation of actual player objects. The player can enter a name for
 * themselves and select a character token.
 *
 * Once the creation process is complete the frame returns to the
 * {@link GuiGameController} a list of players and the actual game begins
 *
 * @author Marcel
 * @author Reuben
 */
public class StartupFrame extends JFrame {

	/**
	 * Randomly generated UID
	 */
	private static final long serialVersionUID = -5354647840440829401L;

	private final GuiGameController GUIGAMECONTROLLER;

	// startup frame dimensions
	private final int width = 500;
	private final int height = 500;
	private final Dimension startupFrame = new Dimension(width, height);

	// Image resource
	private static final ImageIcon image = new ImageIcon("images/Cluedo.png");

	private int players = 3;
	private int count;
	private JPanel panel;
	private JTextField nameEntryField;

	private ArrayList<Player> playersList = new ArrayList<>();

	// Swing Components
	private JButton startGameButton;
	private JButton nextPlayerButton;
	private JRadioButton professorPlumButton;
	private JRadioButton reverendGreenButton;
	private JRadioButton colonelMustardButton;
	private JRadioButton mrsWhiteButton;
	private JRadioButton mrsPeacockButton;
	private JRadioButton missScarletButton;
	private JLabel playerLabel;
	private ButtonGroup characterChoiceOptions;
	private JLabel selectionPromptLabel;
	private JLabel nameEntryPromptLabel;
	private JLabel cluedoImage;
	private JButton submitButton;
	private JComboBox<String> playerSelectionBox;

	// Shortcut booleans
	private boolean creatingPlayers = false;
	private boolean firstPane = true;

	/**
	 * Constructor for the startup frame
	 *
	 *
	 */
	public StartupFrame(final GuiGameController GUIGAMECONTROLLER) {

		this.GUIGAMECONTROLLER = GUIGAMECONTROLLER;

		// Sets the window style to the systems default look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException
				| InstantiationException e) {
			e.printStackTrace(); // TODO display something meaningful
			System.out.println("Look and feel failed");
		}

		// Create contentPanel for frame
		panel = new JPanel();
		setContentPane(panel);

		setFocusable(true);

		// Setup of frame
		setTitle("Welcome to Cluedo");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Gets the player count from the user
		setupStartupFrame();

		// Absolute layout
		setLayout(null);
		setResizable(false);

		// Shortcuts for the lazy
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (firstPane) {
					startPlayerCreation();
				} else if (creatingPlayers) {
					performNextPlayerLogic();
				}
			}
		});

		// Centred
		setLocationRelativeTo(null);

		// Display the
		setVisible(true);
	}

	/**
	 * First panel displayed to the user which shows the name of the game and
	 * asks them for the number of players that will be playing Cluedo. When
	 * they click submit it refreshes the content of the panel and allows them
	 * to create each player for the game.
	 */
	private void setupStartupFrame() {
		// Change size to fit startup window
		setSize(width, 250);

		// Display the cluedo image to the startup frame
		cluedoImage = new JLabel();
		cluedoImage.setIcon(image);
		cluedoImage.setBounds(150, 0, 300, 100);
		panel.add(cluedoImage);

		// Number of players selection box
		String[] options = { "3", "4", "5", "6" };
		playerSelectionBox = new JComboBox<>(options);
		playerSelectionBox.setBounds(230, 110, 60, 60);
		panel.add(playerSelectionBox);

		// Add action listener so when user selects option, the player count is
		// updated
		playerSelectionBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				players = playerSelectionBox.getSelectedIndex() + 3;
			}
		});

		// number of players label
		JLabel playersLabel = new JLabel("Number of Players:");
		playersLabel.setBounds(100, 90, 200, 100);
		panel.add(playersLabel);

		// Submit button, which when pressed will take the user to the create
		// players window
		submitButton = new JButton("Submit");
		submitButton.setBounds(350, 180, 100, 20);
		panel.add(submitButton);
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startPlayerCreation();
			}
		});
	}

	/**
	 * Method to delegate to the player creation
	 */
	private void startPlayerCreation() {
		firstPane = false;
		creatingPlayers = true;
		createPlayers();
	}

	/**
	 * Method which handles the creation of a list of players. The user is
	 * presented with the option to enter their name and selection a character.
	 * This is then parsed into a {@link Player} object and added to the
	 * playersList
	 */
	private void createPlayers() {

		// Resize the frame
		setSize(startupFrame);

		// reset the panel
		panel = new JPanel();
		setContentPane(panel);

		// Set to absolute layout again
		panel.setLayout(null);

		// Display cluedo image to the panel
		panel.add(cluedoImage);

		// Display the current player to be made
		playerLabel = new JLabel("Player: " + (count + 1));
		playerLabel.setBounds(40, 100, 200, 50);
		panel.add(playerLabel);

		// The user must enter there name
		nameEntryPromptLabel = new JLabel("Please enter your name:");
		nameEntryPromptLabel.setBounds(40, 140, 150, 50);
		panel.add(nameEntryPromptLabel);

		nameEntryField = new JTextField();
		nameEntryField.setText("");
		nameEntryField.setBounds(190, 150, 200, 35);
		nameEntryField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					performNextPlayerLogic();
				}
			}
		});
		panel.add(nameEntryField);

		// Lets pull focus to the textfield just to be nice
		nameEntryField.requestFocusInWindow();

		selectionPromptLabel = new JLabel("Select your token:");
		selectionPromptLabel.setBounds(40, 200, 300, 35);
		panel.add(selectionPromptLabel);

		// Delegates to method to create player buttons
		initCharacterButtons();

		// Delegates to method to create action buttons
		initActionButtons();

		// finalise the display
		setLocationRelativeTo(null);
		setResizable(false);
	}

	/**
	 * Initialise the next Player and Start Game action buttons
	 */
	private void initActionButtons() {
		nextPlayerButton = new JButton("Next Player");
		nextPlayerButton.setBounds(270, 420, 100, 35);

		// Method to handle the action listener for nextPlayerButton
		intNextPlayerActionHandler();

		panel.add(nextPlayerButton);

		startGameButton = new JButton("Start Game");
		startGameButton.setBounds(380, 420, 100, 35);
		startGameButton.setEnabled(false);
		startGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// get rid of startup frame and begin the real game
				startGame();
			}
		});

		panel.add(startGameButton);
	}

	/**
	 * Method to start the game
	 */
	private void startGame() {
		dispose();
		GUIGAMECONTROLLER.initGame(playersList);
	}

	/**
	 * Creates the action listener for the next button which checks that the
	 * user has selected a valid choice
	 */
	private void intNextPlayerActionHandler() {
		// create actions for each submit push
		nextPlayerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				performNextPlayerLogic();
			}
		});
	}

	/**
	 * Abstracted this method so that it can be called on a keyListener event as well
	 */
	private void performNextPlayerLogic() {
		for (Enumeration<AbstractButton> buttons = characterChoiceOptions.getElements(); buttons
                .hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                if (!nameEntryField.getText().equals("")) {

                    button.setEnabled(false);
                    characterChoiceOptions.clearSelection();
                    count++;
                    playerLabel.setText("Player: " + (count + 1));
                    nameEntryField.setText("");
                    nameEntryField.requestFocusInWindow();
                }
            }
            // players have reached the max number specified
            // disable all the radio buttons
            // and enabled the option to start game
            if (count == players) {
                playerLabel.setText("Player: " + (count));
                nextPlayerButton.setEnabled(false);
                nameEntryField.setEnabled(false);
                reverendGreenButton.setEnabled(false);
                missScarletButton.setEnabled(false);
                mrsPeacockButton.setEnabled(false);
                mrsWhiteButton.setEnabled(false);
                colonelMustardButton.setEnabled(false);
                professorPlumButton.setEnabled(false);
				creatingPlayers = false;
				break;
            }
        }
		for (Enumeration<AbstractButton> buttons = characterChoiceOptions.getElements(); buttons
                .hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isEnabled()) {
                button.setSelected(true);
                break;
            }
        }
	}

	/**
	 * Adds all character options to the button group
	 */
	private void initCharacterButtons() {
		characterChoiceOptions = new ButtonGroup();

		missScarletButton = new JRadioButton("Miss Scarlett");
		missScarletButton.setBounds(180, 200, 300, 35);
		missScarletButton.setSelected(true);
		panel.add(missScarletButton);

		mrsPeacockButton = new JRadioButton("Mrs Peacock");
		mrsPeacockButton.setBounds(180, 235, 300, 35);
		panel.add(mrsPeacockButton);

		mrsWhiteButton = new JRadioButton("Mrs White");
		mrsWhiteButton.setBounds(180, 270, 300, 35);
		panel.add(mrsWhiteButton);

		colonelMustardButton = new JRadioButton("Colonel Mustard");
		colonelMustardButton.setBounds(180, 305, 300, 35);
		panel.add(colonelMustardButton);

		reverendGreenButton = new JRadioButton("Reverend Green");
		reverendGreenButton.setBounds(180, 340, 300, 35);
		panel.add(reverendGreenButton);

		professorPlumButton = new JRadioButton("Professor Plum");
		professorPlumButton.setBounds(180, 375, 300, 35);
		panel.add(professorPlumButton);

		characterChoiceOptions.add(reverendGreenButton);
		characterChoiceOptions.add(missScarletButton);
		characterChoiceOptions.add(mrsPeacockButton);
		characterChoiceOptions.add(mrsWhiteButton);
		characterChoiceOptions.add(colonelMustardButton);
		characterChoiceOptions.add(professorPlumButton);
	}

	/**
	 * Getter
	 *
	 * @return returns the playerList parsed by the startup frame
	 */
	public ArrayList<Player> getPlayersList() {
		return playersList;
	}
}
