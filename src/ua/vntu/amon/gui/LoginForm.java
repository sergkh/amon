package ua.vntu.amon.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LoginForm implements ActionListener {
	public LoginForm() {
		JFrame loginFrame = new JFrame("Login Form");
		loginFrame.setLayout(new FlowLayout());
		loginFrame.setSize(250, 250);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton submitButtom = new JButton("Submit");
		JButton resetButtom = new JButton("Reset");
		JLabel userNameLabel = new JLabel("user");
		JLabel passwordLabel = new JLabel("password");
		JTextField userNameTextField = new JTextField("");
		JTextField passwordTextField = new JTextField("");

		loginFrame.add(userNameLabel);
		loginFrame.add(userNameTextField);
		loginFrame.add(passwordLabel);
		loginFrame.add(passwordTextField);
		loginFrame.add(submitButtom);
		loginFrame.add(resetButtom);

		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	}
	
}
