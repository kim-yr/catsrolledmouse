package catMouseGame;

import javax.swing.JFrame;

public class GameMain extends JFrame {
	public GameMain() {
		this.setTitle("The Cat Rolled THe Mouse");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		GamePanel gamePanel = new GamePanel();
		this.setContentPane(gamePanel);

//		this.setLocationRelativeTo(null); // 화면 중앙에 띄우기
		this.setVisible(true);
		this.pack(); // 크기를 여기서 정하지 않음
	}

	public static void main(String[] args) {
		new GameMain();
	}

}
