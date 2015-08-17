package view.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import model.Board;

public class StartupFrame extends JFrame {

	private int height;
	private int width;
	private int players;
	private Dimension gameDimensions;
	private JPanel panel;
	private JTextField txtName;

	public StartupFrame(int width, int height) {
		this.width = width;
		this.height = height;
		this.panel = new JPanel();
		setContentPane(panel);

		gameDimensions = new Dimension(width, height);
		setSize(gameDimensions);
		setMinimumSize(gameDimensions);

		initPanel();

		setTitle("Cluedo");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		setLayout(null);
		// setLocationRelativeTo(null);
		setResizable(false);
		requestFocus();
		setVisible(true);

	}

	private void initPanel() {
		// image
		ImageIcon image = new ImageIcon("images/Cluedo.png");
		JLabel imageLabel = new JLabel();
		imageLabel.setIcon(image);
		imageLabel.setBounds(200, 0, 300, 100);

		// player combo box
		String[] options = { "3", "4", "5", "6" };
		JComboBox cb = new JComboBox(options);
		cb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				players = cb.getSelectedIndex() + 3;
			}
		});
		cb.setBounds(200, 110, 60, 60);

		// label
		JLabel label = new JLabel("Welcome to Cluedo");
		label.setBounds(20, 20, 200, 100);

		// number of players label
		JLabel playersLabel = new JLabel("Number of Players:");
		playersLabel.setBounds(20, 90, 200, 100);

		// submit button
		JButton submit = new JButton("Submit");
		submit.setBounds(250, 200, 100, 20);
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(players);
				askPlayers();
			}
		});

		// adding to frame
		panel.add(imageLabel);
		panel.add(playersLabel);
		panel.add(submit);
		panel.add(cb);
		panel.add(label);

	}

	private void askPlayers() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 500, 500);
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		panel.setLayout(gbl_contentPane);

		JLabel lblPleaseEnterYour = new JLabel("Please enter your name");
		GridBagConstraints gbc_lblPleaseEnterYour = new GridBagConstraints();
		gbc_lblPleaseEnterYour.insets = new Insets(0, 0, 5, 5);
		gbc_lblPleaseEnterYour.gridx = 0;
		gbc_lblPleaseEnterYour.gridy = 1;
		panel.add(lblPleaseEnterYour, gbc_lblPleaseEnterYour);

		txtName = new JTextField();
		txtName.setText("Name");
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

		ButtonGroup bg = new ButtonGroup();

		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Miss Scarlett");
		GridBagConstraints gbc_rdbtnNewRadioButton_1 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_1.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnNewRadioButton_1.gridx = 4;
		gbc_rdbtnNewRadioButton_1.gridy = 3;
		panel.add(rdbtnNewRadioButton_1, gbc_rdbtnNewRadioButton_1);

		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Mrs Peacock");
		GridBagConstraints gbc_rdbtnNewRadioButton_2 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_2.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_2.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnNewRadioButton_2.gridx = 4;
		gbc_rdbtnNewRadioButton_2.gridy = 4;
		panel.add(rdbtnNewRadioButton_2, gbc_rdbtnNewRadioButton_2);

		JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("Mrs White");
		GridBagConstraints gbc_rdbtnNewRadioButton_3 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_3.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_3.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnNewRadioButton_3.gridx = 4;
		gbc_rdbtnNewRadioButton_3.gridy = 5;
		panel.add(rdbtnNewRadioButton_3, gbc_rdbtnNewRadioButton_3);

		JRadioButton rdbtnNewRadioButton_4 = new JRadioButton("Colonel Mustard");
		GridBagConstraints gbc_rdbtnNewRadioButton_4 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_4.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_4.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnNewRadioButton_4.gridx = 4;
		gbc_rdbtnNewRadioButton_4.gridy = 6;
		panel.add(rdbtnNewRadioButton_4, gbc_rdbtnNewRadioButton_4);
		bg.add(rdbtnNewRadioButton_4);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("Reverend Green");
		GridBagConstraints gbc_rdbtnNewRadioButton = new GridBagConstraints();
		gbc_rdbtnNewRadioButton.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnNewRadioButton.gridx = 4;
		gbc_rdbtnNewRadioButton.gridy = 7;
		panel.add(rdbtnNewRadioButton, gbc_rdbtnNewRadioButton);

		JRadioButton rdbtnNewRadioButton_5 = new JRadioButton("Professor Plum");
		GridBagConstraints gbc_rdbtnNewRadioButton_5 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_5.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_5.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnNewRadioButton_5.gridx = 4;
		gbc_rdbtnNewRadioButton_5.gridy = 8;
		panel.add(rdbtnNewRadioButton_5, gbc_rdbtnNewRadioButton_5);
		rdbtnNewRadioButton_5.setSelected(true);

		bg.add(rdbtnNewRadioButton);
		bg.add(rdbtnNewRadioButton_1);
		bg.add(rdbtnNewRadioButton_2);
		bg.add(rdbtnNewRadioButton_3);
		bg.add(rdbtnNewRadioButton_5);

		JButton btnSubmit = new JButton("Submit");
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.gridx = 4;
		gbc_btnSubmit.gridy = 10;
		btnSubmit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				for (Enumeration<AbstractButton> buttons = bg.getElements(); buttons.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();

					if (button.isSelected()) {
						System.out.println(button.getText());
					}
				}
				System.out.println(txtName.getText());

			}
		});
		panel.add(btnSubmit, gbc_btnSubmit);

		// setLocationRelativeTo(null);
		setResizable(false);
		requestFocus();
		setVisible(true);

	}

	private void askPlayer() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		new StartupFrame(500, 500);
	}

}
