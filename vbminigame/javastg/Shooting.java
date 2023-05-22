import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Shooting extends Applet implements Runnable, KeyListener{
	
	static final int SCREEN_WIDTH = 800;
	static final int SCREEN_HEIGHT = 600;
	static final int ENEMY_VAL = 200;
	static final int CHARABULLET_VAL = 120;
	
	Thread thread = new Thread(this);
	Chara chara = new Chara();
	Enemy[] enemy = new Enemy[ENEMY_VAL];
	Charabullet[] charabullet = new Charabullet[CHARABULLET_VAL];
	int bulletcnt;
	Keymanager keymanager = new Keymanager();
	Image imgChara;
	Image imgEnemy;
	Image imgCharabullet;
	Image imgEnemybullet;
	
	public void init(){
		for(int i = 0; i < enemy.length; i++){
			enemy[i] = new Enemy();
		}
		for(int i = 0; i < charabullet.length; i++){
			charabullet[i] = new Charabullet();
		}
		addKeyListener(this);
		setBackground(Color.black);
		imgChara = getImage(getDocumentBase(), "pic/chara.jpg");
		imgCharabullet = getImage(getDocumentBase(), "pic/charabullet.jpg");
		imgEnemy = getImage(getDocumentBase(), "pic/enemy.jpg");
		imgEnemybullet = getImage(getDocumentBase(), "pic/enemybullet.jpg");
		bulletcnt = 0;
		thread.start();
	}
	public void run(){
		try{
			
			//ƒƒCƒ“ƒ‹[ƒv
			while(true){
				
				getKey();
				
				//“G
				for(int i = 0; i < enemy.length; i++){
					enemy[i].move();
					enemy[i].shot(chara.getX(), chara.getY());
					enemy[i].moveBullet();
					
					//Ž©‹@‚Æ“G‚Ì’e‚Æ‚Ì“–‚½‚è”»’è
					if(!chara.damagingflag && Hitmanager.hit(chara.getX(), chara.getY(), chara.SIZE, chara.SIZE, enemy[i].bx, enemy[i].by, 8, 8)){
						chara.damagingflag = true;
						enemy[i].by = SCREEN_HEIGHT + 100;
					}
				}
				
				//Ž©‹@‚Ì’e
				for(int i = 0; i < charabullet.length; i++){
					charabullet[i].move(i);
					
					//Ž©‹@‚Ì’e‚Æ“G‚Æ‚Ì“–‚½‚è”»’è
					for(int j = 0; j < enemy.length; j++){
						if(charabullet[i].showflag && enemy[j].life > 0 && Hitmanager.hit(charabullet[i].getX(), charabullet[i].getY(), 7, 16, enemy[j].getX(), enemy[j].getY(), 30, 30)){
							charabullet[i].showflag = false;
							enemy[j].life--;
						}
					}
					
				}
				
				chara.damage();
				
				
				repaint();
				thread.sleep(30);
				
			}
			
		}catch(Exception e){
			System.out.println("run() error!\n" + e);
		}
	}
	public void getKey(){
		if(keymanager.getRight()){
			chara.setX(chara.getX() + 8);
		}
		else if(keymanager.getLeft()){
			chara.setX(chara.getX() - 8);
		}
		else if(keymanager.getUp()){
			chara.setY(chara.getY() - 8);
		}
		else if(keymanager.getDown()){
			chara.setY(chara.getY() + 8);
		}
		
		
		if(keymanager.getZ()){
			
			if(bulletcnt % 4 == 0){
				charabullet[bulletcnt].setBullet(chara.getX() - 15, chara.getY() - 32);
				if(bulletcnt < charabullet.length - 1){
					bulletcnt++;
				}
				else{
					bulletcnt = 0;
				}
			}
			
			
			if(bulletcnt % 4 == 1){
				charabullet[bulletcnt].setBullet(chara.getX() + 7, chara.getY() - 32);
				if(bulletcnt < charabullet.length - 1){
					bulletcnt++;
				}
				else{
					bulletcnt = 0;
				}
			}
			if(bulletcnt % 4 == 2){
				charabullet[bulletcnt].setBullet(chara.getX() + 32 - 7, chara.getY() - 32);
				if(bulletcnt < charabullet.length - 1){
					bulletcnt++;
				}
				else{
					bulletcnt = 0;
				}
			}
			if(bulletcnt % 4 == 3){
				charabullet[bulletcnt].setBullet(chara.getX() + 32 + 15, chara.getY() - 32);
				if(bulletcnt < charabullet.length - 1){
					bulletcnt++;
				}
				else{
					bulletcnt = 0;
				}
			}
		}
		
	}
	public void paint(Graphics g){
		//g.setColor(Color.white);
		
		if(chara.blankflag == false){
			g.drawImage(imgChara, chara.getX(), chara.getY(), this);
		}
		
		for(int i = 0; i < enemy.length; i++){
			if(enemy[i].life > 0){
				g.drawImage(imgEnemy, enemy[i].getX(), enemy[i].getY(), this);
			}
			
			if(enemy[i].showflag){
				g.drawImage(imgEnemybullet, enemy[i].bx, enemy[i].by, this);
			}
		}
		
		for(int i = 0; i < charabullet.length; i++){
			if(charabullet[i].showflag){
				g.drawImage(imgCharabullet, charabullet[i].getX(), charabullet[i].getY(), this);
			}
		}
	}
	
	public void keyTyped(KeyEvent e){}
	public void keyPressed(KeyEvent e){
		
		switch(e.getKeyCode()){
		case 39:
			keymanager.setRight();
			break;
		case 37:
			keymanager.setLeft();
			break;
		case 38:
			keymanager.setUp();
			break;
		case 40:
			keymanager.setDown();
			break;
		case 90:
			keymanager.setZ();
			break;
		}
	}
	public void keyReleased(KeyEvent e){
		switch(e.getKeyCode()){
		case 39:
			keymanager.clearRight();
			break;
		case 37:
			keymanager.clearLeft();
			break;
		case 38:
			keymanager.clearUp();
			break;
		case 40:
			keymanager.clearDown();
			break;
		case 90:
			keymanager.clearZ();
			break;
		}
	}
}

