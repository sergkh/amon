package ua.vntu.amon.gui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

public class ImagePanel extends JComponent {

	private static final long serialVersionUID = 1L;
	private Image img;

	public ImagePanel() {
	}

	void setImage(Image img) {
		this.img = img;
		repaint();
	}

	public void paint(Graphics g) {
		if (img != null) {
			g.drawImage(img, 5, 5, this);
		}
	}
}