package view.gui.game.components;

import controller.GuiGameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Dialog box to confirm actions with the user
 */
public class ConfirmationDialog extends JDialog{

    private final JPanel contentPanel = new JPanel();
    private JLabel confirmationMessage;
    private JPanel buttonPane;
    private JButton yesButton;
    private JButton noButton;
    private KeyboardFocusManager shortcutManager;

    /**
     * Constructor
     *
     * Creates a new dialog box to confirm an action with the user
     */
    public ConfirmationDialog() {
        initDialog(null);
    }

    /**
     * Creates the dialog box and delegates to add buttons and action listeners
     *
     * @param text Optional text to set in dialog box, otherwise it will default
     */
    private void initDialog(String text) {
        // Absolute layout, size and position
        setSize(300, 150);
        setLocationRelativeTo(null);
        setResizable(false);
        contentPanel.setLayout(null);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // Add to the dialog
        getContentPane().add(contentPanel);

        if (text == null){
            confirmationMessage = new JLabel("Are you sure you want to exit?");
        } else {
            confirmationMessage = new JLabel(text);
        }

        // Centre the text
        confirmationMessage.setHorizontalAlignment(SwingConstants.CENTER);
        // This will break if text is long
        confirmationMessage.setBounds(6, 6, 288, 77);
        contentPanel.add(confirmationMessage);

        initButtons();

        // Initialise the Key event dispatch manager for shortcuts
        shortcutManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        shortcutManager.addKeyEventDispatcher(new ShortcutDispatcher());

        setVisible(true);
        requestFocus();
    }

    /**
     * Creates the buttons and adds their action listeners
     */
    private void initButtons() {
        // Create a button panel for the buttons sit
        buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Float to bottom
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        yesButton = new JButton("YES");
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doClose();
            }
        });
        buttonPane.add(yesButton);

        noButton = new JButton("NO");
        noButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPane.add(noButton);
    }

    /**
     * KeyEventDispatcher class to handle the executions of shortcuts in the dialog
     */
    private class ShortcutDispatcher implements KeyEventDispatcher {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (e.getKeyCode() == KeyEvent.VK_Y) {
                    doClose();
                } else if (e.getKeyCode() == KeyEvent.VK_N) {
                    dispose();
                }
            }
            return false;
        }
    }

    /**
     * Delegates to the {@link GuiGameController} to close the game and then disposes itself
     */
    private void doClose() {
        GuiGameController.closeGame();
        dispose();
    }
}
