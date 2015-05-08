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
	
	private long score1 = 0;
	private long score2 = 0;
	private long highScore = 0;
	private long lowwerScore = 0;
	private long limitLevel = 5000; 
	private double difficulty = 0.1;
	public long live1 = 100;
	public long live2 = 100;
	public String name1;
	public String name2;
	// /public String noname = "Noname";
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
	
	public void start(String name1, String name2){
		timer.start();
		this.name1 = name1;
		this.name2 = name2;
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
				if(live1>0)
					score1 += 100;
				if(live2>0)
					score2 += 100;
				if(score1 > highScore)
					highScore = score1;
				if(score2 > highScore)
					highScore = score2;
				if(score1>limitLevel){
					difficulty += 0.1;
					showNextStage();
					lowwerScore = limitLevel;
					limitLevel = (long)(limitLevel*2.5);

					System.out.println(difficulty);
				}
				else if(score2>limitLevel){
					difficulty += 0.1;									
					lowwerScore = limitLevel;
					showNextStage();
					limitLevel = (long)(limitLevel*2.5);
					System.out.println(difficulty);
				}
			}

		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v1.getRectangle();
		Rectangle2D.Double wr = v2.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				//die(e_iter,e);
				if(live1>0)
					live1-=2;
				e.enermydie();
				gp.sprites.remove(e);
				if(live1<=0){
					gp.sprites.remove(v1);
				}
				return;
			}
			if(er.intersects(wr)){
				//die(e_iter,e);
				if(live2>0)
					live2-=2;
				e.enermydie();
				gp.sprites.remove(e);
				if(live2<=0){
					gp.sprites.remove(v2);
				}
				return;
			}
			if(live1<=0&&live2<=0)
				die();
		}
	}

	public void showNextStage(){
		gp.nextStage(this);
		try{
			Thread.sleep(2000);
			}catch(Exception ex){
				System.out.println("Oh.");
			}
		start(name1,name2);
	}
	
	public void die(){
		//if(live > 0){
		//	live--;	
			/*if(live%2==0){
				try{
					Thread.sleep(1000);
				}catch(Exception e){
					System.out.println("Oh.");
				}
				score -= 100;
		
			}	*/
		//}
		//else{
			if(live1<=0&&live2<=0){
		   	gp.gameOver(this);
		   //	e_iter.remove();
			//gp.sprites.remove(ene);
		   	timer.stop();
		}
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v2.move(-1);
			break;
		case KeyEvent.VK_RIGHT:
			v2.move(1);
			break;
		case KeyEvent.VK_UP:
			v2.straight(-1);
			break;
		case KeyEvent.VK_DOWN:
			v2.straight(1);
			break;
		case KeyEvent.VK_A:
			v1.move(-1);
			break;
		case KeyEvent.VK_D:
			v1.move(1);
			break;
		case KeyEvent.VK_W:
			v1.straight(-1);
			break;
		case KeyEvent.VK_S:
			v1.straight(1);
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

	public long getScoreP1(){
		return score1;
	}

	public long getScoreP2(){
		return score2;
	}

	public String getNameP1(){
		return name1;
	}
	public String getNameP2(){
		return name2;
	}

	public long getLiveP1(){
		return live1;
	}
	public long getLiveP2(){
		return live2;
	}

	public long getHighScore(){
		return highScore;
	}

	public long getMoreScore(){
		if(score1>score2)
			return score1;
		else
			return score2;
	}

	public long getLevel(){
		return (long)(difficulty * 10);
	}

	public double getPercent(){
		return ((double)(getMoreScore() - lowwerScore)/(double)(limitLevel - lowwerScore))*100;
	}

	public void newGame(){
		if(getMoreScore() > highScore)
			highScore = getMoreScore();
		score1 = 0;
		score2 = 0;
		live1 = 100;
		live2 = 100;
		difficulty = 0.1;
		lowwerScore = 0;
		limitLevel = 5000;

		//gp.updateGameUI(this);
		start(name1,name2);
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
