import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener{
		
	
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;         //Tamanho dos objetos
 	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/ UNIT_SIZE;
 	static final int DELAY = 70;    //Velocidade do game
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int applesEaten;
	int applex;       //Coordenada x da maçã
	int appley; 	  //Coordenada y da maçã
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
 	
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
	}
	public void draw(Graphics g) {                 
		if(running) {
			//Desenhando linhas na interface
			
		/*	for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH, i*UNIT_SIZE);
			}
			*/
			g.setColor(Color.red);                        //Desenhando a maçã
			g.fillOval(applex, appley, UNIT_SIZE, UNIT_SIZE);
		
			for(int i=0; i<bodyParts; i++) {				//Desenhando a cabeça da cobrinha
				if(i==0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {											//Desenhando o corpo 
					g.setColor(new Color(45, 180, 0));
					//g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255))); //Criando cores aleatórias para o corpo
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			
			}
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 35));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Pontos: "+ applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Pontos: "+ applesEaten)) / 2, g.getFont().getSize());
	}
		
		else {
			gameOver(g);
			
		}
	}
	public void newApple() {         //Gerar uma maçã aleatoria nova depois de ser comida
		applex = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appley = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
	}
	public void move() {
		for (int i = bodyParts; i>0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}		
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	
	}
	public void checkApple() {
		if((x[0] == applex)&& (y[0] == appley)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
		
	}
	public void checkCollisions() {
		//Checando se a cabeça colidiu com o corpo 
		for(int i = bodyParts; i>0; i--) {
			if((x[0] == x[i])&& (y[0] == y[i])) { 
				running = false;
			}
		}
		//Checando se a cabeça colidiu com o lado esquerdo da tela
		if(x[0]<0) {
			running = false;
		}
		//Checando se a cabeça colidiu com o lado direito da tela
		if(x[0]>SCREEN_WIDTH) {
			running = false;
		}
		//Checando se a cabeça colidiu com o topo da tela
		if(y[0]<0) {
			running = false;
		}
		//checar se a cabeça colidiu com parte de baixo da tela
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
	
		if(!running) {
			timer.stop();
		}
	
	}
	
	public void gameOver(Graphics g){
		//Mostrar os pontos na tela de Game Over:
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 35));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Pontos: "+ applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Pontos: "+ applesEaten)) / 2, g.getFont().getSize());
		
		//Texto de Game Over
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 50));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
	}
	
	public void actionPerformed(ActionEvent e) {
	 
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	public class MyKeyAdapter extends KeyAdapter{  //Controlar a cobrinha
		@Override
		public void  keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R'){
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L'){
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D'){
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U'){
					direction = 'D';
				}
				break;
			}
		}
	}
		
	
	
}