//“–‚½‚è”»’èŠÇ—
class Hitmanager{
	public static boolean hit(int x1, int y1, int width1, int height1, int x2, int y2, int width2, int height2){
		if( x1 + width1 > x2 &&
			x1 < x2 + width2 &&
			y1 + height1 > y2 &&
			y1 < y2 + height2){
			return true;
		}
		return false;
	}
}

class Charabullet{
	private int x;
	private int y;
	boolean showflag;
	
	Charabullet(){
		showflag = false;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public void setBullet(int bx, int by){
		x = bx;
		y = by;
		showflag = true;
	}
	
	public void move(int i){
		
		if(showflag == false) return;
		
		if(i % 4 == 0){
			x--;
		}
		else if(i % 4 == 3){
			x++;
		}
		
		y -= 20;
		
		if(y < -30){
			showflag = false;
		}
	}
}

class Chara{
	
	final int SIZE = 32;
	private int x;
	private int y;
	boolean damagingflag;
	boolean blankflag;
	int blankcnt;
	
	Chara(){
		x = Shooting.SCREEN_WIDTH / 2 - SIZE / 2;
		y = Shooting.SCREEN_HEIGHT - 100;
		damagingflag = false;
		blankflag = false;
		blankcnt = 0;
	}
	
	public void setX(int n){
		if(x < 0){
			x = 0;
		}
		else if(x > Shooting.SCREEN_WIDTH - SIZE){
			x = Shooting.SCREEN_WIDTH - SIZE;
		}
		else{
			x = n;
		}
		
	}
	public void setY(int n){
		if(y < 0){
			y = 0;
		}
		else if(y > Shooting.SCREEN_HEIGHT - SIZE){
			y = Shooting.SCREEN_HEIGHT - SIZE;
		}
		else{
			y = n;
		}
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public void damage(){
		if(damagingflag){
			blankflag = !blankflag;
			blankcnt++;
			if(blankcnt >= 40){
				damagingflag = false;
				blankcnt = 0;
				blankflag = false;
			}
		}
	}
	
}

class Enemy{
	
	Random rnd = new Random();
	private int x;
	private int addx;
	private boolean addflag;
	private int y;
	int life;
	int bx;
	int addbx;
	int by;
	int addby;
	boolean showflag;
	final int SIZE = 32;
	final int SHOT_POS = 200;
	
	Enemy(){
		life = 1;
		addx = rnd.nextInt(20) - 10;
		addflag = true;
		x = rnd.nextInt(Shooting.SCREEN_WIDTH - SIZE);
		y = rnd.nextInt(4000) * -1;
		showflag = false;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public void move(){
		
		if(life <= 0) return;
		
		x += addx;
		y += 2;
		
		if(addx > 10 || addx < -10){
			addflag = !addflag;
		}
		if(addflag){
			addx++;
		}
		else{
			addx--;
		}
		
		
		if(y > Shooting.SCREEN_HEIGHT){
			y = rnd.nextInt(4000) * -1;
		}
	}
	
	public void shot(int cx, int cy){
		if(showflag || life <= 0) return;
		
		if(y >= SHOT_POS){
			showflag = true;
			addbx = (cx - x) / 50;
			addby = (cy - y) / 50;
			bx = x;
			by = y;
		}
	}
	public void moveBullet(){
		if(showflag == false) return;
		
		bx += addbx;
		by += addby;
		if(addbx == 0 && addby == 0){
			by ++;
		}
		
		if(bx < 0 || bx > Shooting.SCREEN_WIDTH || by < 0 || by > Shooting.SCREEN_HEIGHT){
			showflag = false;
			bx = 0;
			by = 0;
			addbx = 0;
			addby = 0;
		}
		
	}
}

class Keymanager{
	private boolean rightflag;
	private boolean leftflag;
	private boolean upflag;
	private boolean downflag;
	private boolean zflag;
	
	Keymanager(){
		this.clear();
		zflag = false;
	}
	
	public void clear(){
		rightflag = false;
		leftflag = false;
		upflag = false;
		downflag = false;
	}
	
	public void setRight(){
		this.clear();
		rightflag = true;
	}
	public void setLeft(){
		this.clear();
		leftflag = true;
	}
	public void setUp(){
		this.clear();
		upflag = true;
	}
	public void setDown(){
		this.clear();
		downflag = true;
	}
	public void clearRight(){
		rightflag = false;
	}
	public void clearLeft(){
		leftflag = false;
	}
	public void clearUp(){
		upflag = false;
	}
	public void clearDown(){
		downflag = false;
	}
	
	public void setZ(){
		zflag = true;
	}
	public void clearZ(){
		zflag = false;
	}
	public boolean getZ(){
		return zflag;
	}
	
	public boolean getRight(){
		return rightflag;
	}
	public boolean getLeft(){
		return leftflag;
	}
	public boolean getUp(){
		return upflag;
	}
	public boolean getDown(){
		return downflag;
	}
}
