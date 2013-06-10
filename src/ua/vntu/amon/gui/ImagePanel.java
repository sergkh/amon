package ua.vntu.amon.gui;

import java.awt.*;

import javax.swing.JComponent;

public class ImagePanel extends JComponent {

    private static final long serialVersionUID = 1L;

    private static final int GAP = 5;

    private Image img;

	public ImagePanel() {
	}

	void setImage(Image img) {
		this.img = img;
		repaint();
	}

	public void paint(Graphics g) {
        super.paint(g);
		if (img == null) return ;

        int compWidth = getWidth() - 2*GAP;
        int compHeight = getHeight() - 2*GAP;

        int imgWidth = img.getWidth(this);
        int imgHeight = img.getHeight(this);

        if(imgWidth > compWidth || imgHeight > compHeight) {

            double scale = Math.min((double) compWidth / imgWidth,  (double) compHeight / imgHeight);

            Image newImg = img.getScaledInstance((int) (imgWidth*scale), (int) (imgHeight*scale), Image.SCALE_SMOOTH);

            g.drawImage(newImg, GAP, GAP, this);
        } else {
            g.drawImage(img, GAP, GAP, this);
        }


	}
}