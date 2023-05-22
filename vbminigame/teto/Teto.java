import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Teto extends Applet implements Runnable, KeyListener{
	
	static final int SCREEN_WIDTH = 320;
	static final int SCREEN_HEIGHT = 640;
	
	int score;			//�X�R�A
	int blocknum;		//���݂̃u���b�N�i���o�[
	
	Thread thread = new Thread(this);
	Block block = new Block();
	Screen screen = new Screen();
	Image imgBlock;
	Image imgBack;
	
	//������
	public void init(){
		addKeyListener(this);
		imgBlock = getImage(getDocumentBase(), "pic/block.jpg");
		imgBack = getImage(getDocumentBase(), "pic/back.jpg");
		block.makeBlock(block.rndBlock());
		setBackground(Color.black);
		
		thread.start();
	}
	
	//���s
	public void run(){
		try{
			while(true){
				
				repaint();
				block.move();
				
				//�u���b�N�̓����蔻��
				touchBlock();
				
				//�u���b�N�̏����鏈��
				clearBlock();
				
				getKey();
				
				thread.sleep(100);
			}
		}catch(Exception e){
			System.out.println("run() �G���[!\n" + e);
		}
	}
	
	
	public void paint(Graphics g){
		g.setColor(Color.white);
		g.setFont(new Font("Dialog", 0, 25));
		//�w�i��`��
		for(int i = 0; i < Teto.SCREEN_WIDTH; i += 32){
			g.drawImage(imgBack, i, Font.PLAIN, this);
		}
		
		//�u���b�N��`��
		for(int x = 0; x < block.tleng; x++){
			for(int y = 0; y < block.tleng; y++){
				if(block.territory[x][y] == block.BLOCK){
					g.drawImage(imgBlock, block.x + (x * 32), block.y + (y * 32), this);
				}
			}
		}
		
		//�X�N���[���̃u���b�N��`��
		for(int x = 0; x < Teto.SCREEN_WIDTH; x += 32){
			for(int y = 0; y < Teto.SCREEN_HEIGHT; y += 32){
				if(screen.scr[x / 32][y / 32] == block.BLOCK){
					g.drawImage(imgBlock, x, y, this);
				}
			}
		}
		g.drawString("Score : " + score , 5, 25);
	}
	
	public void touchBlock(){
		boolean flag;
		int tx;
		int ty;
		
		flag = false;
		tx = 0;
		ty = 0;
		
		//�n�ʁi��ʉ��j�Ƃ̐ڐG
		if(block.y + block.under >= Teto.SCREEN_HEIGHT){
			flag = true;
		}
		
		//�����u���b�N�Ƃ̐ڐG
		if(block.y + block.top >= 0){
			
			for(int sx = 0; sx < screen.scr.length; sx++){
				for(int sy = 0; sy < screen.scr[0].length; sy++){
					for(int bx = 0; bx < block.tleng; bx++){
						for(int by = 0; by < block.tleng; by++){
							if(Hitmanager.hit(sx * 32, sy * 32, 32, 32, bx * 32 + block.x, (by + 1) * 32 + block.y, 32, 32) &&
								block.territory[bx][by] == block.BLOCK &&
								screen.scr[sx][sy] == block.BLOCK){
								flag = true;
							}
						}
					}
				}
			}
		}
		
		try{
			if(flag){
				tx = block.x / 32;
				ty = block.y / 32;
				block.x = 32 * 3;
				block.y = 32 * -4;
				
				//�X�N���[���փu���b�N���R�s�[
				for(int x = 0; x < block.tleng; x++){
					for(int y = 0; y < block.tleng; y++){
						if(block.territory[x][y] == block.BLOCK){
							screen.scr[tx + x][ty + y] = block.territory[x][y];
						}
					}
				}
				block.makeBlock(block.rndBlock());
			}
		}catch(Exception e){
			System.out.println("touchBlook() �R�s�[�G���[ :\n" + e);
			System.out.println("tx : " + tx);
			System.out.println("ty : " + ty);
		}
		
		
	}
	
	//�u���b�N��������
	public void clearBlock(){
		int cnt;
		
		cnt = 0;
		
		for(int y = 0; y < screen.scr[0].length; y++){
			for(int x = 0; x < screen.scr.length; x++){
				if(screen.scr[x][y] == block.BLOCK){
					cnt++;
				}
				
				//�������̂ŏ���
				if(cnt >= screen.scr.length){
					cnt = 0;
					score += 100;
					for(int i = 0; i < screen.scr.length; i++){
						screen.scr[i][y] = block.NONE;
						try{
							repaint();
							thread.sleep(50);
						}catch(Exception e){
							System.out.println("clearBlock() �G���[ : \n" + e);
						}
					}
					for(int ix = 0; ix < screen.scr.length; ix++){
						for(int iy = y; iy > 0; iy-- ){
							screen.scr[ix][iy] = screen.scr[ix][iy - 1];
						}
					}
				}
			}
			cnt = 0;
		}
	}
	
	//�L�[����
	public void getKey(){
		if(Keymanager.getRight()){
			boolean flag;
			
			flag = false;
			
			//�E�u���b�N�ւ̂߂荞�ݖh�~
			for(int sx = 0; sx < screen.scr.length; sx++){
				for(int sy = 0; sy < screen.scr[0].length; sy++){
					for(int bx = 0; bx < block.tleng; bx++){
						for(int by = 0; by < block.tleng; by++){
							if(Hitmanager.hit(sx * 32, sy * 32, 32, 32, (bx + 1) * 32 + block.x, by * 32 + block.y, 32, 32) &&
								block.territory[bx][by] == block.BLOCK &&
								screen.scr[sx][sy] == block.BLOCK){
									return;
							}
							else{
								flag = true;
							}
						}
					}
				}
			}
			
			if(flag){
				block.x += 32;
			}
			
			if(block.x + block.rightx >= Teto.SCREEN_WIDTH){
				block.x = Teto.SCREEN_WIDTH - block.rightx;
			}
		}
		else if(Keymanager.getLeft()){
			boolean flag;
			
			flag = false;
			
			//���u���b�N�ւ̂߂荞�ݖh�~
			for(int sx = 0; sx < screen.scr.length; sx++){
				for(int sy = 0; sy < screen.scr[0].length; sy++){
					for(int bx = 0; bx < block.tleng; bx++){
						for(int by = 0; by < block.tleng; by++){
							if(Hitmanager.hit(sx * 32, sy * 32, 32, 32, (bx - 1) * 32 + block.x, by * 32 + block.y, 32, 32) &&
								block.territory[bx][by] == block.BLOCK &&
								screen.scr[sx][sy] == block.BLOCK){
									return;
							}
							else{
								flag = true;
							}
						}
					}
				}
			}
			
			if(flag){
				block.x -= 32;
			}
			
			if(block.x + block.leftx < 0){
				block.x = block.leftx * -1;
			}
		}
		else if(Keymanager.getUp()){
		}
		else if(Keymanager.getDown()){
			block.y += 32;
		}
		else if(Keymanager.getZ()){
			block.roll();
		}
	}
	public void keyTyped(KeyEvent e){}
	
	//�L�[���͔���
	public void keyPressed(KeyEvent e){
		
		switch(e.getKeyCode()){
		case 39:
			Keymanager.setRight();
			break;
		case 37:
			Keymanager.setLeft();
			break;
		case 40:
			Keymanager.setDown();
			break;
		case 90:
			Keymanager.setZ();
			break;
		}
	}
	public void keyReleased(KeyEvent e){
		switch(e.getKeyCode()){
		case 39:
			Keymanager.clearRight();
			break;
		case 37:
			Keymanager.clearLeft();
			break;
		case 40:
			Keymanager.clearDown();
			break;
		case 90:
			Keymanager.clearZ();
			break;
		}
	}
}

