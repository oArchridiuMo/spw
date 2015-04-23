package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator; 

import javax.swing.JOptionPane; 

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private SpaceShip v;	
	
	private Timer timer;
	
	private long score = 0;
	private long highScore = 0;
	private double difficulty = 0.1;
	public long live = 4; //multiple by 2
	public String name;
	public String noname = "Noname";



		public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(String name){
		timer.start();
		this.name = name;
	}
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}
	
	private void process(){
		if(Math.random() < difficulty){
			generateEnemy();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 100;
			}

		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				die();
				return;
			}
		}
	}
	
	public void die(){
		if(live > 0){
			live--;	
			if(live%2==0){
				try{
					Thread.sleep(1000);
				}catch(Exception e){
					System.out.println("Oh.");
				}
				score -= 100;
			}	
		}
		else{
		   	timer.stop();
		   	JOptionPane.showMessageDialog(null, "Score : " + score, "Game Over!",JOptionPane.INFORMATION_MESSAGE );
		}
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.move(-1);
			break;
		case KeyEvent.VK_RIGHT:
			v.move(1);
			break;
		case KeyEvent.VK_UP:
			v.straight(-1);
			break;
		case KeyEvent.VK_DOWN:
			v.straight(1);
			break;
		case KeyEvent.VK_N:
			newGame();
			break;
		}
	}

	public long getScore(){
		return score;
	}

	public String getName(){
		return name;
	}

	public long getLive(){
		return live;
	}

	public long getHighScore(){
		return highScore;
	}

	public void newGame(){
		if(score > highScore)
			highScore = score;
		score = 0;
		live = 5;
		start(name);
	}

	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
