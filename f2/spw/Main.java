package f2.spw;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane; 


public class Main {
	public static void main(String[] args){
		String name;
		long score;
		name = JOptionPane.showInputDialog("Enter your name: ");

		
		JFrame frame = new JFrame("Space War");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 650);
		frame.getContentPane().setLayout(new BorderLayout());
		
		SpaceShip v = new SpaceShip(180, 550, 20, 20);
		GamePanel gp = new GamePanel();
		GameEngine engine = new GameEngine(gp, v);
		frame.addKeyListener(engine);
		frame.getContentPane().add(gp, BorderLayout.CENTER);
		frame.setVisible(true);
		
		engine.start(name);
	}
}
