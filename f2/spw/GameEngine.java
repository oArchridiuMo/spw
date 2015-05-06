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
	private SpaceShip v1;
	private SpaceShip v2;	
	
	private Timer timer;
	
	private long score = 0;
	private long highScore = 0;
	private long lowwerScore = 0;
	private long limitLevel = 5000; 
	private double difficulty = 0.1;
	public long live = 4; //multiple by 2
	public String name;
	public String noname = "Noname";
	private int stepEnermy = 12;
	private int stepBullet = 24;



		public GameEngine(GamePanel gp, SpaceShip v1,SpaceShip v2) {
		this.gp = gp;
		this.v1 = v1;
		this.v2 = v2;		
		
		gp.sprites.add(v1);
		gp.sprites.add(v2);
		
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
			e.proceed(stepEnermy);
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 100;
				if(score > highScore)
					highScore = score;
				if(score>limitLevel){
					difficulty += 0.1;
					lowwerScore = limitLevel;
					limitLevel = (long)(limitLevel*2.5);
					System.out.println(difficulty);
				}
			}

		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v1.getRectangle();
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
		   	gp.gameOver(this);
		}
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v1.move(-1);
			break;
		case KeyEvent.VK_RIGHT:
			v1.move(1);
			break;
		case KeyEvent.VK_UP:
			v1.straight(-1);
			break;
		case KeyEvent.VK_DOWN:
			v1.straight(1);
			break;
		case KeyEvent.VK_A:
			v2.move(-1);
			break;
		case KeyEvent.VK_D:
			v2.move(1);
			break;
		case KeyEvent.VK_W:
			v2.straight(-1);
			break;
		case KeyEvent.VK_S:
			v2.straight(1);
			break;
		case KeyEvent.VK_N:
			if(!timer.isRunning()){
				newGame();
			}
			break;
		case KeyEvent.VK_F1:
			stepEnermy++;
			break;
		case KeyEvent.VK_F2:
			stepEnermy--;
			break;
		case KeyEvent.VK_P:
			if(timer.isRunning()){
				timer.stop();
				JOptionPane.showMessageDialog(null, "                      Pause!!!\nArrow sign : Control the Spaceship.\nF1         : Enermy Speed Up.\nF2         : Enermy Speed Down.\nPress N    : New Game.\nPress P    : Continue game.", "***Pause***",JOptionPane.INFORMATION_MESSAGE );
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

	public long getLevel(){
		return (long)(difficulty * 10);
	}

	public double getPercent(){
		return ((double)(score - lowwerScore)/(double)(limitLevel - lowwerScore))*100;
	}

	public void newGame(){
		if(score > highScore)
			highScore = score;
		score = 0;
		live = 5;
		difficulty = 0.1;
		//gp.updateGameUI(this);
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
