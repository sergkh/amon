package ua.vntu.amon.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginForm implements ActionListener {
	JFrame loginFrame;
	JButton submitButton, resetButton;
	JLabel userNameLabel, passwordLabel;
	JTextField userNameTextField;
	JPasswordField passwordField;
	JPanel informationPanel, buttonPanel;
	JLabel infoLabel;

	public LoginForm() {
		loginFrame = new JFrame("Login Form");
		loginFrame.getContentPane().setLayout(new FlowLayout());
		loginFrame.setSize(400, 250);
		loginFrame.setResizable(false);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		informationPanel = new JPanel(new GridLayout(2, 2));
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		resetButton = new JButton("Reset");
		resetButton.addActionListener(this);

		userNameLabel = new JLabel("user");
		passwordLabel = new JLabel("password");
		userNameTextField = new JTextField(15);
		passwordField = new JPasswordField(15);
		infoLabel = new JLabel("blabla");

		informationPanel.add(userNameLabel);
		informationPanel.add(userNameTextField);
		informationPanel.add(passwordLabel);
		informationPanel.add(passwordField);
		buttonPanel.add(submitButton);
		buttonPanel.add(resetButton);

		loginFrame.getContentPane().add(informationPanel);
		loginFrame.getContentPane().add(buttonPanel);
		loginFrame.getContentPane().add(infoLabel);

		loginFrame.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == resetButton) {
			userNameTextField.setText("");
			passwordField.setText("");
		}
		if (ae.getSource() == submitButton) {
			infoLabel.setText("name " + userNameTextField.getText());
		}
	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new LoginForm();
			}
		});
	}
}
