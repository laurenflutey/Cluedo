package view.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import model.Board;

public class StartupFrame extends JFrame {

	private int height;
	private int width;
	private int players;
	private Dimension gameDimensions;
	private JPanel panel;

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
		setLocationRelativeTo(null);
		setResizable(false);
		requestFocus();
		setVisible(true);

	}

	private void initPanel() {

		String[] options = { "3", "4", "5", "6" };

		JComboBox cb = new JComboBox(options);
		cb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				players = cb.getSelectedIndex() + 3;
			}

		});

		cb.setBounds(20, 20, 200, 200);
		panel.add(cb);
		
	}

	public static void main(String[] args) {
		new StartupFrame(500, 300);
	}

}
