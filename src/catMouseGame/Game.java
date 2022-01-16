package catMouseGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Game extends Thread {
	private int threadDelay = 20; // 전체 화면 랜더링 속도
	String gameState = "direction";
	private boolean isOver = false;
	private boolean playOrStop = true;
	private int count = 0;
	private int round = 0; // 1~10
	private int order = 1; // first second
	private int turn = 1; // player1 or player2
	private int fallen = 0;
	private String winner;
	private boolean dReady = true;
	private boolean pReady = false;
	private boolean rollMouse = false;
	private int dx = 250;
	private int py = 750;
	private int dSpeed = 50;
	private int pSpeed = 50;
	private int mx = 0;
	private int my = 725;
	private long dt = 1;
	private long pt = 1;

	private Image gameImage = new ImageIcon("images/game_page.jpg").getImage();
	private Image endingImage;
	private Image directionImg = new ImageIcon("images/direction.png").getImage();
	private Image powerImg = new ImageIcon("images/power.png").getImage();

	private Milk milk;
	private Mouse mouse;

	private Cat player01 = new Cat(25, 25, "cat1");
	private Cat player02 = new Cat(725, 25, "cat2");

	private ArrayList<Milk> milks = new ArrayList<>();
	private ArrayList<Mouse> mouseBall = new ArrayList<>();
	private ArrayList<Integer> p1score = new ArrayList<>();
	private ArrayList<Integer> p2score = new ArrayList<>();

	public Game() {
		// 점수리스트 클리어해줘야 함
		mouseBall.clear();
		milks.clear();
		gameState = "direction";
		isOver = false;
		dReady = true;
		pReady = true;
		rollMouse = false;
//		round = 0;
		turn = 1;
		winner = "";
		start();
		makeMilk();
	}

	@Override
	public void run() {
		while (true) {
			try {
				sleep(threadDelay);
			} catch (InterruptedException e) {
				System.out.println("stop");
				return;
			}
//			System.out.println("doing");
			directionProcess();
			powerProcess();
			mouseProcess();
		}
	}

	private void makeMilk() {
		int x = 0, y = 0, n = 0;
		String directionOfFalling;
		for (int i = 0; i < 10; i++) {
			if (i == 4 || i == 7 || i == 9) {
				n = 0;
			}
			if (i < 4) {
				x = 345;
				y = 175;
			} else if (i >= 4 && i < 7) {
				x = 380;
				y = 225;
			} else if (i >= 7 && i < 9) {
				x = 415;
				y = 275;
			} else {
				x = 450;
				y = 325;
			}
			milk = new Milk(x + 70 * n, y, ""); // 우유병간 거리를 변수로 설정하면 그에 따라 각 줄의 첫 우유병 시작위치 조절 되도록 해보기
			n++;
			milk.setState(true);
			milks.add(milk);
		}

	}

	private void rollingMouse() {
//		System.out.println(py);
		mouse = new Mouse(mx, my, py);
		mouseBall.add(mouse);
		rollMouse = true;
	}

	public void play() {
		if (playOrStop) {
			if (gameState.equals("direction")) {
//			System.out.println("쥐 멈춰");
				mx = dx;
				dReady = false;
				gameState = "power";
				pReady = true;
			} else if (gameState.equals("power")) {
				pReady = false;
//			gameState = "bowling";
				rollingMouse();
				addScore();
				System.out.println("order: " + order + " turn: " + turn + " round: " + round);
				playOrStop = false;
			}
		}

	}

	public void mouseProcess() {
		for (int i = 0; i < mouseBall.size(); i++) {
			mouse = mouseBall.get(i);
			mouse.shoot();
			fallen = 0;
			for (int j = 0; j < milks.size(); j++) {
				milk = milks.get(j);
				if (hit(new Rectangle(mouse.getX(), mouse.getY(), mouse.getWidth(), mouse.getHeight()),
						new Rectangle(milk.getX(), milk.getY(), milk.getWidth(), milk.getHeight()))) {
					milks.remove(milk);
					fallen++;
//					System.out.println(j + "hit!");
				}
			}
			if (mouse.getY() < mouse.getLimit()) {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 점수 입력
				dx = 250;
				py = 750;
				if (order == 2) {
					milks.clear();
					makeMilk();
					switch (turn) {
					case 1:
						turn = 2;
						break;
					case 2:
						turn = 1;
						break;
					}
					order = 0;
				}
				System.out.println("222order: " + order + " turn: " + turn + " round: " + round);

				if (turn == 2 && order == 2) {
					round++;
					System.out.println("order: " + order + " turn: " + turn + " round: " + round);
				}
				mouseBall.remove(mouse);
				dReady = true;
				gameState = "direction";
				playOrStop = true;
				order++;
			}
		}
//		sleep
//		if (rollMouse) {
//			mouse.shoot();
//			
//		}
	}

	public void addScore() {
//		p1score, p2score
		if (turn == 1) { // player1
			p1score.add(fallen);
		} else { // player2
			p2score.add(fallen);
		}
	}

	public void directionProcess() {
		boolean plusMinus = true;
		while (dReady) {
			try {
				sleep(dt); // 더 빨리 못하나?
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			System.out.println(dReady);
			count++;
			if (plusMinus) {
				dx++;
				if (dx == 650) {
					plusMinus = false;
				}
			} else if (!plusMinus) {
				dx--;
				if (dx == 250) {
					plusMinus = true;
				}
			}
		}
	}

	public void powerProcess() {
		boolean plusMinus = true;
		while (pReady) {
			try {
				sleep(pt);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count++;
			if (plusMinus) {
				py--;
				if (py == 475) {
					plusMinus = false;
				}
			} else if (!plusMinus) {
				py++;
				if (py == 775) {
					plusMinus = true;
				}
			}
		}
	}

	public boolean hit(Rectangle r1, Rectangle r2) {
		return r1.intersects(r2);
	}

	public void drawMilks(Graphics g) {
		for (int i = 0; i < milks.size(); i++) {
			milk = milks.get(i);
//			g.fillRect(milk.getX(), milk.getY(), milk.getWidth(), milk.getHeight());
			g.drawImage(milk.getImage(), milk.getX(), milk.getY(), null);
		}
	}

	public void drawCats(Graphics g) {
//		g.setColor(Color.red);
//		g.fillRect(player01.getX(), player01.getY(), player01.getWidth(), player01.getHeight());
		g.drawImage(player01.getImg(), player01.getX(), player01.getY(), null);
		g.drawImage(player02.getImg(), player02.getX(), player02.getY(), null);
	}

	public void drawMouse(Graphics g) {
		for (int i = 0; i < mouseBall.size(); i++) {
			mouse = mouseBall.get(i);
//			g.fillRect(mouse.getX(), mouse.getY(), mouse.getWidth(), mouse.getHeight());
			g.drawImage(mouse.getImage(), mouse.getX(), mouse.getY(), null);
		}
	}

	public void drawDirectioin(Graphics g) {
		int x = dx - directionImg.getWidth(null) / 2;
		int y = 725 - directionImg.getHeight(null) / 2;
		g.drawImage(directionImg, x, y, null);
	}

	public void drawPower(Graphics g) {
		g.setColor(new Color(208, 52, 255));
		g.fillRect(635, 475, 30, 300);
		g.setColor(new Color(230, 147, 255));
		g.fillRect(635, 575, 30, 200);
		g.setColor(new Color(255, 255, 255));
		g.fillRect(635, 675, 30, 100);

		int x = 650 - powerImg.getWidth(null) / 2;
		int y = py - powerImg.getHeight(null) / 2;
		g.drawImage(powerImg, x, y, null);
	}

	public void drawGaming(Graphics g) {
		g.drawImage(gameImage, 0, 0, null);
	}

	public void drawEnding(Graphics g) {
		String winnerPage = "";
		if (winner.equals("player1")) {
			winnerPage = "player1";
		} else if (winner.equals("player2")) {
			winnerPage = "player2";
		}
		endingImage = new ImageIcon("images/ending_page_" + winnerPage + ".jpg").getImage();
		g.drawImage(endingImage, 0, 0, null);
	}

	public void drawAll(Graphics g) {
		if (isOver) {
			drawEnding(g);
		} else {
			drawGaming(g);
			drawMilks(g);
			drawCats(g);
			drawDirectioin(g);
			drawPower(g);
			drawMouse(g);
//			drawInfo(g);
		}
	}

	public boolean isOver() {
		return isOver;

	}

}