//�u���b�N
class Block{
	
	int x;
	int y;
	int top;
	int under;
	int leftx;
	int rightx;
	int cnt;
	int tleng;	//�z��territory�̃T�C�Y
	
	final static int NONE = 0;
	final static int BLOCK = 1;
	
	int[][] territory = new int[4][4];
	
	Block(){
		x = 32 * 1;
		y = 32 * - 1;
	}
	
	//�u���b�N�̌`���쐬
	public void makeBlock(int i){
		
		for(int x = 0; x < tleng; x++){
			for(int y = 0; y < tleng; y++){
				territory[x][y] = NONE;
			}
		}
		
		switch(i){
		case 0:
			territory[3][0] = BLOCK;
			territory[3][1] = BLOCK;
			territory[3][2] = BLOCK;
			territory[3][3] = BLOCK;
			tleng = 4;
			break;
		case 1:
			territory[0][3] = BLOCK;
			territory[1][3] = BLOCK;
			territory[2][3] = BLOCK;
			territory[3][3] = BLOCK;
			tleng = 4;
			break;
		case 2:
			territory[0][0] = BLOCK;
			territory[0][1] = BLOCK;
			territory[1][1] = BLOCK;
			territory[2][1] = BLOCK;
			tleng = 3;
			break;
		case 3:
			territory[2][0] = BLOCK;
			territory[2][1] = BLOCK;
			territory[1][1] = BLOCK;
			territory[0][1] = BLOCK;
			tleng = 3;
			break;
		case 4:
			territory[0][0] = BLOCK;
			territory[0][1] = BLOCK;
			territory[1][1] = BLOCK;
			territory[2][1] = BLOCK;
			tleng = 3;
			break;
		case 5:
			territory[2][0] = BLOCK;
			territory[1][0] = BLOCK;
			territory[1][1] = BLOCK;
			territory[0][1] = BLOCK;
			tleng = 3;
			break;
		case 6:
			territory[0][0] = BLOCK;
			territory[1][0] = BLOCK;
			territory[0][1] = BLOCK;
			territory[1][1] = BLOCK;
			tleng = 2;
			break;
		case 7:
			territory[1][0] = BLOCK;
			territory[0][1] = BLOCK;
			territory[1][1] = BLOCK;
			territory[2][1] = BLOCK;
			tleng = 3;
			break;
		}
		
		setSize();
	}
	
