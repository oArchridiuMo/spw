package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
    

import javax.swing.JPanel;

import javax.swing.ImageIcon;
import java.awt.Image;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;	
	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	ImageIcon background = new ImageIcon("Untitle.png");


	public GamePanel() {
		bi = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		big.setBackground(Color.BLACK);
	}

	public void clearScreen() {
		big.clearRect(0, 0, 400, 600);
	}

	public void updateGameUI(GameReporter reporter){
		big.clearRect(0, 0, 400, 600);
		
		big.setColor(Color.WHITE);
		big.drawString(String.format("%s", reporter.getName()), 20, 20);
		big.drawString(String.format("live : %d", reporter.getLive()/2), 155, 20);
		big.drawString(String.format("Score :"), 250, 20);
		big.drawString(String.format("%08d", reporter.getScore()), 300, 20);
		big.drawString(String.format("Highest Score :"), 204, 40);
		big.drawString(String.format("%08d", reporter.getHighScore()), 300, 40);
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}

	//@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(bi, null, 0, 0);
		Image bg = background.getImage();
		g2d.drawImage(bg,  0, 0, 400, 600, this);
	}

}
