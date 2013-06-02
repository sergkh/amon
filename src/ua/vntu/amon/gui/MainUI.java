package ua.vntu.amon.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import ua.vntu.amon.json.entity.GraphEntity;
import ua.vntu.amon.json.entity.HostEntity;
import ua.vntu.amon.provider.zabbix.ZabbixClient;

public class MainUI extends JFrame {

	private static final long serialVersionUID = 1L;

	int periodCoefficient = 3600, period = 1, graphid;
	JTextField periodTextField;
	Timer updateImageTimer;
	Vector<String> hostNameVector = new Vector<>();
	ZabbixClient client = new ZabbixClient();
	FilterComboBox graphComboBox;
	String login, password, url;
	ImagePanel imagePanel = new ImagePanel();

	public MainUI(String name, String password, String url) {
		this.login = name;
		this.password = password;
		this.url = url;
	}

	public void createGUI() {

		Font simpleFont = new Font("Verdana", Font.PLAIN, 14);

		updateImageTimer = new Timer(3000, new ActionListener() {
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

		/* Request to Zabbix */
		client.setUrl(url);
		System.out.println();
		client.register(login, password);
		System.out.println();
		client.host("extend");
		System.out.println();
		client.hostGroup("extend", "name");
		System.out.println();

		/* Frame */
		setTitle("Network Monitor");
		getContentPane().setLayout(new BorderLayout());
		setSize(1300, 650);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* Menu */
		JMenuBar menuBar = new JMenuBar();
		createMenu(menuBar, "Help", " Help ... ", simpleFont, null);
		createMenu(menuBar, "Logout", " Logout ... ", simpleFont, null);
		createMenu(menuBar, "Exit", " Exit ... ", simpleFont,
				new MenuListener() {
					@Override
					public void menuSelected(MenuEvent e) {
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
				});
		// ******************************************************************
		ButtonGroup periodGroup = new ButtonGroup();

		periodTextField = new JTextField(10);
		periodTextField.setText("1");

		/* Main Panel */
		JPanel mainPanel = createPanel(getContentPane(), BorderLayout.CENTER,
				1300, 675, new BorderLayout());

		/* Panel with information about author */
		JPanel infoPanel = createPanel(mainPanel, BorderLayout.SOUTH, 1300, 25,
				new BorderLayout());
		infoPanel.add(new JLabel(" Author - DERMAN YAROSLAV "),
				BorderLayout.EAST);

		/* Panel to show graphics */
		JPanel workGraphPanel = createPanel(mainPanel, BorderLayout.CENTER,
				1000, 650, new BorderLayout());
		workGraphPanel.add(imagePanel, BorderLayout.CENTER);

		/* Panel with radio buttons */
		JPanel periodPanel = createPanel(workGraphPanel, BorderLayout.NORTH,
				900, 50, new FlowLayout());

		createLabel(periodPanel, "Enter period", "Period of Graphics",
				simpleFont);
		periodPanel.add(periodTextField);

		createRadioButton(periodPanel, periodGroup, "Hour",
				"Press to show graphics for the last _ hours", simpleFont,
				true, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						periodCoefficient = 3600;
						checkPeriod();
					}
				});

		createRadioButton(periodPanel, periodGroup, "Day",
				"Press to show graphics for the last _ days", simpleFont,
				false, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						periodCoefficient = 86400;
						checkPeriod();
					}
				});

		createRadioButton(periodPanel, periodGroup, "Week",
				"Press to show graphics for the last _ weeks", simpleFont,
				false, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						periodCoefficient = 604800;
						checkPeriod();
					}
				});
		/* Ckeck box */
		final JCheckBox autoUpdateCheckBox = new JCheckBox("Auto Update");
		autoUpdateCheckBox.setSelected(true);
		autoUpdateCheckBox.setFont(simpleFont);
		autoUpdateCheckBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				if (!autoUpdateCheckBox.isSelected()) {
					updateImageTimer.stop();
				} else {
					updateImageTimer.restart();
				}
			}
		});

		/* ComboBox */
		for (HostEntity x : client.getHostEntity()) {
			hostNameVector.add(x.getHost());
		}

		final JComboBox<String> hostComboBox = new JComboBox<String>(
				hostNameVector);
		hostComboBox.setFont(simpleFont);
		hostComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onHostChanged((String) hostComboBox.getSelectedItem());
			}
		});

		graphComboBox = new FilterComboBox(client.getGraphList());
		// graphComboBox = new JComboBox<String>();
		graphComboBox.setEditable(true);
		graphComboBox.setFont(simpleFont);
		graphComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onGraphChanged((String) graphComboBox.getSelectedItem());
				updateImageTimer.restart();
			}
		});

		// autoselect 1-st host
		if (!hostNameVector.isEmpty()) {
			hostComboBox.setSelectedIndex(0);
		}

		/* Panel with graphics list */
		JPanel listGraphPanel = createPanel(mainPanel, BorderLayout.WEST, 250,
				600, new GridLayout(16, 1));
		createLabel(listGraphPanel, "Host :", "Host of you PC", simpleFont);
		listGraphPanel.add(hostComboBox);
		createLabel(listGraphPanel, "Graphics:", "Graphics name", simpleFont);
		listGraphPanel.add(graphComboBox);
		listGraphPanel.add(autoUpdateCheckBox);

		/* Add elements to Frame */
		setJMenuBar(menuBar);
		setVisible(true);
	}

	private void checkPeriod() {
		period = Integer.parseInt(periodTextField.getText());
		period *= periodCoefficient;
		if (period > 63072000) {
			period = 63072000;
			period = period / periodCoefficient;
			String tooltip = "Value must be between 1 and "
					+ Integer.toString(period);
			periodTextField.setText(Integer.toString(period));
			periodTextField.setToolTipText(tooltip);
			updateImageTimer.restart();
		}
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

	private JPanel createPanel(Container c, String layout, int width,
			int height, LayoutManager layoutPanel) {
		JPanel panel = new JPanel();
		c.add(panel, layout);
		panel.setPreferredSize(new Dimension(width, height));
		panel.setLayout(layoutPanel);
		return panel;
	}

	private void createMenu(JMenuBar menuBar, String title, String toolTip,
			Font font, MenuListener listener) {
		JMenu menu = new JMenu(title);
		menuBar.add(menu);
		menu.setFont(font);
		menu.setToolTipText(toolTip);
		menu.addMenuListener(listener);
	}

	private void createRadioButton(Container c, ButtonGroup group,
			String title, String toolTip, Font font, Boolean selected,
			ActionListener listener) {
		JRadioButton button = new JRadioButton(title);
		group.add(button);
		c.add(button);
		button.setToolTipText(toolTip);
		button.setFont(font);
		button.setSelected(selected);
		button.addActionListener(listener);
	}

	protected void createLabel(Container c, String title, String toolTip,
			Font font) {
		JLabel label = new JLabel(title);
		c.add(label);
		label.setToolTipText(toolTip);
		label.setFont(font);
	}
}