	//�u���b�N�̏㉺���E�̈ʒu��ݒ�
	public void setSize(){
		under = 0;
		rightx = 0;
		top = 1000;
		leftx = 1000;
		
		for(int x = 0; x < tleng; x++){
			for(int y = 0; y < tleng; y++){
				if(territory[x][y] == BLOCK){
					if(under < y * 32 + 32){
						under = y * 32 + 32;
					}
					if(rightx < x * 32 + 32){
						rightx = x * 32 + 32;
					}
					if(leftx > x * 32){
						leftx = x * 32;
					}
					if(top > y * 32){
						top = y * 32;
					}
				}
			}
		}
		
	}
	
	public int rndBlock(){
		Random rnd = new Random();
		return rnd.nextInt(8);
	}
	
	//����
	public void move(){
		
		if(Keymanager.getDown()) return;
		
		cnt++;
		if(cnt >= 4){
			y += 32;
			cnt = 0;
		}
		
	}
	
	//��]
	public void roll(){
		int[][] temp = new int[4][4];
		
		//��]����
		for(int x = 0; x < tleng; x++){
			for(int y = 0; y < tleng; y++){
				temp[x][y] = territory[tleng - 1 - y][x];
			}
		}
		
		//�X�V
		for(int x = 0; x < tleng; x++){
			for(int y = 0; y < tleng; y++){
				territory[x][y] = temp[x][y];
			}
		}
		setSize();
		
		//��ʂ���͂ݏo�Ȃ��悤����
		if(x + leftx < 0){
			x = leftx * -1;
		}
		else if(x + rightx >= Teto.SCREEN_WIDTH){
			x = Teto.SCREEN_WIDTH - rightx;
		}
	}
}

//�X�N���[��
class Screen{
	int[][] scr = new int[Teto.SCREEN_WIDTH / 32][Teto.SCREEN_HEIGHT / 32];
	
	Screen(){
		for(int x = 0; x < scr.length; x++){
			for(int y = 0; y < scr[0].length; y++){
				scr[x][y] = 0;
			}
		}
	}
}