package f2.spw;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Main {
	public static void main(String[] args){
		String name1;
		String name2;
		name1 = JOptionPane.showInputDialog("Enter P1's name: ");
		name2 = JOptionPane.showInputDialog("Enter P2's name: ");
		JOptionPane.showMessageDialog(null, "Arrow sign : Control the Spaceship.\nF1         : Enermy Speed Up.\nF2         : Enermy Speed Down.\nPress N    : New Game.\nPress P    : Pause.", "How to play?",JOptionPane.INFORMATION_MESSAGE );

		
		JFrame frame = new JFrame("Space War");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 650);
		frame.getContentPane().setLayout(new BorderLayout());
		
		SpaceShip v1 = new SpaceShip(80, 550, 20, 10);
		SpaceShip v2 = new SpaceShip(280, 550, 20 ,10);
		GamePanel gp = new GamePanel();
		GameEngine engine = new GameEngine(gp, v1, v2);
		frame.addKeyListener(engine);
		frame.getContentPane().add(gp, BorderLayout.CENTER);
		frame.setVisible(true);
		
		engine.start(name1,name2);


	}
}
