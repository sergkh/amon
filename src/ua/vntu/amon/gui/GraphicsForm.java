package ua.vntu.amon.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import ua.vntu.amon.provider.zabbix.ZabbixClient;

public class GraphicsForm implements ActionListener, MenuListener {
	JFrame graphicsFrame;
	JPanel mainPanel, listGraphPanel, workGraphPanel, infoPanel;
	JLabel listGraphLabel, infoLabel, graphLabel, showGraphLabel;
	JMenu fileMenu, helpMenu, getSupportedMenu, logoutMenu, exitMenu;
	JButton showButton;
	JComboBox<String> graphComboBox;

	ArrayList<String> host = new ArrayList<>();
	static ZabbixClient client = new ZabbixClient();

	static String login;
	static String password;

	public GraphicsForm() {
	}

	public GraphicsForm(String name, String password) {
		GraphicsForm.login = name;
		GraphicsForm.password = password;
	}

	public void createGUI() {
		/* Request to Zabbix */
		System.out.println();
		client.register(login, password);
		System.out.println();
		client.host("extend");
		System.out.println();
		//host.add("Zabbix server");
		host.addAll(client.getHostList());
		client.graphsObject(host);

		/* Frame */
		graphicsFrame = new JFrame("Show Graphics");
		graphicsFrame.getContentPane().setLayout(new BorderLayout());
		graphicsFrame.setSize(1000, 500);
		graphicsFrame.setResizable(true);
		graphicsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* Menu */
		Font font = new Font("Verdana", Font.PLAIN, 12);
		JMenuBar menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		helpMenu = new JMenu("Help");
		getSupportedMenu = new JMenu("Get Supported");
		logoutMenu = new JMenu("Logout");
		exitMenu = new JMenu("Exit");

		fileMenu.setFont(font);
		helpMenu.setFont(font);
		getSupportedMenu.setFont(font);
		logoutMenu.setFont(font);
		exitMenu.setFont(font);
		exitMenu.addMenuListener(this);

		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		menuBar.add(getSupportedMenu);
		menuBar.add(logoutMenu);
		menuBar.add(exitMenu);

		/* Panel */
		showButton = new JButton("Show");
		listGraphLabel = new JLabel("Graphics name");
		infoLabel = new JLabel(" @Derman Yaroslav ");
		graphLabel = new JLabel("Show Graphics", 0);
		showGraphLabel = new JLabel("Get Selected");

		System.out.println("From Graphics Form");
		System.out.println(client.getGraphVector());
		Vector<String> v = new Vector<>(client.getGraphVector());
		graphComboBox = new JComboBox<String>(v);
		graphComboBox.setEditable(false);

		graphComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String item = (String) graphComboBox.getSelectedItem();
				showGraphLabel.setText(item);
			}
		});

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		listGraphPanel = new JPanel();
		listGraphPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		listGraphPanel.setLayout(new BoxLayout(listGraphPanel,
				BoxLayout.PAGE_AXIS));
		listGraphPanel.add(listGraphLabel);
		listGraphPanel.add(graphComboBox);
		listGraphPanel.add(showGraphLabel);
		listGraphPanel.add(showButton);

		workGraphPanel = new JPanel();
		workGraphPanel.setLayout(new BorderLayout());
		workGraphPanel.add(graphLabel, BorderLayout.NORTH);

		infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		infoPanel.add(infoLabel, BorderLayout.EAST);

		mainPanel.add(listGraphPanel, BorderLayout.WEST);
		mainPanel.add(workGraphPanel, BorderLayout.CENTER);
		mainPanel.add(infoPanel, BorderLayout.SOUTH);
		graphicsFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		graphicsFrame.setJMenuBar(menuBar);
		graphicsFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void menuSelected(MenuEvent e) {
		if (e.getSource() == exitMenu)
			System.exit(0);
	}

	@Override
	public void menuCanceled(MenuEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void menuDeselected(MenuEvent e) {
		// TODO Auto-generated method stub
	}

	/*
	 * public static void main(String args[]) throws Exception {
	 * client.register(login, password); GraphicsForm graphicForm =new
	 * GraphicsForm(); graphicForm.createGUI();
	 * 
	 * }
	 */

}