package ua.vntu.amon.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.*;
import ua.vntu.amon.provider.zabbix.ZabbixClient;

public class LoginForm implements ActionListener {
	static ZabbixClient client = new ZabbixClient();
	JFrame loginFrame;
	JButton submitButton, resetButton;
	JLabel userNameLabel, passwordLabel;
	JTextField userNameTextField;
	JPasswordField passwordField;
	JPanel informationPanel, buttonPanel, errorPanel;
	JLabel errorLabel;

	public JFrame getLoginFrame() {
		return loginFrame;
	}

	public void setLoginFrame(JFrame loginFrame) {
		this.loginFrame = loginFrame;
	}

	public JLabel getErrorLabel() {
		return errorLabel;
	}

	public void setErrorLabel(JLabel errorLabel) {
		this.errorLabel = errorLabel;
	}

	String name, password;
	int value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoginForm() {
		loginFrame = new JFrame("Login Form");
		loginFrame.getContentPane().setLayout(new FlowLayout());
		loginFrame.setSize(400, 250);
		loginFrame.setResizable(false);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		informationPanel = new JPanel(new GridLayout(2, 2));
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		errorPanel = new JPanel(new GridLayout(1, 1));

		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		submitButton
				.setToolTipText("Press to login in network monitoring program");
		resetButton = new JButton("Reset");
		resetButton.addActionListener(this);
		resetButton.setToolTipText("Press to clean all field");

		Font errorFont = new Font("Verdana", Font.BOLD, 14);
		Font simpleFont = new Font("Verdana", Font.PLAIN, 14);
		userNameLabel = new JLabel("User");
		userNameLabel.setFont(simpleFont);
		passwordLabel = new JLabel("Password");
		passwordLabel.setFont(simpleFont);

		userNameTextField = new JTextField(15);
		userNameTextField.setToolTipText("Enter your register user name");
		passwordField = new JPasswordField(15);
		passwordField.setToolTipText("Enter your password ");

		errorLabel = new JLabel("");
		errorLabel.setFont(errorFont);
		errorLabel.setVisible(false);
		errorLabel.setForeground(Color.RED);

		informationPanel.add(userNameLabel);
		informationPanel.add(userNameTextField);
		informationPanel.add(passwordLabel);
		informationPanel.add(passwordField);
		buttonPanel.add(submitButton);
		buttonPanel.add(resetButton);
		errorPanel.add(errorLabel);

		loginFrame.getContentPane().add(errorPanel);
		loginFrame.getContentPane().add(informationPanel);
		loginFrame.getContentPane().add(buttonPanel);

		loginFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				value = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to close login form?", "Close",
						JOptionPane.YES_NO_OPTION);
				if (value == 0) {
					System.exit(0);
				}
			}
		});

		loginFrame.setVisible(true);
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == resetButton) {
			userNameTextField.setText("");
			passwordField.setText("");
			errorLabel.setVisible(false);
		}
		if (ae.getSource() == submitButton) {
			name = userNameTextField.getText();
			password = passwordField.getText();
			if ((name.equals("")) && password.equals("")) {
				errorLabel.setText("Username and Password are required !!");
				errorLabel.setVisible(true);
			} else if ((name.equals(""))) {
				errorLabel.setText("Username is required !!");
				errorLabel.setVisible(true);
			} else if (password.equals("")) {
				errorLabel.setText("Password is required !!");
				errorLabel.setVisible(true);
			} else {
				try {
					client.register(name, password);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				errorLabel.setVisible(false);
			}
		}
	}

	public static void main(String args[]) throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new LoginForm();
			}
		});
	}
}
