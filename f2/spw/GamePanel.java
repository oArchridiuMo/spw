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

	public void newGamePanel(){
		big.clearRect(0, 0, 400, 600);
	}

	public void updateGameUI(GameReporter reporter){
		big.clearRect(0, 0, 400, 600);
		
		big.setColor(Color.WHITE);
		big.drawString(String.format("%s", reporter.getNameP1()), 0, 20);
		big.drawString(String.format("%-10s", reporter.getNameP2()), 310, 20);
		big.drawString(String.format("live P1: %3d%%", reporter.getLiveP1()), 0, 50);
		big.drawString(String.format("live P2: %3d%%", reporter.getLiveP2()), 312, 50);
		big.drawString(String.format("Score : %08d", reporter.getScoreP1()), 0, 80);
		big.drawString(String.format("Score : %08d", reporter.getScoreP2()), 285, 80);
		big.drawString(String.format("Highest Score : %08d", reporter.getHighScore()), 125, 50);
		big.drawString(String.format("Level : %d   %02.2f%%", reporter.getLevel(),reporter.getPercent()), 150, 20);
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}

	public void nextStage(GameReporter reporter){
		big.clearRect(0, 0, 400, 600);

		big.setColor(Color.WHITE);
		big.drawString(String.format("Stage %d",reporter.getLevel()),290,180);
	}

	public void gameOver(GameReporter reporter){
		big.clearRect(0, 0, 400, 600);
		
		big.setColor(Color.WHITE);
		big.drawString(String.format("Game Over!!!"), 170, 270);
		big.drawString(String.format("P1's Score :"), 125, 300);
		big.drawString(String.format("%08d", reporter.getScoreP1()), 220, 300);
		big.drawString(String.format("P2's Score :"), 125, 330);
		big.drawString(String.format("%08d", reporter.getScoreP2()), 220, 330);
		big.drawString(String.format("Highest Score :"), 114, 360);
		big.drawString(String.format("%08d", reporter.getHighScore()), 220, 360);
		big.drawString(String.format("Level : %d   %02.2f%%", reporter.getLevel(),reporter.getPercent()), 160, 390);
	}

	//@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(bi, null, 0, 0);
		Image bg = background.getImage();
		g2d.drawImage(bg,  0, 0, 400, 600, this);
	}

}
