package ua.vntu.amon.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import ua.vntu.amon.json.entity.GraphEntity;
import ua.vntu.amon.json.entity.HostEntity;
import ua.vntu.amon.provider.zabbix.ZabbixClient;

public class MainUI extends JFrame {

	private static final long serialVersionUID = 1L;

    final static String SINGLE = "Single";
    final static String DASHBOARD = "Dashboard";

	int periodCoefficient = 3600, period = 1, timerDelay = 3000;
    private JTextField periodTextField;
    private JPanel workGraphPanel, dashBoardPanel, graphPanel;

    private CardLayout graphsLayout = new CardLayout();

    private JCheckBox dashboardCheckBox;
    private Timer updateImageTimer;
    private Vector<String> hostNameVector = new Vector<>();
    private List<GraphEntity> dashboardList = new ArrayList<GraphEntity>();
    private ZabbixClient client = new ZabbixClient();
    private FilterComboBox graphComboBox;
    private String login, password, url;

    private ImagePanel imagePanel = new ImagePanel();

	public MainUI(String name, String password, String url) {
		this.login = name;
		this.password = password;
		this.url = url;
        createGUI();
	}

	private void createGUI() {

		Font simpleFont = new Font("Verdana", Font.PLAIN, 14);

		updateImageTimer = new Timer(timerDelay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {

                    if(dashboardCheckBox.isSelected()) {
                        updateDashboard(dashboardList, dashBoardPanel);
                    } else {
                        onGraphChanged((String) graphComboBox.getSelectedItem(), imagePanel);
                    }
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
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// ******************************************************************
		ButtonGroup periodGroup = new ButtonGroup();

		periodTextField = new JTextField(10);
		periodTextField.setText("1");

		/* Main Panel */
		JPanel mainPanel = createPanel(getContentPane(), BorderLayout.CENTER, 1300, 675, new BorderLayout());

		/* Panel with information about author */
		JPanel infoPanel = createPanel(mainPanel, BorderLayout.SOUTH, 1300, 25, new BorderLayout());
		infoPanel.add(new JLabel(" Author - DERMAN YAROSLAV "), BorderLayout.EAST);

		/* Panel to show graphics */
		workGraphPanel = createPanel(mainPanel, BorderLayout.CENTER, 1000, 650, new BorderLayout());

        graphPanel = new JPanel(graphsLayout);
        workGraphPanel.add(graphPanel, BorderLayout.CENTER);

        graphPanel.add(imagePanel, SINGLE);

        dashBoardPanel = new JPanel();
        graphPanel.add(dashBoardPanel, DASHBOARD);

		/* Panel with radio buttons */
		JPanel periodPanel = createPanel(workGraphPanel, BorderLayout.NORTH,
				900, 50, new FlowLayout());

		createLabel(periodPanel, "Enter period", "Period of Graphics",
				simpleFont);

		JRadioButton hourRadioButton = createRadioButton(periodGroup, "Hour",
				"Press to show graphics for the last _ hours", "hour.png",
				simpleFont, true, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						periodCoefficient = 3600;
						checkPeriod();
					}
				});

		JRadioButton dayRadioButton = createRadioButton(periodGroup, "Day",
				"Press to show graphics for the last _ days", "day.png",
				simpleFont, false, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						periodCoefficient = 86400;
						checkPeriod();
					}
				});

		JRadioButton weekRadioButton = createRadioButton(periodGroup, "Week",
				"Press to show graphics for the last _ weeks", "week.png",
				simpleFont, false, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						periodCoefficient = 604800;
						checkPeriod();
					}
				});
		periodPanel.add(periodTextField);
		periodPanel.add(hourRadioButton);
		periodPanel.add(dayRadioButton);
		periodPanel.add(weekRadioButton);

		final JButton addDashButton = new JButton("Add to Dashboard");
		final JButton removeDashButton = new JButton("Remove from Dashboard");

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
		graphComboBox.setEditable(true);
		graphComboBox.setFont(simpleFont);
		graphComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onGraphChanged((String) graphComboBox.getSelectedItem(), imagePanel);
				updateImageTimer.restart();
				for (GraphEntity x : dashboardList)
					for (GraphEntity as : client.getGraphEntity()) {
						if (graphComboBox.getSelectedItem().equals(x.getName())
								&& (as.equals(x))) {
							addDashButton.setEnabled(false);
						} else {
							addDashButton.setEnabled(true);
						}
					}
			}
		});

		// autoselect 1-st host
		if (!hostNameVector.isEmpty()) {
			hostComboBox.setSelectedIndex(0);
		}

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

		/* Panel with graphics list */
		JPanel listGraphPanel = createPanel(mainPanel, BorderLayout.WEST, 250,
				600, new GridLayout(16, 1));
		createLabel(listGraphPanel, "Host :", "Host of you PC", simpleFont);
		listGraphPanel.add(hostComboBox);
		createLabel(listGraphPanel, "Graphics:", "Graphics name", simpleFont);
		listGraphPanel.add(graphComboBox);
		listGraphPanel.add(autoUpdateCheckBox);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				int value = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to close Graphics Form?",
						"Close", JOptionPane.YES_NO_OPTION,
						JOptionPane.ERROR_MESSAGE);
				if (value == 0) {
					System.exit(0);
				} else {
					setVisible(true);
				}

			}
		});

		/* Checkbox dashboard */
		dashboardCheckBox = new JCheckBox("Dashboard");
		dashboardCheckBox.setFont(simpleFont);
		dashboardCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (dashboardCheckBox.isSelected()) {
                    enable(false, graphComboBox, hostComboBox, addDashButton, removeDashButton);
                    reinitDashboardPane(dashboardList, dashBoardPanel);
                    updateDashboard(dashboardList, dashBoardPanel);
                    graphsLayout.show(graphPanel, DASHBOARD);
				} else {
                    enable(true, graphComboBox, hostComboBox, addDashButton, removeDashButton);
                    dashBoardPanel.removeAll();
                    graphsLayout.show(graphPanel, SINGLE);
				}

                updateImageTimer.restart();
			}
		});

		addDashButton.setFont(simpleFont);
		addDashButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addToDashboard((String) graphComboBox.getSelectedItem());

			}
		});

		removeDashButton.setFont(simpleFont);

		listGraphPanel.add(dashboardCheckBox);

        final JPanel dashBoardButtonPanel = new JPanel(new FlowLayout());
		dashBoardButtonPanel.add(addDashButton);
		dashBoardButtonPanel.add(removeDashButton);
		workGraphPanel.add(dashBoardButtonPanel, BorderLayout.SOUTH);
		/* Add elements to Frame */
		setJMenuBar(createMenu(simpleFont));
	}


    private JMenuBar createMenu(Font menuFont) {
        /* Menu */
        JMenuBar menuBar = new JMenuBar();

        createMenu(menuBar, "Help", null, menuFont, new MenuListener() {

            @Override
            public void menuSelected(MenuEvent e) {
                // TODO Auto-generated method stub
                //
                JOptionPane.showMessageDialog(
                        getParent(),
                        "  This is Network Monitorring Program, which\n"
                                + "help to follow for your host in network .\n"
                                + "  You can choose available Host, with proposed\n"
                                + "graphics.\n"
                                + "  Enter a value for the Period during which you\n"
                                + "want to observe the graphics.",
                        "Help", JOptionPane.INFORMATION_MESSAGE);
            }

            @Override public void menuDeselected(MenuEvent e) {}
            @Override public void menuCanceled(MenuEvent e) {}
        });

        createMenu(menuBar, "Logout", " Logout ... ", menuFont, new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                new LoginUI();
                setVisible(false);
            }

            @Override public void menuDeselected(MenuEvent e) {}
            @Override public void menuCanceled(MenuEvent e) {}
        });
        return menuBar;
    }

    private void reinitDashboardPane(List<GraphEntity> list, JPanel pane) {
        pane.removeAll();
        if(list.isEmpty()) return ;

        pane.setLayout(new GridLayout(list.size()/2 + 1, 2));

        for(int i = 0; i < list.size(); i++) {
            pane.add(new ImagePanel());
        }
    }

    private void addToDashboard(String graphName) {
		for (GraphEntity ge : client.getGraphEntity()) {
			if (ge.getName().equals(graphName) && !dashboardList.contains(ge)) {
				dashboardList.add(ge);
                break;
			}
		}
	}

	private void updateDashboard(List<GraphEntity> list, Container dashboard) {
        Component[] components = dashboard.getComponents();

        for (int i = 0; i < components.length; i++) {
            updateImage(list.get(i), (ImagePanel) components[i]);
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

	private void onGraphChanged(String graphName, ImagePanel panel) {
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

		updateImage(graphObj, panel);
	}

    private void updateImage(GraphEntity ge, ImagePanel panel) {
        try {
            String imageurl = client.makeImageUrl(ge.getGraphid(), period);
            panel.setImage(client.getGraphImage(imageurl));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
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
		}
		updateImageTimer.restart();
	}

    private void enable(boolean enable, JComponent... components) {
        for(JComponent c : components) {
            c.setEnabled(enable);
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

	private JRadioButton createRadioButton(ButtonGroup group, String title,
			String toolTip, String path, Font font, Boolean selected,
			ActionListener listener) {
		JRadioButton button = new JRadioButton(title);
		group.add(button);
		button.setToolTipText(toolTip);
		button.setFont(font);
		button.setSelected(selected);
		button.addActionListener(listener);
		if (path.equals("")) {
			return button;
		}
		try {
			Image img = ImageIO.read(getClass().getResourceAsStream(
					"/resources/" + path));
			button.setIcon(new ImageIcon(img));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return button;
	}

	protected void createLabel(Container c, String title, String toolTip,
			Font font) {
		JLabel label = new JLabel(title);
		c.add(label);
		label.setToolTipText(toolTip);
		label.setFont(font);
	}
}