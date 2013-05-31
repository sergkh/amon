package ua.vntu.amon.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import ua.vntu.amon.json.converting.GraphEntity;
import ua.vntu.amon.json.converting.HostEntity;
import ua.vntu.amon.provider.zabbix.ZabbixClient;

public class GraphicsFrame implements ActionListener, MenuListener {
	int periodCoefficient = 3600, period = 3600, graphid;

	JPanel mainPanel, listGraphPanel, workGraphPanel, infoPanel;
	JLabel listGraphLabel, showGraphLabel, hostLabel, periodLabel;
	JLabel graphicShowLabel, imageLabel;
	JTextField periodTextField;
	JRadioButton hourRadioButton, dayRadioButton, weekRadioButton;
	JMenu fileMenu, helpMenu, getSupportedMenu, logoutMenu, exitMenu;
	JButton showButton;
	JComboBox<String> graphComboBox, hostComboBox;

	Timer updateImageTimer;

	Vector<String> hostNameVector = new Vector<>();

	ZabbixClient client = new ZabbixClient();

	String login;
	String password;

	ImagePanel imagePanel = new ImagePanel();

	public GraphicsFrame(String name, String password) {
		this.login = name;
		this.password = password;
		
	}

	public void createGUI() {
		updateImageTimer = new Timer(6000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() { // TODO Auto-generated method
						onGraphChanged((String) graphComboBox.getSelectedItem());						
					}
				});
			}
		});
		updateImageTimer.setInitialDelay(0);
		updateImageTimer.start();
		
		imagePanel.refresh();
		/* Request to Zabbix */
		System.out.println();
		client.register(login, password);
		System.out.println();
		client.host("extend");
		System.out.println();
		client.hostGroup("extend", "name");
		System.out.println();

		/* Frame */
		JFrame graphicsFrame = new JFrame("Show Graphics");
		graphicsFrame.getContentPane().setLayout(new BorderLayout());
		graphicsFrame.setSize(1200, 650);
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

		/* Other Elements */

		/* Buttons */
		// showButton = new JButton("Show");
		hourRadioButton = new JRadioButton("Hour");
		hourRadioButton.setSelected(true);
		dayRadioButton = new JRadioButton("Day");
		weekRadioButton = new JRadioButton("Week");

		ButtonGroup periodGroup = new ButtonGroup();
		periodGroup.add(hourRadioButton);
		periodGroup.add(dayRadioButton);
		periodGroup.add(weekRadioButton);

		/* Labels */
		listGraphLabel = new JLabel("Graphics name");

		showGraphLabel = new JLabel("Grapid Name");
		periodLabel = new JLabel("Enter period");
		hostLabel = new JLabel("Host : ");

		/* Text Field */
		periodTextField = new JTextField(10);
		periodTextField.setText("1");
		
		/* ComboBox */
		for (HostEntity x : client.getHostEntity()) {
			hostNameVector.add(x.getHost());
		}

		hostComboBox = new JComboBox<String>(hostNameVector);
		hostComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onHostChanged((String) hostComboBox.getSelectedItem());
			}
		});

		graphComboBox = new JComboBox<String>();
		graphComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onGraphChanged((String) graphComboBox.getSelectedItem());
			}
		});

		// autoselect 1-st host
		if (!hostNameVector.isEmpty()) {
			hostComboBox.setSelectedIndex(0);
		}

		/* ActionListener */
		hourRadioButton.addActionListener(this);
		dayRadioButton.addActionListener(this);
		weekRadioButton.addActionListener(this);

		/* PANELS */
		/* Main PAnel */
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		/* Panel with graphics list */
		listGraphPanel = new JPanel();
		listGraphPanel.setPreferredSize(new Dimension(250, 600));
		listGraphPanel.setOpaque(true);
		//listGraphPanel.
		//listGraphPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		listGraphPanel.setLayout(new GridLayout(16, 1));
		listGraphPanel.add(hostLabel);
		listGraphPanel.add(hostComboBox);
		listGraphPanel.add(showGraphLabel);
		listGraphPanel.add(graphComboBox);
		// listGraphPanel.add(showButton);

		/* Panel to show graphics */
		JPanel periodPanel = new JPanel();
		periodPanel.setLayout(new FlowLayout());
		periodPanel.add(periodLabel);
		periodPanel.add(periodTextField);
		periodPanel.add(hourRadioButton);
		periodPanel.add(dayRadioButton);
		periodPanel.add(weekRadioButton);
		
		workGraphPanel = new JPanel();
		workGraphPanel.setLayout(new BorderLayout());
		workGraphPanel.add(periodPanel, BorderLayout.NORTH);
		workGraphPanel.add(imagePanel, BorderLayout.CENTER);

		/* Panel with information about author */
		infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		infoPanel.add(new JLabel(" @Derman Yaroslav "), BorderLayout.EAST);

		/* Add elements to Main PAnel */
		mainPanel.add(listGraphPanel, BorderLayout.WEST);
		mainPanel.add(workGraphPanel, BorderLayout.CENTER);
		mainPanel.add(infoPanel, BorderLayout.SOUTH);

		/* Add elements to Frame */
		graphicsFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		graphicsFrame.setJMenuBar(menuBar);
		graphicsFrame.setVisible(true);
		
	}

	private void onHostChanged(String host) {
		client.graphsObject(Arrays.asList(host));

		Vector<String> graphs = client.getGraphVector();
		graphComboBox.setModel(new DefaultComboBoxModel<>(graphs));

		// autoselect first item
		if (graphs != null && !graphs.isEmpty()) {
			graphComboBox.setSelectedIndex(0);
		}
	}

	private void onGraphChanged(String graphName) {
		GraphEntity graphObj = null;

		for (GraphEntity ge : client.getGraphEntity()) {
			if (graphName.equals(ge.getName())) {
				graphObj = ge;
				break;
			}
		}

		if (graphObj == null) {
			throw new IllegalStateException(
					"Graph object is not found for name: " + graphName);
		}

		try {
			String imageurl = client
					.makeImageUrl(graphObj.getGraphid(), period);
			imagePanel.setImage(client.getGraphImage(imageurl));

			// Dimension size = new Dimension();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == hourRadioButton) {
			periodCoefficient = 3600;
			period = Integer.parseInt(periodTextField.getText());
			period = period * periodCoefficient;
		}

		if (e.getSource() == dayRadioButton) {
			periodCoefficient = 86400;
			period = Integer.parseInt(periodTextField.getText());
			period = period * periodCoefficient;
		}
		if (e.getSource() == weekRadioButton) {
			periodCoefficient = 604800;
			period = Integer.parseInt(periodTextField.getText());
			period = period * periodCoefficient;
		}			
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
}

class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Image img;

	public ImagePanel() {
			}

	void refresh() {
		repaint();
	}

	void setImage(Image img) {
		this.img = img;
		repaint();
	}

	public void paintComponent(Graphics g) {
		if (img != null) {
			g.drawImage(img, 0, 0, this);
		}
	}
}
