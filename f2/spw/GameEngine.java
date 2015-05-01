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
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();	
	private SpaceShip v;	
	
	private Timer timer;
	
	private long score = 0;
	private long highScore = 0;
	private double difficulty = 0.1;
	public long live = 4; //multiple by 2
	public String name;
	public String noname = "Noname";
	private int stepEnermy = 12;
	private int stepShot = 25;



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

	private void shoot(){
		Bullet b = new Bullet(v.getX(),v.getY());
		gp.sprites.add(b);
		bullets.add(b);
	}
	
	private void process(){
		if(Math.random() < difficulty){
			generateEnemy();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed(stepEnermy);
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 100;
				if(score > highScore)
					highScore = score;
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
		case KeyEvent.VK_S:
			shoot();
			break;
		case KeyEvent.VK_F1:
			stepEnermy++;
			break;
		case KeyEvent.VK_F2:
			stepEnermy--;
			break;
		case KeyEvent.VK_1:
			difficulty += 0.05;
			break;
		case KeyEvent.VK_2:
			difficulty -= 0.05;
			break;
		case KeyEvent.VK_P:
			if(timer.isRunning()){
				timer.stop();
				JOptionPane.showMessageDialog(null, "                      Pause!!!\nArrow sign : Control the Spaceship.\nF1         : Enermy Speed Up.\nF2         : Enermy Speed Down.\nNumber  1  : Difficulity Up.\nNumber  2  : Difficulity Down.\nPress N    : New Game.\nPress P    : Continue game.", "***Pause***",JOptionPane.INFORMATION_MESSAGE );
				}
			else
				timer.start();
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
