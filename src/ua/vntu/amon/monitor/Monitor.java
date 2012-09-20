package ua.vntu.amon.monitor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Monitor extends JComponent {
	private static final long serialVersionUID = -3709557218616218923L;

	public Monitor() {
		setPreferredSize(new Dimension(120, 120));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		int mx = getWidth() / 2 - 30;
		int my = getHeight() / 2 - 10;
		
		g2.drawString("Monitor name", mx, my);
	}
	
	
	public static void main(String[] args) {
		JFrame testFrame = new JFrame("Monitor test frame");
		testFrame.setLayout(new BorderLayout());
		testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testFrame.setSize(600, 400);
		
		Monitor mon1 = new Monitor();
		testFrame.add(mon1, BorderLayout.NORTH);
		
		Monitor mon2 = new Monitor();
		testFrame.add(mon2, BorderLayout.SOUTH);
		// testFrame.add(new Monitor(), BorderLayout.SOUTH);
		
		testFrame.setVisible(true);
	}
}
