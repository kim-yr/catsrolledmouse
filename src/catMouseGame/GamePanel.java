package catMouseGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private Game game;

	private Image bufferImage;
	private Graphics screenGraphics;
	private Image startImage = new ImageIcon("images/start_page.jpg").getImage();
	private Image loadingImage = new ImageIcon("images/loading_page.jpg").getImage();
	private Image infoImage = new ImageIcon("images/info_page.jpg").getImage();

	private String isState = "start";

	public GamePanel() {
		this.setPreferredSize(new Dimension(900, 800));
		setOpaque(true);
		setBackground(Color.black);
		gameInit();
	}

	public void gameInit() {

		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
//				System.out.println(e.getKeyCode());
				int keyCode = e.getKeyCode();
				switch (keyCode) {
				case KeyEvent.VK_ENTER:
					if (isState.equals("start")) {
						loading();
					} else if (isState.equals("information")) {
						isState = "gaming";
						game = new Game();
					} else if (isState.equals("gaming")) {
						game.play();
					}
					break;
				case KeyEvent.VK_ESCAPE:
					System.exit(0);
					break;
				case KeyEvent.VK_R:
					if (game.isOver()) {
						isState = "start";
					}
					break;
				}
			}

		});
	};

	private void loading() {
		isState = "loading";
		Timer loadingTimer = new Timer();
		TimerTask loadingTimerTask = new TimerTask() {

			@Override
			public void run() {
				isState = "information";
			}

		};
		loadingTimer.schedule(loadingTimerTask, 1000);
	}

	public void paintComponent(Graphics g) {
		// 더블 버퍼링 기법
		// 1. 버퍼 이미지 생성
		// 2. 메모리에 올리기
		// 3. graphics가 그리기
		bufferImage = this.createImage(900, 800);
		screenGraphics = bufferImage.getGraphics();
		screenCapture(screenGraphics);
		g.drawImage(bufferImage, 0, 0, this);
	}

	public void screenCapture(Graphics g) {
//		game.drawPlayer(g);
//		game.drawPlayerBullet(g);
//		game.drawPlayerEnemy(g);
		if (isState.equals("start")) {
			g.drawImage(startImage, 0, 0, null);
		} else if (isState.equals("loading")) {
			g.drawImage(loadingImage, 0, 0, null);
		} else if (isState.equals("information")) {
			g.drawImage(infoImage, 0, 0, null);
		} else if (isState.equals("gaming")) {
			game.drawAll(g);
		}
		repaint();
	}
}
